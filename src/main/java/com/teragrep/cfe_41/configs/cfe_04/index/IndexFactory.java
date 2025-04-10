package com.teragrep.cfe_41.configs.cfe_04.index;

import java.util.Map;

public final class IndexFactory {
    private final Map<String, String> config;

    public IndexFactory(final Map<String, String> config) {
        this.config = config;
    }

    public Index index(final String name) {
        if (!config.containsKey("cfe_04.config.index.home.path.template")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.home.path.template not specified");
        }

        if (!config.containsKey("cfe_04.config.index.cold.path.template")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.cold.path.template not specified");
        }

        if (!config.containsKey("cfe_04.config.index.thawed.path.template")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.thawed.path.template not specified");
        }

        if (!config.containsKey("cfe_04.config.index.summary.home.path")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.summary.home.path not specified");
        }

        final String homePathTemplate = config.get("cfe_04.config.index.home.path.template");
        if (!homePathTemplate.contains("%s")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.home.path.template does not contain %s");
        }
        final String coldPathTemplate = config.get("cfe_04.config.index.cold.path.template");
        if (!coldPathTemplate.contains("%s")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.cold.path.template does not contain %s");
        }
        final String thawedPathTemplate = config.get("cfe_04.config.index.thawed.path.template");
        if (!thawedPathTemplate.contains("%s")) {
            throw new IllegalArgumentException("Config cfe_04.config.index.thawed.path.template does not contain %s");
        }
        final String summaryHomePath = config.get("cfe_04.config.index.summary.home.path");

        return new Index(
                name,
                homePathTemplate,
                coldPathTemplate,
                thawedPathTemplate,
                summaryHomePath
        );
    }
}
