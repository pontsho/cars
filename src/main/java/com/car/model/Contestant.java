package com.car.model;

import lombok.Getter;

@Getter
public class Contestant {

    private final Car car;
    private int score;

    public Contestant(Car car) {
        this.car = car;
    }

    public void race(RaceTrack track) {
        score = track.getPath().stream()
                .mapToInt(car::transition)
                .sum();
    }

}
