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

import com.teragrep.cfe_41.fakes.*;
import com.teragrep.cfe_41.media.JsonMedia;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class CFE04ConfigTest {

    @Test
    void testEqualsContract() {
        EqualsVerifier.forClass(CFE04Config.class).verify();
    }

    @Test
    void testIdealCase() {
        final CFE04Config config = new CFE04Config(
                new CaptureGroupAllRequestFake(),
                new CaptureRequestFake(),
                new CFE04CaptureStorageRequestFake()
        );
        final JsonObject output = config.print(new JsonMedia()).asJson();

        final JsonObject expMeta = Json.createObjectBuilder().add("name", "meta-name").build();

        final JsonObject expIndex = Json
                .createObjectBuilder()
                .add("name", "fake-index")
                .add("repFactor", "auto")
                .add("disabled", "false")
                .add("homePath", "fake:home0/fake-index/fake")
                .add("coldPath", "fake:cold0/fake-index/fake")
                .add("thawedPath", "fake:thaw0/fake-index/fake")
                .add(
                        "override",
                        Json.createArrayBuilder().add(Json.createObjectBuilder().add("key", "frozenTimePeriodInSecs").add("value", "2592000"))
                )
                .add("summaryHomePath", "fake:summary/summary/fake/fake")
                .build();

        final JsonObject expSourcetype = Json
                .createObjectBuilder()
                .add("name", "fake-source")
                .add("max_days_ago", "25")
                .add("category", "Fake category")
                .add("description", "Fake description")
                .add("transforms", JsonValue.EMPTY_JSON_ARRAY)
                .add("truncate", "1")
                .add("freeform_indexer_enabled", "false")
                .add("freeform_indexer_text", "")
                .add("freeform_lb_enabled", "false")
                .add("freeform_lb_text", "")
                .build();

        final JsonObject expGlobal = Json
                .createObjectBuilder()
                .add("truncate", "12345")
                .add("last_chance_index", "lci")
                .add("last_chance_index_malformed", "lci_malformed")
                .add("max_days_ago", "25")
                .build();

        final JsonObject expected = Json
                .createObjectBuilder()
                .add("_meta", expMeta)
                .add("volumes", JsonValue.EMPTY_JSON_ARRAY)
                .add("indexes", Json.createArrayBuilder().add(expIndex))
                .add("sourcetypes", Json.createArrayBuilder().add(expSourcetype))
                .add("fields", JsonValue.EMPTY_JSON_ARRAY)
                .add("transforms", JsonValue.EMPTY_JSON_ARRAY)
                .add("global", expGlobal)
                .build();

        Assertions.assertEquals(expected, output);
    }

    @Test
    void testNoCFE04Storages() {
        final CFE04Config config = new CFE04Config(
                new CaptureGroupAllRequestFake(),
                new CaptureRequestFake(),
                new CaptureStorageRequestFake()
        );
        final JsonObject output = config.print(new JsonMedia()).asJson();

        final JsonObject expMeta = Json.createObjectBuilder().add("name", "meta-name").build();

        final JsonObject expGlobal = Json
                .createObjectBuilder()
                .add("truncate", "12345")
                .add("last_chance_index", "lci")
                .add("last_chance_index_malformed", "lci_malformed")
                .add("max_days_ago", "25")
                .build();

        final JsonObject expected = Json
                .createObjectBuilder()
                .add("_meta", expMeta)
                .add("volumes", JsonValue.EMPTY_JSON_ARRAY)
                .add("indexes", JsonValue.EMPTY_JSON_ARRAY)
                .add("sourcetypes", JsonValue.EMPTY_JSON_ARRAY)
                .add("fields", JsonValue.EMPTY_JSON_ARRAY)
                .add("transforms", JsonValue.EMPTY_JSON_ARRAY)
                .add("global", expGlobal)
                .build();

        Assertions.assertEquals(expected, output);
    }
}
