package br.com.diovani.golden.raspberry.awards.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "producer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "name")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Producer {

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Getter
	@Setter
	@Column(unique = true)
	private String name;
	@Getter
	@Setter
	@Default
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "movie_producer", joinColumns = {
			@JoinColumn(name = "producer_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "movie_id", referencedColumnName = "id") })
	private List<Movie> movies = new ArrayList<>();
	
}
