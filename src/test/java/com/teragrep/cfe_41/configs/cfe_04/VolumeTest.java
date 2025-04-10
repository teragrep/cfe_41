package com.teragrep.cfe_41.configs.cfe_04;

import com.teragrep.cfe_41.configs.cfe_04.volume.Volume;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class VolumeTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(Volume.class).verify();
    }

    @Test
    void testIdealCase() {
        final Volume volume = new Volume(
                "volume-name",
                "path-to-volume",
                "1234"
        );

        final JsonObject expected = Json.createObjectBuilder()
                .add("name", "volume-name")
                .add("path", "path-to-volume")
                .add("maxVolumeDataSizeMB", "1234")
                .build();

        Assertions.assertEquals(expected, volume.asJsonStructure());
    }
}
