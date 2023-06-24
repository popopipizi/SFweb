package com.example.trafficmonitor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrafficController {

    private final TrafficDataRepository repository;
    private final com.example.trafficmonitor.NetworkMonitor monitor;

    public TrafficController(TrafficDataRepository repository, com.example.trafficmonitor.NetworkMonitor monitor) {
        this.repository = repository;
        this.monitor = monitor;
    }

    @GetMapping("/api/traffic/realtime")
    public List<com.example.trafficmonitor.TrafficData> getRealtimeTraffic(@RequestParam String host, @RequestParam int port) {
        return monitor.getTrafficData(host, port);
    }

    @GetMapping("/api/traffic/history")
    public List<com.example.trafficmonitor.TrafficData> getTrafficHistory(@RequestParam String host, @RequestParam int port) {
        return repository.findAll(); // this is a simplification, in reality you should filter by host and port
    }

}
