package com.car.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrackState {

    CORNER(0),
    STRAIGHT(1);

    private final int value;

    public static TrackState fromValue(int value) {
        for (TrackState trackState : values()) {
            if (trackState.value == value) {
                return trackState;
            }
        }
        return null;
    }
}
