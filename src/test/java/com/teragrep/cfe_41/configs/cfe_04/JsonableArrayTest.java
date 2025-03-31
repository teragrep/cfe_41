package com.teragrep.cfe_41.configs.cfe_04;

import com.teragrep.cfe_41.fakes.JsonableFake;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public final class JsonableArrayTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(JsonableArray.class).verify();
    }

    @Test
    void testIdealCase() {
        final JsonableArray jsonableArray = new JsonableArray(
                List.of(
                    new JsonableFake(),
                        new JsonableFake()
                )
        );

        final JsonArray expected = Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("fake-key", "fake-value").build())
                .add(Json.createObjectBuilder().add("fake-key", "fake-value").build())
                .build();

        Assertions.assertEquals(expected, jsonableArray.asJsonStructure());
    }

    @Test
    void testEmptyList() {
        final JsonableArray jsonableArray = new JsonableArray(
                Collections.emptyList()
        );

        final JsonArray expected = JsonValue.EMPTY_JSON_ARRAY;

        Assertions.assertEquals(expected, jsonableArray.asJsonStructure());
    }

}
