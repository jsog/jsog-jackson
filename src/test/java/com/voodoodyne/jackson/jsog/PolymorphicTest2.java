package com.voodoodyne.jackson.jsog;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PolymorphicTest2 {
    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class SomeClass {
        public AbstractClass abstractClass;
    }

    @JsonSubTypes({
            @JsonSubTypes.Type(value = SubClass.class, name = "sub"),
            @JsonSubTypes.Type(value = SubClass2.class, name = "sub2")
    })
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
    private abstract static class AbstractClass {
        public String name;
    }

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class SubClass extends AbstractClass {
        public List<SomeClass> someClasses;
    }

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class SubClass2 extends AbstractClass {
    }

    @Test
    public void polymorphicTest2() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<SomeClass> serializing = new ArrayList<SomeClass>();

        SubClass subClass = new SubClass();
        subClass.someClasses = new ArrayList<SomeClass>();
        subClass.name = "sub class";

        SubClass2 subClass2 = new SubClass2();
        subClass2.name = "sub2 class";

        SomeClass someClass = new SomeClass();
        someClass.abstractClass = subClass;
        subClass.someClasses.add(someClass);
        serializing.add(someClass);


        someClass = new SomeClass();
        someClass.abstractClass = subClass;
        subClass.someClasses.add(someClass);
        serializing.add(someClass);

        someClass = new SomeClass();
        someClass.abstractClass = subClass;
        subClass.someClasses.add(someClass);
        serializing.add(someClass);

        SomeClass someOtherClass = new SomeClass();
        someOtherClass.abstractClass = subClass2;
        serializing.add(someOtherClass);


        String json;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        objectMapper.writer().writeValue(baos, serializing);

        json = baos.toString();

        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        SomeClass[] result = objectMapper.reader(SomeClass[].class).readValue(stream);
    }
}
