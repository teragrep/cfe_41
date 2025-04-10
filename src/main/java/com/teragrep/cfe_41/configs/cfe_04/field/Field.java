package com.teragrep.cfe_41.configs.cfe_04.field;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Field field = (Field) o;
        return Objects.equals(name, field.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
