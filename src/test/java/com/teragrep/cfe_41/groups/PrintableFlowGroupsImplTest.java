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
package com.teragrep.cfe_41.groups;

import com.teragrep.cfe_41.captureGroup.CaptureGroup;
import com.teragrep.cfe_41.captureGroup.PartialCaptureGroupResponse;
import com.teragrep.cfe_41.flow.Flow;
import com.teragrep.cfe_41.flow.PartialFlowResponse;
import com.teragrep.cfe_41.media.JsonMedia;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jdk.jfr.Description;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public final class PrintableFlowGroupsImplTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(PrintableFlowGroupsImpl.class).verify();
    }

    @Test
    @Description("Tests that PrintableFlowGroups can print the results")
    public void PrintableFlowGroupsTestSuccessfully() {
        final Flow flow = new PartialFlowResponse(Json.createObjectBuilder().add("id", 1).add("name", "flow").build());
        final CaptureGroup captureGroup = new PartialCaptureGroupResponse(
                Json.createObjectBuilder().add("capture_def_group_name", "group").add("capture_definition_id", 1).add("capture_group_type", "relp").add("id", 1).build()
        );
        final FlowWithGroup flowWithGroup = new FlowWithGroup(flow, captureGroup);
        final PrintableFlowGroups printableFlowGroups = new PrintableFlowGroupsImpl(List.of(flowWithGroup));
        final JsonObject actual = printableFlowGroups.print(new JsonMedia()).asJson();

        final JsonArray content = Json.createArrayBuilder().add("flow-group").build();
        final JsonObject expected = Json.createObjectBuilder().add("groups", content).build();

        Assertions.assertFalse(actual.isEmpty());
        Assertions.assertEquals(flowWithGroup, printableFlowGroups.flowGroup().get(0));
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @Description("Test that PrintableFlowGroups can return empty results")
    public void PrintableFlowGroupsTestEmpty() {
        final PrintableFlowGroupsImpl printableFlowGroups = new PrintableFlowGroupsImpl(Collections.emptyList());
        final JsonObject actual = printableFlowGroups.print(new JsonMedia()).asJson();

        final JsonObject expected = Json.createObjectBuilder().add("groups", JsonValue.EMPTY_JSON_ARRAY).build();
        Assertions.assertTrue(printableFlowGroups.flowGroup().isEmpty());
        Assertions.assertEquals(expected, actual);

    }
}
