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
package com.teragrep.cfe_41.captureGroup;

import jakarta.json.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CaptureGroupResponseTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(CaptureGroupResponse.class).verify();
    }

    @Test
    public void captureGroupResponseTest() {
        // Creating expected outcome of one group but with multiple captures
        JsonArrayBuilder captureGroupBuilder = Json.createArrayBuilder();
        captureGroupBuilder
                .add(Json.createObjectBuilder().add("capture_def_group_name", "captureGroup1").add("capture_definition_id", 1).add("capture_group_type", "relp").add("id", 1)).add(Json.createObjectBuilder().add("capture_def_group_name", "captureGroup1").add("capture_definition_id", 2).add("capture_group_type", "relp").add("id", 1));
        JsonArray captureGroupArray = captureGroupBuilder.build();

        CaptureGroupResponse captureGroupResponse = new CaptureGroupResponse(captureGroupArray);
        List<PartialCaptureResponse> actualPartialCaptureResponses = captureGroupResponse.partialCaptureResponses();

        JsonObjectBuilder expectedCaptureGroupBuilder1 = Json.createObjectBuilder();
        expectedCaptureGroupBuilder1.add("capture_def_group_name", "captureGroup1");
        expectedCaptureGroupBuilder1.add("capture_definition_id", 1);
        expectedCaptureGroupBuilder1.add("capture_group_type", "relp");
        expectedCaptureGroupBuilder1.add("id", 1);
        JsonObject expectedCaptureGroup1 = expectedCaptureGroupBuilder1.build();

        JsonObjectBuilder expectedCaptureGroupBuilder2 = Json.createObjectBuilder();
        expectedCaptureGroupBuilder2.add("capture_def_group_name", "captureGroup1");
        expectedCaptureGroupBuilder2.add("capture_definition_id", 2);
        expectedCaptureGroupBuilder2.add("capture_group_type", "relp");
        expectedCaptureGroupBuilder2.add("id", 1);
        JsonObject expectedCaptureGroup2 = expectedCaptureGroupBuilder2.build();

        List<PartialCaptureResponse> expectedPartialCaptureResponses = new ArrayList<>();

        PartialCaptureResponse expectedPartialCapture1 = new PartialCaptureResponse(expectedCaptureGroup1);
        PartialCaptureResponse expectedPartialCapture2 = new PartialCaptureResponse(expectedCaptureGroup2);
        expectedPartialCaptureResponses.add(expectedPartialCapture1);
        expectedPartialCaptureResponses.add(expectedPartialCapture2);

        Assertions.assertEquals(2, actualPartialCaptureResponses.size());
        int loopsExecuted = 0;
        for (PartialCaptureResponse response : actualPartialCaptureResponses) {
            Assertions
                    .assertEquals(expectedPartialCaptureResponses.get(loopsExecuted).groupName(), response.groupName());
            Assertions
                    .assertEquals(expectedPartialCaptureResponses.get(loopsExecuted).captureDefinitionId(), response.captureDefinitionId());
            Assertions
                    .assertEquals(expectedPartialCaptureResponses.get(loopsExecuted).captureGroupType(), response.captureGroupType());
            Assertions.assertEquals(expectedPartialCaptureResponses.get(loopsExecuted).groupId(), response.groupId());
            loopsExecuted++;
        }
        Assertions.assertEquals(2, loopsExecuted);

    }
}
