package br.com.diovani.golden.raspberry.awards.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.diovani.golden.raspberry.awards.entities.Producer;

public class ProducerCSVDeserializer extends JsonDeserializer<List<Producer>> {
	@Override
	public List<Producer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		List<Producer> producers = new ArrayList<Producer>();
		if (!p.getText().isEmpty()) {
			String[] namesProducers = p.getText().replace(" and ", ",").split(",");
			for (String name : namesProducers) {
				if (!name.isEmpty()) {
					Producer producer = new Producer();
					producer.setName(name.trim());
					producers.add(producer);
				}
			}
		}
		return producers;
	}

}
