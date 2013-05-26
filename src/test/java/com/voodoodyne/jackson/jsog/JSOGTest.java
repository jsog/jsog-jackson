package com.voodoodyne.jackson.jsog;

import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSOGTest {

	@JsonIdentityInfo(generator=JSOGGenerator.class)
	public static class Inner {
		public String bar;
	}

	@JsonIdentityInfo(generator=JSOGGenerator.class)
	public static class Outer {
		public String foo;
		public Inner inner1;
		public Inner inner2;
	}

	/** Expected output */
	private static final String JSOGIFIED = "{\"@id\":\"1\",\"foo\":\"foo\",\"inner1\":{\"@id\":\"2\",\"bar\":\"bar\"},\"inner2\":{\"@ref\":\"2\"}}";

	/** */
	ObjectMapper mapper = new ObjectMapper();

	/** */
	@Test
	public void serializationWorks() throws Exception {
		Outer outer = new Outer();
		outer.foo = "foo";
		outer.inner1 = outer.inner2 = new Inner();
		outer.inner1.bar = "bar";

		String jsog = mapper.writeValueAsString(outer);

		System.out.println("Serialized to: " + jsog);

		assert jsog.equals(JSOGIFIED);
	}

	/**
	 * This does not currently work. Help fixing it would be greatly apprecaited!
	 */
	//@Test
	public void deserializationWorks() throws Exception {
		Outer outer = mapper.readValue(JSOGIFIED, Outer.class);

		assert outer.inner1 == outer.inner2;
	}
}
