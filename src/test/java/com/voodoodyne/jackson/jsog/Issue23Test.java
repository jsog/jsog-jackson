package com.voodoodyne.jackson.jsog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Issue23Test {

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    public static class TestType {
        public String value;
    }

    @Test
    public void shouldDeserializeWithFailOnTrailingTokensFeatureEnabled() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        tryDeserializeAndAssert(objectMapper);
    }

    @Test
    public void shouldDeserializeWithFailOnTrailingTokensFeatureDisabled() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        tryDeserializeAndAssert(objectMapper);
    }

    private void tryDeserializeAndAssert(final ObjectMapper objectMapper) throws JsonProcessingException {
        // when
        List<TestType> deserialized = objectMapper.readValue(
                "[{\"@id\":\"1\",\"value\":\"testValue\"},{\"@ref\":\"1\"}]",
                new TypeReference<List<TestType>>() {});
        // then
        assertThat(deserialized.get(0).value, equalTo("testValue"));
        assertThat(deserialized.get(1).value, equalTo("testValue"));
    }
}
