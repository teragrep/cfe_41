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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Index implements Jsonable {

    private final String name;
    private final String repFactor;
    private final String disabled;
    private final String homePath;
    private final String coldPath;
    private final String thawedPath;
    private final List<KeyValuePair> override;
    private final String summaryHomePath;

    public Index(final String name, final Map<String, String> config) {
        this(
                name,
                config.get("cfe_04.config.index.home.path.template"),
                config.get("cfe_04.config.index.cold.path.template"),
                config.get("cfe_04.config.index.thawed.path.template"),
                config.get("cfe_04.config.index.summary.home.path")
        );
    }

    public Index(
            final String name,
            final String homePathTemplate,
            final String coldPathTemplate,
            final String thawedPathTemplate,
            final String summaryHomePath
    ) {
        this(
                name,
                "auto",
                "false",
                String.format(homePathTemplate, name),
                String.format(coldPathTemplate, name),
                String.format(thawedPathTemplate, name),
                List.of(new KeyValuePair("frozenTimePeriodInSecs", "2592000")),
                summaryHomePath
        );
    }

    public Index(
            final String name,
            final String repFactor,
            final String disabled,
            final String homePath,
            final String coldPath,
            final String thawedPath,
            final List<KeyValuePair> override,
            final String summaryHomePath
    ) {
        this.name = name;
        this.repFactor = repFactor;
        this.disabled = disabled;
        this.homePath = homePath;
        this.coldPath = coldPath;
        this.thawedPath = thawedPath;
        this.override = override;
        this.summaryHomePath = summaryHomePath;
    }

    public JsonObject asJsonStructure() {
        JsonArrayBuilder overrideArrayBuilder = Json.createArrayBuilder();
        for (final KeyValuePair keyValuePair : override) {
            overrideArrayBuilder.add(keyValuePair.asJsonStructure().asJsonObject());
        }

        return Json
                .createObjectBuilder()
                .add("name", name)
                .add("repFactor", repFactor)
                .add("disabled", disabled)
                .add("homePath", homePath)
                .add("coldPath", coldPath)
                .add("thawedPath", thawedPath)
                .add("override", overrideArrayBuilder.build())
                .add("summaryHomePath", summaryHomePath)
                .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Index index = (Index) o;
        return Objects.equals(name, index.name) && Objects.equals(repFactor, index.repFactor)
                && Objects.equals(disabled, index.disabled) && Objects.equals(homePath, index.homePath) && Objects.equals(coldPath, index.coldPath) && Objects.equals(thawedPath, index.thawedPath) && Objects.equals(override, index.override) && Objects.equals(summaryHomePath, index.summaryHomePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, repFactor, disabled, homePath, coldPath, thawedPath, override, summaryHomePath);
    }
}
