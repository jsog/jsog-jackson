package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PolymorphicTest {

	@JsonIdentityInfo(generator=JSOGGenerator.class)
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="@class")
	public static class Inner {
		public String bar;

		public Inner() {}
		public Inner(String bar) { this.bar = bar; }
	}

	public static class SubInner extends Inner {
		public String extra;

		public SubInner() {}
		public SubInner(String bar, String extra) {
			super(bar);
			this.extra = extra;
		}
	}

	@JsonIdentityInfo(generator=JSOGGenerator.class)
	public static class Outer {
		public String foo;
		public Inner inner1;
		public Inner inner2;
	}

	/** Expected output */
	private static final String JSOGIFIED = "{\"@id\":\"1\",\"foo\":\"foo\",\"inner1\":{\"@class\":\"com.voodoodyne.jackson.jsog.PolymorphicTest$SubInner\",\"@id\":\"2\",\"bar\":\"bar\",\"extra\":\"extra\"},\"inner2\":{\"@ref\":\"2\"}}";

	/** */
	ObjectMapper mapper = new ObjectMapper();

	/** */
	@Test
	public void serializationWorks() throws Exception {
		Outer outer = new Outer();
		outer.foo = "foo";
		outer.inner1 = outer.inner2 = new SubInner("bar", "extra");

		String jsog = mapper.writeValueAsString(outer);

		System.out.println("Serialized to: " + jsog);

		assertThat(jsog, equalTo(JSOGIFIED));
	}

	/**
	 */
	@Test
	public void deserializationWorks() throws Exception {
		Outer outer = mapper.readValue(JSOGIFIED, Outer.class);

		assert outer.inner1 == outer.inner2;
	}
}
