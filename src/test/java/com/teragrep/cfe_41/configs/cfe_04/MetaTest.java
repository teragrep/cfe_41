package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MetaTest {
    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Meta.class).verify();
    }

    @Test
    void testIdealCase() {
        final Meta meta = new Meta("fake-name");
        final JsonObject expected = Json.createObjectBuilder().add("name", "fake-name").build();
        Assertions.assertEquals(expected, meta.asJsonStructure());
    }

    @Test
    void testEmptyName() {
        final Meta meta = new Meta("");
        final JsonObject expected = Json.createObjectBuilder().add("name", "").build();
        Assertions.assertEquals(expected, meta.asJsonStructure());
    }
}
