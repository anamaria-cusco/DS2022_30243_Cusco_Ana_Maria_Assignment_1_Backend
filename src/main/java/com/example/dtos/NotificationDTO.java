package com.example.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@ToString
public class NotificationDTO {
    private String username;
    private Long deviceId;
    private String deviceDescription;
    private Float currentValue;
    private Float maxValue;
    private String timestamp;
}
