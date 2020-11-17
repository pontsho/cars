package com.car.repository;

import com.car.model.RaceTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceTrackRepository extends JpaRepository<RaceTrack, Long> {
}
