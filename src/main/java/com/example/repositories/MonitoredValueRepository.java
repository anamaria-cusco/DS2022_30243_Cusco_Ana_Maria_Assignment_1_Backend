package com.example.repositories;

import com.example.entities.MonitoredValue;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MonitoredValueRepository extends JpaRepository<MonitoredValue, Long> {
}
