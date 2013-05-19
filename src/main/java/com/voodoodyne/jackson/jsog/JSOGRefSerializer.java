package com.voodoodyne.jackson.jsog;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Knows how to take a JSOGRef and print it as @id or @ref as appropriate.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class JSOGRefSerializer extends JsonSerializer<JSOGRef>
{
	@Override
	public void serialize(JSOGRef value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		if (value.used) {
			jgen.writeStartObject();
			jgen.writeObjectField(JSOGRef.REF_KEY, value.ref);
			jgen.writeEndObject();
		} else {
			value.used = true;
			jgen.writeObject(value.ref);
		}
	}

}
