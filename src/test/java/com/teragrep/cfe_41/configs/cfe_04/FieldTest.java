package com.teragrep.cfe_41.configs.cfe_04;

import com.teragrep.cfe_41.configs.cfe_04.field.Field;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class FieldTest {
    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Field.class).verify();
    }

    @Test
    void testIdealCase() {
        final Field field = new Field(
                "field-name"
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("name", "field-name")
                .build();

        Assertions.assertEquals(expected, field.asJsonStructure());
    }
}
