package br.com.diovani.golden.raspberry.awards.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.diovani.golden.raspberry.awards.data.AwardInterval;
import br.com.diovani.golden.raspberry.awards.data.ProducerInterval;
import br.com.diovani.golden.raspberry.awards.entities.Movie;
import br.com.diovani.golden.raspberry.awards.entities.Producer;
import br.com.diovani.golden.raspberry.awards.repositories.MovieRepository;

@Service
public class AwardService {

	private static final Integer RECORDS_MAX = 2;

	@Autowired
	private MovieRepository movieRepository;

	public AwardInterval findAwardInterval() {
		List<ProducerInterval> intervals = new ArrayList<>();
		List<Movie> moviesWinners = movieRepository.findByWinner(true);
	
		for (Movie movie : moviesWinners) {
			for (Producer producer : movie.getProducers()) {
				ProducerInterval producerInterval = new ProducerInterval(producer.getName());
				if (!intervals.contains(producerInterval)) {
					producerInterval.setPreviousWin(movie.getYear());
					intervals.add(producerInterval);
					continue;
				}
				int index = intervals.indexOf(producerInterval);
				ProducerInterval interval = intervals.get(index);
				interval.setFollowingWin(movie.getYear());
				int diff = interval.getFollowingWin() - interval.getPreviousWin();
				interval.setInterval(diff);
				intervals.set(index, interval);
			}
		}

		removeProducersWithOnlyOneAward(intervals);

		List<ProducerInterval> minInterval = getMinInterval(intervals);
		List<ProducerInterval> maxInterval = getMaxInterval(intervals);

		return new AwardInterval(minInterval, maxInterval);
	}

	private List<ProducerInterval> getMaxInterval(List<ProducerInterval> intervals) {
		return intervals.stream().sorted(Comparator.comparingInt(ProducerInterval::getInterval).reversed())
				.limit(RECORDS_MAX).collect(Collectors.toList());
	}

	protected List<ProducerInterval> getMinInterval(List<ProducerInterval> intervals) {
		return intervals.stream().sorted(Comparator.comparingInt(ProducerInterval::getInterval)).limit(RECORDS_MAX)
				.collect(Collectors.toList());
	}

	protected void removeProducersWithOnlyOneAward(List<ProducerInterval> intervals) {
		Iterator<ProducerInterval> it = intervals.iterator();
		while (it.hasNext()) {
			ProducerInterval producerInterval = it.next();
			if (producerInterval.isInvalid()) {
				it.remove();
			}
		}
	}
}
