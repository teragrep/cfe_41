package com.teragrep.cfe_41.configs.cfe_04.transform;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

import java.util.Objects;

public final class Transform implements Jsonable {
    private final String name;
    private final boolean writeMeta;
    private final boolean writeDefaultValue;
    private final String defaultValue;
    private final String destKey;
    private final String regex;
    private final String format;

    public Transform(final String name, final boolean writeMeta, final boolean writeDefaultValue, final String defaultValue, final String destKey, final String regex, final String format) {
        this.name = name;
        this.writeMeta = writeMeta;
        this.writeDefaultValue = writeDefaultValue;
        this.defaultValue = defaultValue;
        this.destKey = destKey;
        this.regex = regex;
        this.format = format;
    }

    @Override
    public JsonStructure asJsonStructure() {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("write_meta", writeMeta)
                .add("write_default_value", writeDefaultValue)
                .add("default_value", defaultValue)
                .add("dest_key", destKey)
                .add("regex", regex)
                .add("format", format)
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Transform transform = (Transform) o;
        return writeMeta == transform.writeMeta && writeDefaultValue == transform.writeDefaultValue && Objects.equals(name, transform.name) && Objects.equals(defaultValue, transform.defaultValue) && Objects.equals(destKey, transform.destKey) && Objects.equals(regex, transform.regex) && Objects.equals(format, transform.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, writeMeta, writeDefaultValue, defaultValue, destKey, regex, format);
    }
}
