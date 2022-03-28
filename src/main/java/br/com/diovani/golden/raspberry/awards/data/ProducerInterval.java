package br.com.diovani.golden.raspberry.awards.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "producer")
public class ProducerInterval {

	@Getter
	@Setter
	@NonNull
	private String producer;
	@Getter
	@Setter
	private Integer interval;
	@Getter
	@Setter
	private Integer previousWin;
	@Getter
	@Setter
	private Integer followingWin;

	@JsonIgnore
	public boolean isInvalid() {
		return interval == null || followingWin == null;
	}

}
