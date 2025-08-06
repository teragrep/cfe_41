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

import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroup;
import com.teragrep.cfe_41.captureGroup.CaptureGroupAllRequest;
import com.teragrep.cfe_41.captureGroup.PartialCaptureGroupResponse;
import com.teragrep.cfe_41.fakes.CaptureGroupAllRequestFake;
import com.teragrep.cfe_41.fakes.CaptureRequestFake;
import com.teragrep.cfe_41.flow.Flow;
import com.teragrep.cfe_41.flow.PartialFlowResponse;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jdk.jfr.Description;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public final class FlowGroupsTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(FlowGroups.class).verify();
    }

    @Test
    @Description("FlowGroups can successfully return List of FlowWithGroups")
    public void FlowGroupsSuccessfulTest() {
        final List<FlowWithGroup> expectedList = new ArrayList<>();

        // Flow instance for testing
        final JsonObject flowAsJson = Json.createObjectBuilder().add("id", 1).add("name", "fake-flow").build();
        final Flow flow = new PartialFlowResponse(flowAsJson);

        // Build first expected combination
        final JsonObject captureGroupAsJson = Json
                .createObjectBuilder()
                .add("capture_def_group_name", "group1")
                .add("capture_definition_id", 1)
                .add("capture_group_type", "relp")
                .add("id", 1)
                .build();
        final CaptureGroup captureGroup = new PartialCaptureGroupResponse(captureGroupAsJson);

        // Build second expected combination
        final JsonObject captureGroupAsJson2 = Json
                .createObjectBuilder()
                .add("capture_def_group_name", "group2")
                .add("capture_definition_id", 1)
                .add("capture_group_type", "relp")
                .add("id", 2)
                .build();
        final CaptureGroup captureGroup2 = new PartialCaptureGroupResponse(captureGroupAsJson2);

        // Created expected objects and add them to the list of expected items
        final FlowWithGroup expected1 = new FlowWithGroup(flow, captureGroup);
        final FlowWithGroup expected2 = new FlowWithGroup(flow, captureGroup2);
        expectedList.add(expected1);
        expectedList.add(expected2);

        // Fake requests for actual data creation
        final CaptureGroupAllRequest captureGroupRequestFake = new CaptureGroupAllRequestFake();
        final CaptureRequest captureRequestFake = new CaptureRequestFake();
        final FlowGroups flowGroups = new FlowGroups(captureGroupRequestFake, captureRequestFake);

        // Create actual object from fake requests
        final List<FlowWithGroup> actualList = Assertions
                .assertDoesNotThrow(() -> flowGroups.requestAndReturnAllCombinations(flow));

        // Compare actualList against expected list
        int loopsExecuted = 0;
        for (final FlowWithGroup oneFlowWithGroup : actualList) {
            Assertions.assertEquals(expectedList.get(loopsExecuted).asCombination(), oneFlowWithGroup.asCombination());
            loopsExecuted++;
        }
        Assertions.assertEquals(2, loopsExecuted);
        // Assert that list is not empty
        Assertions.assertFalse(expectedList.isEmpty());
    }

    @Test
    @Description("FlowGroups can not create config if there are no matching records")
    public void FlowGroupsNoMatchTest() {
        JsonObject flowAsJson = Json.createObjectBuilder().add("id", 1).add("name", "flowWithoutGroups").build();
        Flow flow = new PartialFlowResponse(flowAsJson);

        // Fake requests for actual data creation
        final CaptureGroupAllRequest captureGroupRequestFake = new CaptureGroupAllRequestFake();
        final CaptureRequest captureRequestFake = new CaptureRequestFake();
        final FlowGroups flowGroups = new FlowGroups(captureGroupRequestFake, captureRequestFake);

        // Create actual object from fake requests
        final List<FlowWithGroup> actualList = Assertions
                .assertDoesNotThrow(() -> flowGroups.requestAndReturnAllCombinations(flow));

        // Assert that list is empty because fake requests do not match flow name of "flowWithoutGroups"
        Assertions.assertTrue(actualList.isEmpty());
    }

}
