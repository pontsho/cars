package com.car.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int braking;
    private int topSpeed;
    private int cornering;
    private int acceleration;

    public int transition(TrackState trackState) {
        switch (trackState) {
            case STRAIGHT:
                return acceleration + topSpeed;
            case CORNER:
                return braking + cornering;
            default:
                throw new IllegalArgumentException("Invalid track state" + trackState);
        }
    }
}
