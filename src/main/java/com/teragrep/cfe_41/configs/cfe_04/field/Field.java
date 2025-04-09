package com.teragrep.cfe_41.configs.cfe_04.field;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

public final class Field implements Jsonable {
    private final String name;

    public Field(final String name) {
        this.name = name;
    }

    @Override
    public JsonStructure asJsonStructure() {
        return Json.createObjectBuilder()
                .add("name", name)
                .build();
    }
}
