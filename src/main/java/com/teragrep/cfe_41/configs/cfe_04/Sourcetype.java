/*
 * Integration Command-line tool for Teragrep
 * Copyright (C) 2025  Suomen Kanuuna Oy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://github.com/teragrep/teragrep/blob/main/LICENSE>.
 *
 *
 * Additional permission under GNU Affero General Public License version 3
 * section 7
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with other code, such other code is not for that reason alone subject to any
 * of the requirements of the GNU Affero GPL version 3 as long as this Program
 * is the same Program as licensed from Suomen Kanuuna Oy without any additional
 * modifications.
 *
 * Supplemented terms under GNU Affero General Public License version 3
 * section 7
 *
 * Origin of the software must be attributed to Suomen Kanuuna Oy. Any modified
 * versions must be marked as "Modified version of" The Program.
 *
 * Names of the licensors and authors may not be used for publicity purposes.
 *
 * No rights are granted for use of trade names, trademarks, or service marks
 * which are in The Program if any.
 *
 * Licensee must indemnify licensors and authors for any liability that these
 * contractual assumptions impose on licensors and authors.
 *
 * To the extent this program is licensed as part of the Commercial versions of
 * Teragrep, the applicable Commercial License may apply to this file if you as
 * a licensee so wish it.
 */
package com.teragrep.cfe_41.configs.cfe_04;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Sourcetype implements Jsonable {

    private final String name;
    private final String max_days_ago;
    private final String category;
    private final String description;
    private final List<String> transforms;
    private final String truncate;
    private final String freeform_indexer_enabled;
    private final String freeform_indexer_text;
    private final String freeform_lb_enabled;
    private final String freeform_lb_text;

    public Sourcetype(final String name, final Map<String, String> config) {
        this(
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

    public Sourcetype(
            final String name,
            final String max_days_ago,
            final String category,
            final String description,
            final List<String> transforms,
            final String truncate,
            final String freeform_indexer_enabled,
            final String freeform_indexer_text,
            final String freeform_lb_enabled,
            final String freeform_lb_text
    ) {
        this.name = name;
        this.max_days_ago = max_days_ago;
        this.category = category;
        this.description = description;
        this.transforms = transforms;
        this.truncate = truncate;
        this.freeform_indexer_enabled = freeform_indexer_enabled;
        this.freeform_indexer_text = freeform_indexer_text;
        this.freeform_lb_enabled = freeform_lb_enabled;
        this.freeform_lb_text = freeform_lb_text;
    }

    public JsonObject asJsonStructure() {
        return Json
                .createObjectBuilder()
                .add("name", name)
                .add("max_days_ago", max_days_ago)
                .add("category", category)
                .add("description", description)
                .add("transforms", Json.createArrayBuilder(transforms))
                .add("truncate", truncate)
                .add("freeform_indexer_enabled", freeform_indexer_enabled)
                .add("freeform_indexer_text", freeform_indexer_text)
                .add("freeform_lb_enabled", freeform_lb_enabled)
                .add("freeform_lb_text", freeform_lb_text)
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Sourcetype that = (Sourcetype) o;
        return Objects.equals(name, that.name)
                && Objects.equals(max_days_ago, that.max_days_ago) && Objects.equals(category, that.category) && Objects.equals(description, that.description) && Objects.equals(transforms, that.transforms) && Objects.equals(truncate, that.truncate) && Objects.equals(freeform_indexer_enabled, that.freeform_indexer_enabled) && Objects.equals(freeform_indexer_text, that.freeform_indexer_text) && Objects.equals(freeform_lb_enabled, that.freeform_lb_enabled) && Objects.equals(freeform_lb_text, that.freeform_lb_text);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        name, max_days_ago, category, description, transforms, truncate, freeform_indexer_enabled,
                        freeform_indexer_text, freeform_lb_enabled, freeform_lb_text
                );
    }
}
