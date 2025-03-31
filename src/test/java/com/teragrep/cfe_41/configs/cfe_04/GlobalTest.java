package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class GlobalTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Global.class).verify();
    }

    @Test
    void testIdealCase() {
        final Global global = new Global(
                "12345",
                "lci",
                "lci-malformed",
                "20"
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("truncate", "12345")
                .add("last_chance_index", "lci")
                .add("last_chance_index_malformed", "lci-malformed")
                .add("max_days_ago", "20")
                .build();

        Assertions.assertEquals(expected, global.asJsonStructure());
    }
}
