package com.teragrep.cfe_41.configs.cfe_04.global;

import java.util.Map;

public final class GlobalFactory {
    private final Map<String, String> config;

    public GlobalFactory(final Map<String, String> config) {
        this.config = config;
    }

    public Global global() {
        if (!config.containsKey("cfe_04.global.truncate")) {
            throw new IllegalArgumentException("Config cfe_04.global.truncate not specified");
        }

        if (!config.containsKey("cfe_04.global.last_chance_index")) {
            throw new IllegalArgumentException("Config cfe_04.global.last_chance_index not specified");
        }

        if (!config.containsKey("cfe_04.global.last_chance_index_malformed")) {
            throw new IllegalArgumentException("Config cfe_04.global.last_chance_index_malformed not specified");
        }

        if (!config.containsKey("cfe_04.global.max_days_ago")) {
            throw new IllegalArgumentException("Config cfe_04.global.max_days_ago not specified");
        }

        return new Global(
                config.get("cfe_04.global.truncate"),
                config.get("cfe_04.global.last_chance_index"),
                config.get("cfe_04.global.last_chance_index_malformed"),
                config.get("cfe_04.global.max_days_ago")
        );
    }
}
