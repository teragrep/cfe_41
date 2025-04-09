package com.teragrep.cfe_41.configs.cfe_04.volume;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

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
}
