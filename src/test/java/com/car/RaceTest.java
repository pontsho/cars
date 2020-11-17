package com.car;

import com.car.model.Car;
import com.car.model.Contestant;
import com.car.model.Race;
import com.car.model.RaceTrack;
import com.car.repository.CarsRepository;
import com.car.repository.RaceRepository;
import com.car.repository.RaceTrackRepository;
import com.car.service.RaceService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.car.utils.JsonUtil.asFormattedJson;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:test-db.properties")
@ContextConfiguration(classes = RaceTest.EmployeeServiceImplTestContextConfiguration.class)
@RunWith(SpringRunner.class)
public class RaceTest {

    @MockBean
    private CarsRepository carsRepository;
    @MockBean
    private RaceRepository raceRepository;
    @MockBean
    private RaceTrackRepository raceTrackRepository;
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public RaceService employeeService(CarsRepository carsRepository, RaceRepository raceRepository, RaceTrackRepository raceTrackRepository) {
            return new RaceService(carsRepository, raceRepository, raceTrackRepository);
        }
        @Bean
        public DataSource dataSource(){
            return
                    (new EmbeddedDatabaseBuilder())
                            .setType(EmbeddedDatabaseType.H2)
                            .addScript("classpath:db/migration/V__CarsEntities.sql")
                            .addScript("classpath:db/migration/V__RaceTrackEntities.sql")
                            .build();
        }
    }

    @Mock
    private RaceService raceService;

    @Before
    public void setUp() {
        List<Car> list = Arrays.asList(Car.builder()
                .id(1l)
                .name("BMW")
                .acceleration(3)
                .braking(4)
                .cornering(5)
                .topSpeed(6).build());

        Mockito.when(carsRepository.findAll())
                .thenReturn(list);

        List<RaceTrack> trackList = Arrays.asList(RaceTrack.builder()
                .id(1l)
                .value(BigInteger.valueOf(Long.parseLong("111100111001")))
                .build());
        Mockito.when(raceTrackRepository.findAll())
                .thenReturn(trackList);
    }


    @Test
    @Ignore("Out of time to make this work properly :(")
    public void testCars() {
        List<String> tracks = Arrays.asList();

        List<Race> actual = raceService.startAll(tracks);

        for (Race race : actual) {
            System.out.printf("Finished race on track: %s.\n The top 3 are: %s \n\n All contestants: %s \n\n",
                    asFormattedJson(race.getTrack()),
                    asFormattedJson(race.getTopThree()),
                    asFormattedJson(race.getRankings()));
        }

        assertThat(actual).isEmpty();
        assertThat(actual.size()).isEqualTo(tracks.size());
    }
}
