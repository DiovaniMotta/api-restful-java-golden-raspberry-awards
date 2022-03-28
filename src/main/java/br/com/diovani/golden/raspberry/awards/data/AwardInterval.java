package br.com.diovani.golden.raspberry.awards.data;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AwardInterval {

	@Default
	@Getter
	private List<ProducerInterval> min = new ArrayList<>(2);
	@Default
	@Getter
	private List<ProducerInterval> max = new ArrayList<>(2);
}
