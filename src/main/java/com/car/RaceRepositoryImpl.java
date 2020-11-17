package com.car;

import com.car.model.Race;
import com.car.repository.RaceRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RaceRepositoryImpl implements RaceRepository {
    @Override
    public Race add(Race race) {
        //TODO persist on DB
        return race;
    }
}
