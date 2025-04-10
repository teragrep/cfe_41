package com.teragrep.cfe_41.configs.cfe_04.volume;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

import java.util.Objects;

public final class Volume implements Jsonable {

    private final String name;
    private final String path;
    private final String maxVolumeDataSizeMB;

    public Volume(final String name, final String path, final String maxVolumeDataSizeMB) {
        this.name = name;
        this.path = path;
        this.maxVolumeDataSizeMB = maxVolumeDataSizeMB;
    }

    @Override
    public JsonStructure asJsonStructure() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("path", path)
                .add("maxVolumeDataSizeMB", maxVolumeDataSizeMB)
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Volume volume = (Volume) o;
        return Objects.equals(name, volume.name) && Objects.equals(path, volume.path) && Objects.equals(maxVolumeDataSizeMB, volume.maxVolumeDataSizeMB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, maxVolumeDataSizeMB);
    }
}
