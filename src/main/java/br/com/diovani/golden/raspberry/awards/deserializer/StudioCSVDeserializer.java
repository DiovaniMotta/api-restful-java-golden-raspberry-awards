package br.com.diovani.golden.raspberry.awards.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.diovani.golden.raspberry.awards.entities.Studio;

public class StudioCSVDeserializer extends JsonDeserializer<List<Studio>> {

	@Override
	public List<Studio> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		List<Studio> studios = new ArrayList<Studio>();
		if (!p.getText().isEmpty()) {
			String[] namesProducers = p.getText().replace(" and ", ",").split(",");
			for (String name : namesProducers) {
				if (!name.isEmpty()) {
					Studio studio = new Studio();
					studio.setName(name.trim());
					studios.add(studio);
				}
			}
		}
		return studios;
	}

}
