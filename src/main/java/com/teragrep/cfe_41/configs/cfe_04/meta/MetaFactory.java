package com.teragrep.cfe_41.configs.cfe_04.meta;

import java.util.Map;

public final class MetaFactory {
    private final Map<String, String> config;

    public MetaFactory(final Map<String, String> config) {
        this.config = config;
    }

    public Meta meta() {
        if (!config.containsKey("cfe_04.meta.name")) {
            throw new IllegalArgumentException("Missing required property 'cfe_04.meta.name'");
        }

        return new Meta(config.get("cfe_04.meta.name"));
    }
}
