package com.teragrep.cfe_41.sourcetypes;

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cfe_41.fakes.CaptureGroupRequestFake;
import com.teragrep.cfe_41.fakes.CaptureRequestFake;
import com.teragrep.cfe_41.fakes.EmptyCaptureGroupRequestFake;
import com.teragrep.cfe_41.media.JsonMedia;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public final class SourcetypesJsonTest {
    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(SourcetypesJson.class).verify();
    }

    @Test
    void testSourcetypesJson() {
        final SourcetypesJson sourcetypesJson = new SourcetypesJson(
                new ApiConfig(Map.of()), "captureGroup1",
                new CaptureGroupRequestFake(), new CaptureRequestFake()
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("table", Json.createArrayBuilder().add(Json.createObjectBuilder().add("index", "fake-tag").add("value", "fake-source")))
                .build();

        Assertions.assertEquals(expected, Assertions.assertDoesNotThrow(() -> sourcetypesJson.asPrintableCaptures().print(new JsonMedia()).asJson()));
    }

    @Test
    void testSourcetypesJsonEmpty() {
        final SourcetypesJson sourcetypesJson = new SourcetypesJson(
                new ApiConfig(Map.of()), "captureGroup1",
                new EmptyCaptureGroupRequestFake(), new CaptureRequestFake()
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("table", JsonValue.EMPTY_JSON_ARRAY)
                .build();

        Assertions.assertEquals(expected, Assertions.assertDoesNotThrow(() -> sourcetypesJson.asPrintableCaptures().print(new JsonMedia()).asJson()));
    }
}
