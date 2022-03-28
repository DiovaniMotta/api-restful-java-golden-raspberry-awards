package br.com.diovani.golden.raspberry.awards.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.diovani.golden.raspberry.awards.entities.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

	public List<Movie> findByWinner(Boolean winner);
}