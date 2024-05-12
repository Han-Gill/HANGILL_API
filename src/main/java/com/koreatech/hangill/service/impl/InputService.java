package com.koreatech.hangill.service.impl;

import com.koreatech.hangill.domain.AccessPoint;
import com.koreatech.hangill.domain.Building;
import com.koreatech.hangill.domain.Node;
import com.koreatech.hangill.dto.NodeSearch;
import com.koreatech.hangill.dto.request.BuildFingerprintRequest;
import com.koreatech.hangill.dto.request.NodeSearchRequest;
import com.koreatech.hangill.dto.request.SignalRequest;
import com.koreatech.hangill.repository.AccessPointRepository;
import com.koreatech.hangill.repository.BuildingRepository;
import com.koreatech.hangill.service.AccessPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class InputService {
    List<String> datas = new ArrayList<>();
    private final BuildingRepository buildingRepository;
    private final AccessPointService accessPointService;
    private final AccessPointRepository accessPointRepository;
    private final NodeServiceImpl nodeService;

    public void initData() throws IOException {
        System.setIn(new java.io.FileInputStream("/Users/jeongseong-o/2024-1/hanGill/2공_모든_AP_핑거프린팅2.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            datas.add(line);
        }
//        for (int i = 0; i < datas.size(); i++) {
//            System.out.println(i + ":" + datas.get(i));
//        }
        br.close();
    }

    public NodeSearchRequest parseNode(String data) {
        String[] arr = data.split(", ");
//        System.out.println(Arrays.toString(arr));
        String buildingName = arr[0].split(": ")[1];
        Integer floor = Integer.parseInt(arr[1].split(": ")[1]);
        Integer number = Integer.parseInt(arr[2].split(": ")[1]);
        return new NodeSearchRequest(buildingName, number, floor);
    }

    public List<SignalRequest> parseSignal(String data) {
        List<SignalRequest> list = new ArrayList<>();
        String[] arr = data.split(";");
        for (String s : arr) {
            String[] tmp = s.split(",");
            list.add(new SignalRequest(tmp[0], tmp[1], Integer.parseInt(tmp[2])));
        }
        return list;
    }

    public void saveAccessPoints() {
        Map<String, SignalRequest> map = new HashMap<>();
        String buildingName = null;
        for (int i = 0; i < datas.size(); i += 3) {
            NodeSearchRequest nodeSearchRequest = parseNode(datas.get(i));
            if (buildingName == null) buildingName = nodeSearchRequest.getBuildingName();

            List<SignalRequest> signalRequests = parseSignal(datas.get(i + 1));
            for (SignalRequest signalRequest : signalRequests) {
                map.put(signalRequest.getMac(), signalRequest);
            }

        }
        Building building = buildingRepository.findOne(buildingName);
        for (SignalRequest signalRequest : map.values()) {
            accessPointRepository.save(new AccessPoint(signalRequest.getSsid(), signalRequest.getMac(), building));
        }
    }

    public void saveFingerPrint() {
        for (int i = 0; i < datas.size(); i += 3) {
            NodeSearchRequest nodeSearchRequest = parseNode(datas.get(i));
            Building building = buildingRepository.findOne(nodeSearchRequest.getBuildingName());
            Node node = nodeService.findOne(new NodeSearch(
                    building.getId(),
                    nodeSearchRequest.getNumber(),
                    nodeSearchRequest.getFloor()
            ));
            List<SignalRequest> signalRequests = parseSignal(datas.get(i + 1));
            nodeService.buildFingerPrint(new BuildFingerprintRequest(node.getId(), signalRequests));
        }
    }
    public void find() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < datas.size(); i += 3) {
            NodeSearchRequest nodeSearchRequest = parseNode(datas.get(i));
            Building building = buildingRepository.findOne(nodeSearchRequest.getBuildingName());

            try {
                Node node = nodeService.findOne(new NodeSearch(
                        building.getId(),
                        nodeSearchRequest.getNumber(),
                        nodeSearchRequest.getFloor()
                ));
            }catch (Exception e) {
                sb.append(nodeSearchRequest.getNumber()).append("번 ").append(nodeSearchRequest.getFloor()).append("층").append("\n");
            }
        }
        System.out.println(sb);
    }
}
