package br.com.diovani.golden.raspberry.awards.builders;

import br.com.diovani.golden.raspberry.awards.data.ProducerInterval;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProducerIntervalBuilder {

	@Getter
	private ProducerInterval producerInterval;
	
	public static ProducerIntervalBuilder oneProducerInterval() {
		ProducerIntervalBuilder builder = new ProducerIntervalBuilder();
		builder.producerInterval = new ProducerInterval("");
		return builder;
	}
	
	public ProducerIntervalBuilder producer(String producer) {
		this.producerInterval.setProducer(producer);
		return this;
	}
	
	public ProducerIntervalBuilder previousWin(Integer previousWin) {
		this.producerInterval.setPreviousWin(previousWin);
		return this;
	}
	
	public ProducerIntervalBuilder followingWin(Integer followingWin) {
		this.producerInterval.setFollowingWin(followingWin);
		int interval = this.producerInterval.getFollowingWin() - this.producerInterval.getPreviousWin();
		this.producerInterval.setInterval(interval);
		return this;
	}
	
}
