package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.configs.cfe_04.Jsonable;
import jakarta.json.Json;
import jakarta.json.JsonStructure;

public final class JsonableFake implements Jsonable {
    @Override
    public JsonStructure asJsonStructure() {
        return Json.createObjectBuilder().add("fake-key", "fake-value").build();
    }
}
