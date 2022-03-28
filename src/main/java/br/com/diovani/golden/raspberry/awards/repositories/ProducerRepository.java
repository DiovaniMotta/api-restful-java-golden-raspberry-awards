package br.com.diovani.golden.raspberry.awards.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.diovani.golden.raspberry.awards.entities.Producer;

public interface ProducerRepository extends CrudRepository<Producer, Long> {
	
}
