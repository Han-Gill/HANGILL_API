package com.koreatech.hangill.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Fingerprint {
    public Fingerprint(AccessPoint accessPoint, Integer rssi, LocalDateTime measurementDate) {
        changeAccessPoint(accessPoint);
        this.rssi = rssi;
        this.measurementDate = measurementDate;
    }
    @Id @GeneratedValue
    @Column(name = "fingerprint_id")
    private Long id;

    private Integer rssi;
    private LocalDateTime measurementDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "node_id")
    private Node node;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "access_point_id")
    private AccessPoint accessPoint;

    public void changeNode(Node node) {
        this.node = node;
    }
    public void changeAccessPoint(AccessPoint accessPoint) {
        this.accessPoint = accessPoint;
    }

}
