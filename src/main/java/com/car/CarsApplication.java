package com.car;

import com.car.model.Car;
import com.car.model.Race;
import com.car.model.RaceTrack;
import com.car.service.RaceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.Console;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.car.utils.JsonUtil.asFormattedJson;

@Slf4j
@SpringBootApplication

public class CarsApplication implements CommandLineRunner {

    @Autowired
    private  ApplicationContext appContext;
    @Autowired
    private  RaceService raceService;

    String  raceInput = "";

    public static void main(String[] args) {
        SpringApplication.run(CarsApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        List<String> tracks = new ArrayList<>();
        Console console = System.console();
        List<RaceTrack> trackList = raceService.getAllRaceTracks();

        console.printf("Available race tracks to choose from :\n");
        trackList.forEach(raceTrack -> {
            console.printf("(%d)  %d \n",
                    raceTrack.getId(),
                    raceTrack.getValue());
        });

        boolean canRace = false;

        while (true) {
            if (tracks.size() >= 2) {
                if (raceInput.equals("0")){
                    canRace = true;
                    break;
                }
                raceInput = console.readLine("Enter race track number or 0 to start the race ");
            } else if (raceInput.equals("999")) break;
            else {
                raceInput = console.readLine("Enter race track number or 999 to quit ");
            }

            Optional<BigInteger> track = trackList.stream()
                    .filter(raceTrack -> String.valueOf(raceTrack.getId()).equals(raceInput))
                    .map(RaceTrack::getValue).findFirst();
            if (track.isPresent()) {
                tracks.add(track.get().toString());
            } else if (!raceInput.equals("0")){
                console.printf("Invalid race track number\n");
            }
        }
        if (canRace) {
            console.printf("\n");

            List<Car> cars = raceService.getAllCars();
            // print all cars available to race
            printCarsAvailableToRace(console, cars);
            //pause until user presses ENTER
            console.readLine("\nPlease press ENTER to start the race\n");
            // start the races
            List<Race> races = raceService.startAll(tracks);
            // print all the race stats on the console
            printRaceStats(races, console);
        } else {
            console.printf("bye\n\n");
        }
        initiateShutdown(0);

    }

    private void printCarsAvailableToRace(Console console, List<Car> cars) {
        console.printf("Racing cars list\n");
        String formatter = "%1$4s %2$10s %3$8s %4$8s %5$8s%n";
        console.printf(formatter, "Name", "Acceleration", "Braking", "Top speed", "Cornering");
        cars.forEach(car -> {
            console.printf(formatter, car.getName(), car.getAcceleration(), car.getBraking(), car.getTopSpeed(), car.getCornering());
        });
    }

    private void printRaceStats(List<Race> races, Console console) {
        String formatterWithScore = "%1$4s %2$10s %3$8s %4$8s %5$8s %6$8s%n";

        for (Race race : races) {
            // print race details then followed by stats i.e The top 3 and all the participants
            console.printf("\nFinished race on track run: %1$4s. \n\n",
                    asFormattedJson(race.getTrack()));

            console.printf("The top 3 are:\n");
            console.printf(formatterWithScore, "Name", "Score", "Acceleration", "Braking", "Top speed", "Cornering");
            race.getTopThree().forEach(contestant -> {
                console.printf(formatterWithScore, contestant.getCar().getName(), contestant.getScore(), contestant.getCar().getAcceleration(), contestant.getCar().getBraking(), contestant.getCar().getTopSpeed(), contestant.getCar().getCornering());
            });

            console.printf("\nAll contestants on this race:\n");
            console.printf(formatterWithScore, "Name", "Score", "Acceleration", "Braking", "Top speed", "Cornering");
            race.getRankings().forEach(contestant -> {
                console.printf(formatterWithScore, contestant.getCar().getName(), contestant.getScore(), contestant.getCar().getAcceleration(), contestant.getCar().getBraking(), contestant.getCar().getTopSpeed(), contestant.getCar().getCornering());
            });
        }
    }

    //Method to shutdown the application
    public void initiateShutdown(int returnCode)
    {
        System.out.println("Shutting Down Application");

        SpringApplication.exit(appContext, () -> returnCode);
    }
}
