package com.koreatech.hangill.service;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Fingerprint;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.request.NodePositionRequest;
import com.koreatech.hangill.dto.request.SignalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class FindPositionService {
    private final int RSSI_OFFSET = 100;

    abstract public Node findPosition(NodePositionRequest request);

    @Data
    @AllArgsConstructor
    protected static class Position {
        private Node node;
        private double weight;
    }

    protected Map<String, Integer> buildDiff(List<AccessPoint> aps) {
        Map<String, Integer> diff = new HashMap<>();
        for (AccessPoint accessPoint : aps) {
            diff.put(accessPoint.getMac(), 0);
        }
        return diff;
    }

    protected double calculateWeight(Map<String, Integer> diff, Node node, List<SignalRequest> signals) {
        List<Fingerprint> fingerprints = node.getFingerprints();
        for (Fingerprint fingerprint : fingerprints) {
            String mac = fingerprint.getAccessPoint().getMac();
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) + (fingerprint.getRssi() + RSSI_OFFSET));
        }
        for (SignalRequest signal : signals) {
            String mac = signal.getMac();
            if (diff.containsKey(mac)) diff.put(mac, diff.get(mac) - (signal.getRssi() + RSSI_OFFSET));
        }

        int total_score = 0;
        for (Integer score : diff.values()) {
            total_score += score * score;
        }
        return Math.sqrt(total_score);
    }

    protected void loggingSignal(NodePositionRequest request, Node node, Set<String> runningAPs) {
        StringBuilder logging = new StringBuilder();
        logging.append("\n==========================User Info==========================").append("\n");
        for (SignalRequest signal : request.getSignals()) {
            if (runningAPs.contains(signal.getMac())) logging.append(signal).append("\n");
        }

        logging.append(String.format("==========================Node %d floor %d==========================", node.getNumber(), node.getFloor())).append("\n");
        for (Fingerprint fingerprint : node.getFingerprints()) {
            if (runningAPs.contains(fingerprint.getAccessPoint().getMac()))
                logging.append("ssid : ").append(fingerprint.getAccessPoint().getSsid()).append(" ")
                        .append("mac : ").append(fingerprint.getAccessPoint().getMac()).append(" ")
                        .append("rssi : ").append(fingerprint.getRssi()).append(" \n");
        }
        log.info("{}", logging);
    }
}
