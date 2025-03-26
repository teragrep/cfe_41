package com.teragrep.cfe_41.sourcetypes;

import com.teragrep.cfe_41.fakes.CaptureFake;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class SourcetypesFileTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(SourcetypesFile.class).verify();
    }

    @Test
    void testIdealCase() {
        final String targetPath = "src/test/resources/captureGroup1_sourcetypes.json";
        SourcetypesFile sourcetypesFile = new SourcetypesFile(
            new PrintableSourcetypesCaptures(List.of(new CaptureFake())),
                "captureGroup1",
                "src/test/resources"
        );

        Assertions.assertDoesNotThrow(sourcetypesFile::save);

        final Reader fileReader = Assertions.assertDoesNotThrow(() -> new FileReader(targetPath));
        final JsonReader jsonReader = Json.createReader(fileReader);
        final JsonObject objectFromFile = jsonReader.readObject();

        final JsonObject expected = Json.createObjectBuilder().add("table",
                Json.createArrayBuilder().add(Json.createObjectBuilder().add("index","fake-tag").add("value","fake-sourcetype"))).build();

        Assertions.assertEquals(expected, objectFromFile);

        Assertions.assertDoesNotThrow(() -> {
            jsonReader.close();
            fileReader.close();
            Files.delete(Paths.get(targetPath));
        });

    }

    @Test
    void testEmptyCaptures() {
        final String targetPath = "src/test/resources/captureGroup1_sourcetypes.json";
        SourcetypesFile sourcetypesFile = new SourcetypesFile(
                new PrintableSourcetypesCaptures(List.of()),
                "captureGroup1",
                "src/test/resources"
        );

        Assertions.assertDoesNotThrow(sourcetypesFile::save);

        final Reader fileReader = Assertions.assertDoesNotThrow(() -> new FileReader(targetPath));
        final JsonReader jsonReader = Json.createReader(fileReader);
        final JsonObject objectFromFile = jsonReader.readObject();

        final JsonObject expected = Json.createObjectBuilder().add("table", JsonValue.EMPTY_JSON_ARRAY).build();

        Assertions.assertEquals(expected, objectFromFile);

        Assertions.assertDoesNotThrow(() -> {
            jsonReader.close();
            fileReader.close();
            Files.delete(Paths.get(targetPath));
        });

    }
}
