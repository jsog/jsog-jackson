package com.voodoodyne.jackson.jsog;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 * Knows how to take either form of a JSOGRef (string or {@ref:string} and convert it back into a JSOGRef.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class JSOGRefDeserializer extends JsonDeserializer<JSOGRef>
{
	@Override
	public JSOGRef deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAsTree();
		if (node.isTextual()) {
			return new JSOGRef(node.asText());
		} else {
			return new JSOGRef(node.get(JSOGRef.REF_KEY).asText());
		}
	}

}
