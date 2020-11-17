package com.car.service;

import com.car.model.Car;
import com.car.model.Contestant;
import com.car.model.Race;
import com.car.model.RaceTrack;
import com.car.repository.CarsRepository;
import com.car.repository.RaceRepository;
import com.car.repository.RaceTrackRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RaceService {

    private final CarsRepository carsRepository;
    private final RaceRepository raceRepository;
    private  RaceTrackRepository raceTrackRepository;

    public Race start(String track) {
        List<Contestant> contestants = carsRepository.findAll()
                .stream()
                .map(Contestant::new)
                .collect(Collectors.toList());
        Race race = new Race(track, contestants);
        race.start();
        //In case we want to store race result: will implement phase 2 :)
        return raceRepository.add(race);
    }

    public List<Race> startAll(List<String> tracks) {
        return tracks.stream()
                .map(this::start)
                .collect(Collectors.toList());
    }

    public List<RaceTrack> getAllRaceTracks() {
        return raceTrackRepository.findAll();
    }
    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }

}
