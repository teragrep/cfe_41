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
package com.teragrep.cfe_41.hostGroup;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HostGroupResponseTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(HostGroupResponse.class).verify();
    }

    @Test
    public void hostGroupResponseTest() {
        // Build data for expected response
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder
                .add(Json.createObjectBuilder().add("host_id", 1).add("host_group_name", "hostGroup1").add("host_group_type", "relp").add("md5", "md5first").add("id", 1)).add(Json.createObjectBuilder().add("host_id", 2).add("host_group_name", "hostGroup1").add("host_group_type", "relp").add("md5", "md5second").add("id", 2));
        JsonArray hostGroupArray = arrayBuilder.build();

        HostGroupResponse hostGroupResponse = new HostGroupResponse(hostGroupArray);
        List<PartialHostResponse> actualHostGroupResponseList = hostGroupResponse.partialHostResponses();

        JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
        objectBuilder1
                .add("host_id", 1)
                .add("host_group_name", "hostGroup1")
                .add("host_group_type", "relp")
                .add("md5", "md5first")
                .add("id", 1);

        JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
        objectBuilder2
                .add("host_id", 2)
                .add("host_group_name", "hostGroup1")
                .add("host_group_type", "relp")
                .add("md5", "md5second")
                .add("id", 2);

        PartialHostResponse partialHostResponse1 = new PartialHostResponse(objectBuilder1.build());
        PartialHostResponse partialHostResponse2 = new PartialHostResponse(objectBuilder2.build());
        List<PartialHostResponse> expectedHostGroupResponseList = new ArrayList<>();
        expectedHostGroupResponseList.add(partialHostResponse1);
        expectedHostGroupResponseList.add(partialHostResponse2);

        Assertions.assertEquals(2, actualHostGroupResponseList.size());
        int loopsExecuted = 0;
        for (PartialHostResponse response : actualHostGroupResponseList) {
            Assertions.assertEquals(expectedHostGroupResponseList.get(loopsExecuted).hostId(), response.hostId());
            Assertions
                    .assertEquals(expectedHostGroupResponseList.get(loopsExecuted).hostGroupName(), response.hostGroupName());
            Assertions
                    .assertEquals(expectedHostGroupResponseList.get(loopsExecuted).hostGroupType(), response.hostGroupType());
            Assertions.assertEquals(expectedHostGroupResponseList.get(loopsExecuted).md5(), response.md5());
            Assertions.assertEquals(expectedHostGroupResponseList.get(loopsExecuted).groupId(), response.groupId());
            loopsExecuted++;
        }
        Assertions.assertEquals(2, loopsExecuted);

    }
}
