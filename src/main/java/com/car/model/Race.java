package com.car.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class Race {

    private final RaceTrack track;
    private final List<Contestant> contestants;

    public Race(String track, List<Contestant> contestants) {
        this(new RaceTrack(track), contestants);
    }

    public Race(RaceTrack track, List<Contestant> contestants) {
        this.track = track;
        this.contestants = contestants;
    }

    public void start() {
        contestants.forEach(contestant -> contestant.race(track));
    }

    public List<Contestant> getRankings() {
        return contestants.stream()
                .sorted(Comparator.comparing(Contestant::getScore).reversed())
                .collect(Collectors.toList());
    }

    public List<Contestant> getTopThree() {
        return getRankings().stream()
                .limit(3)
                .collect(Collectors.toList());
    }
}
