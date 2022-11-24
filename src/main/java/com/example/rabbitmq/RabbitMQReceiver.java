package com.example.rabbitmq;

import com.example.controllers.AuthController;


import com.example.dtos.DeviceDTO;
import com.example.dtos.MonitoredValueDTO;
import com.example.dtos.NotificationDTO;
import com.example.services.DeviceService;
import com.example.services.MonitoredValueService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.management.Notification;


@Component
@RabbitListener(queues = "my-queue", id = "listener")
public class RabbitMQReceiver {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MonitoredValueService monitoredValueService;

    @RabbitHandler
    public void producer(String incomingMessage){
        JSONObject jsonObject = new JSONObject(incomingMessage);
        Long deviceId = jsonObject.getLong("deviceId");
        String timestamp = jsonObject.getString("timestamp");
        Float energyConsumption = jsonObject.getFloat("value");
        System.err.println(deviceId);
        System.err.println(timestamp);
        System.err.println(energyConsumption);

        DeviceDTO deviceDTO = deviceService.findDeviceById(deviceId);
        MonitoredValueDTO monitoredValueDTO = MonitoredValueDTO.builder()
                                                                .timestamp(timestamp)
                                                                .energyConsumption(energyConsumption)
                                                                .build();
        NotificationDTO notification = NotificationDTO.builder()
                .username(deviceService.findUserUsernameForDevice(deviceDTO.getId()))
                .deviceId(deviceDTO.getId())
                .deviceDescription(deviceDTO.getDescription())
                .timestamp(timestamp)
                .currentValue(energyConsumption)
                .maxValue(deviceDTO.getMaxConsumption())
                .build();
        if(energyConsumption > deviceDTO.getMaxConsumption()){
            monitoredValueDTO.setEnergyConsumption(deviceDTO.getMaxConsumption());
            //String message = "Hourly consumption exceeded for device " + deviceId + " Current Value: "+ energyConsumption;
            logger.error(String.valueOf(notification));
            sendPeakNotification(notification);
        }

        monitoredValueService.addMonitoredValue(deviceId, monitoredValueDTO);
        logger.info("MonioredValue listener invoked - Consuming Message");


    }


    @CrossOrigin
    @MessageMapping("/send")
    @SendTo("/topic/message")
    public ResponseEntity<?> sendPeakNotification(NotificationDTO notification){
        logger.error("-------------SENT--------------");
        template.convertAndSend("/topic/message", notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
