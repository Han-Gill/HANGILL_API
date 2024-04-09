package com.koreatech.hangill.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.koreatech.hangill.domain.OperationStatus.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AccessPoint {
    public AccessPoint(String ssid, String mac, Building building) {
        this.ssid = ssid;
        this.mac = mac;
        this.building = building;
        turnOn();
    }
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "access_point_id")
    private Long id;

    private String ssid;

    private String mac;

    @Enumerated(STRING)
    private OperationStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "building_id")
    private Building building;


    public void turnOff() {
        this.status = STOP;
    }
    public void turnOn() {
        this.status = RUNNING;
    }
}
