package br.com.diovani.golden.raspberry.awards.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.diovani.golden.raspberry.awards.deserializer.BooleanCSVDeserializer;
import br.com.diovani.golden.raspberry.awards.deserializer.ProducerCSVDeserializer;
import br.com.diovani.golden.raspberry.awards.deserializer.StudioCSVDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = { "title", "year" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Setter
	private Integer year;
	@Getter
	@Setter
	@Column(unique = true)
	private String title;
	@Getter
	@Setter
	@Default
	@ManyToMany
	@JsonDeserialize(using = StudioCSVDeserializer.class)
	@JoinTable(name = "movie_studio", joinColumns = {
			@JoinColumn(name = "movie_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "studio_id", referencedColumnName = "id") })
	private List<Studio> studios = new ArrayList<>();
	@Getter
	@Setter
	@Default
	@ManyToMany
	@JsonDeserialize(using = ProducerCSVDeserializer.class)
	@JoinTable(name = "movie_producer", joinColumns = {
			@JoinColumn(name = "movie_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "producer_id", referencedColumnName = "id") })
	private List<Producer> producers = new ArrayList<>();
	@Getter
	@Setter
	@JsonDeserialize(using = BooleanCSVDeserializer.class)
	private boolean winner;

	public void add(Producer producer) {
		int index = producers.indexOf(producer);
		if (index != -1) {
			this.producers.set(index, producer);
		}
	}

	public void add(Studio studio) {
		int index = studios.indexOf(studio);
		if (index != -1) {
			this.studios.set(index, studio);
		}
	}
}
