package com.car.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RaceTrack {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private BigInteger value;

    public RaceTrack(BigInteger value) {
        //TODO validate input
        this.value = value;
    }

    public RaceTrack(String value) {
        //TODO validate input
        this(new BigInteger(value));
    }

    public List<TrackState> getPath() {
        List<TrackState> states = new ArrayList<>();
        String trackAsString = value.toString();
        for (int i = 0; i < trackAsString.length(); i++) {
            TrackState trackState = TrackState.fromValue(Character.getNumericValue(trackAsString.charAt(i)));
            if (Objects.nonNull(trackState))
                states.add(trackState);
        }
        return states;
    }

}
