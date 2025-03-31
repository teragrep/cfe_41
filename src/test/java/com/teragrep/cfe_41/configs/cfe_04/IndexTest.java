package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class IndexTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Index.class).verify();
    }

    @Test
    void testIdealCase() {
        final Index index = new Index(
                "name",
                "home:%s",
                "cold:%s",
                "thaw:%s",
                "summary"
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("name", "name")
                .add("repFactor", "auto")
                .add("disabled", "false")
                .add("homePath", "home:name")
                .add("coldPath", "cold:name")
                .add("thawedPath", "thaw:name")
                .add("override", Json.createArrayBuilder().add(Json.createObjectBuilder().add("key","frozenTimePeriodInSecs").add("value", "2592000")))
                .add("summaryHomePath", "summary")
                .build();

        Assertions.assertEquals(expected, index.asJsonStructure());
    }
}
