package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Date;

/**
 * Demonstrates the inability to round trip serialize deserialize when using DefaultTyping.NON_FINAL,
 * and version fixes for NON_CONCRETE strategies.
 */
public class Issue15Test {

  @Test // always works
  public void testIssue5WorkaroundObject() throws IOException {
    testWithPolymorphicStrategy(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);

  }
  @Test // works with Jackson 2.5.4+
  public void testIssue5WorkaroundObjectAndNonConcrete() throws IOException {
    testWithPolymorphicStrategy(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);

  }
  @Test // works with Jackson 2.5.4+
  public void testIssue5WorkaroundNonConcreteAndArrays() throws IOException {
    testWithPolymorphicStrategy(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);

  }
  @Test // fails on Jackson 2.8.8 (no working version as of this date) unless JSOGRef is a final class
  public void testIssue5WorkaroundNonFinal() throws IOException {
    testWithPolymorphicStrategy(ObjectMapper.DefaultTyping.NON_FINAL);
  }

  private void testWithPolymorphicStrategy(ObjectMapper.DefaultTyping typingStragegy) throws IOException {
    Thing t = new Thing();
    t.setName("foo");

    t.setDate(new Date());

    ObjectMapper mapper = new ObjectMapper();
    mapper.enableDefaultTyping(typingStragegy);

    // issue is JSOG specific since removing this line causes all tests to
    // pass in 2.5.4 (did not test earlier)
    mapper.addMixIn(Object.class, JSOGMixin.class);

    // No difference if we use the module workaround from Issue #5

//    SimpleModule jModule = new SimpleModule();
//    jModule.setMixInAnnotation(Object.class, JSOGMixin.class);
//    mapper.registerModule(jModule);

    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
    mapper.readValue(json, Thing.class);
  }

  @JsonIdentityInfo(generator = JSOGGenerator.class) private static class JSOGMixin {}
}
