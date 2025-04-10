package com.teragrep.cfe_41.configs.cfe_04;

import com.teragrep.cfe_41.configs.cfe_04.transform.Transform;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TransformTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Transform.class).verify();
    }

    @Test
    void testIdealCase() {
        final Transform transform = new Transform(
                "tf-name",
                false,
                true,
                "def",
                "dest-key",
                "[A-Za-z]+",
                "format"
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("name", "tf-name")
                .add("write_meta", false)
                .add("write_default_value", true)
                .add("default_value", "def")
                .add("dest_key", "dest-key")
                .add("regex", "[A-Za-z]+")
                .add("format", "format")
                .build();

        Assertions.assertEquals(expected, transform.asJsonStructure());
    }
}
