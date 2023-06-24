package com.example.trafficmonitor;

import org.pcap4j.core.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class NetworkMonitor {

    private final TrafficDataRepository repository;
    private PcapNetworkInterface device;

    public NetworkMonitor(TrafficDataRepository repository) {
        this.repository = repository;
    }

    public void init() throws PcapNativeException {
        List<PcapNetworkInterface> allDevs = Pcaps.findAllDevs();
        device = allDevs.get(0); // this is a simplification, in reality you should let the user choose the device
    }

    public List<TrafficData> getTrafficData(String host, int port) {
        List<TrafficData> data = new ArrayList<>();

        try {
            PcapHandle handle = device.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 10);

            PacketListener listener = new PacketListener() {
                @Override
                public void gotPacket(PcapPacket packet) {
                    // here you should analyze the packet and create a TrafficData object
                    TrafficData trafficData = new TrafficData();
                    trafficData.setTimestamp(LocalDateTime.now());
                    trafficData.setHost(host);
                    trafficData.setPort(port);
                    trafficData.setBytesIn(packet.length()); // this is a simplification, in reality you should differentiate between incoming and outgoing traffic
                    trafficData.setBytesOut(0); // this is a simplification, in reality you should differentiate between incoming and outgoing traffic

                    data.add(trafficData);
                    repository.save(trafficData);
                }
            };

            handle.loop(10, listener);

        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

}
