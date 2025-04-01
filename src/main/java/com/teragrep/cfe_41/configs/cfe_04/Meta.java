package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonStructure;
import java.util.Map;
import java.util.Objects;

public final class Meta implements Jsonable {

    private final String name;

    public Meta(final Map<String, String> config) {
        this(config.get("cfe_04.meta.name"));
    }

    public Meta(final String name) {
        this.name = name;
    }

    @Override
    public JsonStructure asJsonStructure() {
        return Json.createObjectBuilder().add("name", name).build();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Meta meta = (Meta) o;
        return Objects.equals(name, meta.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
