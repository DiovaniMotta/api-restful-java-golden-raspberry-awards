package br.com.diovani.golden.raspberry.awards.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BooleanCSVDeserializer extends JsonDeserializer<Boolean> {

	@Override
	public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		switch (p.getText()) {
		case "yes":
		case "Yes":
		case "YES":
		case "YEs":
		case "yES":
		case "y":
		case "Y":
		case "1":	
			return true;
		default:
			return false;
		}
	}



}
