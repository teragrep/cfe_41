package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public final class SourcetypeTest {
    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Sourcetype.class).verify();
    }

    @Test
    void testIdealCase() {
        final Sourcetype sourcetype = new Sourcetype(
                "name",
                "20",
                "category",
                "desc",
                List.of(),
                "12345",
                "false",
                "",
                "false",
                ""
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("name","name")
                .add("max_days_ago", "20")
                .add("category", "category")
                .add("description", "desc")
                .add("transforms", JsonValue.EMPTY_JSON_ARRAY)
                .add("truncate", "12345")
                .add("freeform_indexer_enabled", "false")
                .add("freeform_indexer_text", "")
                .add("freeform_lb_enabled", "false")
                .add("freeform_lb_text", "")
                .build();

        Assertions.assertEquals(expected, sourcetype.asJsonStructure());
    }
}
