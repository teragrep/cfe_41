/*
 * Integration Command-line tool for Teragrep
 * Copyright (C) 2021  Suomen Kanuuna Oy
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
import Fakes.FlowRequestFake;
import com.teragrep.cfe_41.flow.FlowResponse;
import com.teragrep.cfe_41.flow.PartialFlowResponse;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FlowTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(FlowResponse.class).verify();
    }

    @Test
    public void flowRequestTest() {
        // Create fake request
        FlowRequestFake fakeFlow = new FlowRequestFake();

        // Build data for expected response
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder
                .add(Json.createObjectBuilder().add("id", 1).add("name", "flow1"))
                .add(Json.createObjectBuilder().add("id", 2).add("name", "flow2"));
        JsonArray flowsArray = arrayBuilder.build();

        FlowResponse fakeFlowResponse = Assertions.assertDoesNotThrow(fakeFlow::flowResponse);
        FlowResponse realFlowResponse = new FlowResponse(flowsArray);

        // Assertions
        Assertions.assertFalse(flowsArray.isEmpty());
        // Asserting that contents equal to each other
        Assertions.assertEquals(realFlowResponse.hashCode(), fakeFlowResponse.hashCode());

    }

    // tests that flowResponse can produce List of PartialFlowResponses accordingly when fed JsonArray
    // tests partialFlowResponse object at the same time?
    @Test
    public void flowResponseTest() {
        // Build data for expected response
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder
                .add(Json.createObjectBuilder().add("id", 1).add("name", "flow1"))
                .add(Json.createObjectBuilder().add("id", 2).add("name", "flow2"));
        JsonArray flowsArray = arrayBuilder.build();

        FlowResponse flowResponse = new FlowResponse(flowsArray);
        List<PartialFlowResponse> actualFlowResponseList = flowResponse.partialFlowResponses();

        JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
        objectBuilder1.add("id", 1).add("name", "flow1");

        JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
        objectBuilder2.add("id", 2).add("name", "flow2");

        PartialFlowResponse partialFlowResponse1 = new PartialFlowResponse(objectBuilder1.build());
        PartialFlowResponse partialFlowResponse2 = new PartialFlowResponse(objectBuilder2.build());
        List<PartialFlowResponse> expectedFlowResponseList = new ArrayList<>();
        expectedFlowResponseList.add(partialFlowResponse1);
        expectedFlowResponseList.add(partialFlowResponse2);

        Assertions.assertEquals(2, actualFlowResponseList.size());
        int loopsExecuted = 0;
        for (PartialFlowResponse response : actualFlowResponseList) {
            Assertions.assertEquals(expectedFlowResponseList.get(loopsExecuted).flowId(), response.flowId());
            Assertions.assertEquals(expectedFlowResponseList.get(loopsExecuted).flowName(), response.flowName());
            loopsExecuted++;
        }
        Assertions.assertEquals(2, loopsExecuted);

    }

}
