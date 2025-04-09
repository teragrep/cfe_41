package com.teragrep.cfe_41.configs.cfe_04;

import java.util.Map;
import java.util.List;

public final class SourcetypeFactory {

    private final Map<String, String> config;

    public SourcetypeFactory(final Map<String, String> config) {
        this.config = config;
    }

    public Sourcetype sourcetype(final String name) {
        if (!config.containsKey("cfe_04.config.sourcetype.max_days_ago")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.max_days_ago must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.category")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.category must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.description")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.description must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.truncate")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.truncate must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.freeform_indexer_enabled")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.freeform_indexer_enabled must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.freeform_indexer_text")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.freeform_indexer_text must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.freeform_lb_enabled")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.freeform_lb_enabled must be set");
        }

        if (!config.containsKey("cfe_04.config.sourcetype.freeform_lb_text")) {
            throw new IllegalArgumentException("cfe_04.config.sourcetype.freeform_lb_text must be set");
        }

        return new Sourcetype(
                name,
                config.get("cfe_04.config.sourcetype.max_days_ago"),
                config.get("cfe_04.config.sourcetype.category"),
                config.get("cfe_04.config.sourcetype.description"),
                List.of(),
                config.get("cfe_04.config.sourcetype.truncate"),
                config.get("cfe_04.config.sourcetype.freeform_indexer_enabled"),
                config.get("cfe_04.config.sourcetype.freeform_indexer_text"),
                config.get("cfe_04.config.sourcetype.freeform_lb_enabled"),
                config.get("cfe_04.config.sourcetype.freeform_lb_text")
        );
    }
}
