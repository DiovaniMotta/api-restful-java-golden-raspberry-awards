package br.com.diovani.golden.raspberry.awards;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static br.com.diovani.golden.raspberry.awards.builders.ProducerIntervalBuilder.oneProducerInterval;
import br.com.diovani.golden.raspberry.awards.data.AwardInterval;
import br.com.diovani.golden.raspberry.awards.data.ProducerInterval;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoldenRaspberryAwardsApplicationTests {

	private static final String SERVER = "http://localhost:";
	private static final String END_POINT = "/golden-raspberry-awards/award/range";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldGetAwardInterval() {
		final String URL = SERVER + port + END_POINT;

		ProducerInterval minOne = oneProducerInterval().producer("Joel Silver").previousWin(1990).followingWin(1991)
				.getProducerInterval();
		ProducerInterval minTwo = oneProducerInterval().producer("Bo Derek").previousWin(1984).followingWin(1990)
				.getProducerInterval();

		ProducerInterval maxOne = oneProducerInterval().producer("Matthew Vaughn").previousWin(2002).followingWin(2015)
				.getProducerInterval();
		ProducerInterval maxTwo = oneProducerInterval().producer("Buzz Feitshans").previousWin(1985).followingWin(1994)
				.getProducerInterval();

		ResponseEntity<AwardInterval> responseEntity = restTemplate.getForEntity(URL, AwardInterval.class);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getMin()).size().isEqualByComparingTo(2);
		assertThat(responseEntity.getBody().getMin()).containsAnyOf(minOne, minTwo);
		assertThat(responseEntity.getBody().getMax()).size().isEqualByComparingTo(2);
		assertThat(responseEntity.getBody().getMax()).containsAnyOf(maxOne, maxTwo);
	}

}
