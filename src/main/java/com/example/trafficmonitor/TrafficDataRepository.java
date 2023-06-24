package com.example.trafficmonitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficDataRepository extends JpaRepository<com.example.trafficmonitor.TrafficData, Integer> {
}