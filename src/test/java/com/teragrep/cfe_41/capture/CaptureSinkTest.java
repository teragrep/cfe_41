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
package com.teragrep.cfe_41.capture;

import com.teragrep.cfe_41.sink.Sink;
import com.teragrep.cfe_41.sink.SinkResponse;
import com.teragrep.cfe_41.sink.SinkRuleset;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class CaptureSinkTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(CaptureSink.class).verify();
    }

    @Test
    public void captureSinkTest() {
        // Build two Sinks for set
        JsonObjectBuilder sinkOneBuilder = Json.createObjectBuilder();
        sinkOneBuilder.add("flow", "flow1");
        sinkOneBuilder.add("protocol", "protocol1");
        sinkOneBuilder.add("ip_address", "ip_address1");
        sinkOneBuilder.add("port", "port1");
        sinkOneBuilder.add("id", 1);
        JsonObject sinkOneJsonObject = sinkOneBuilder.build();

        JsonObjectBuilder sinkTwoBuilder = Json.createObjectBuilder();
        sinkTwoBuilder.add("flow", "flow2");
        sinkTwoBuilder.add("protocol", "protocol2");
        sinkTwoBuilder.add("ip_address", "ip_address2");
        sinkTwoBuilder.add("port", "port2");
        sinkTwoBuilder.add("id", 2);
        JsonObject sinkTwoJsonObject = sinkTwoBuilder.build();

        Sink sinkOne = new SinkResponse(sinkOneJsonObject);
        Sink sinkTwo = new SinkResponse(sinkTwoJsonObject);

        Set<Sink> sinks = Set.of(sinkOne, sinkTwo);

        // Request flow2 and protocol2 which is expected to provide sink details
        CaptureSink captureSink = new CaptureSink(sinks, "flow2", "protocol2");

        SinkRuleset actual = captureSink.buildSink();

        // Expected to
        SinkRuleset expected = new SinkRuleset("flow2ip_address2port2protocol2", sinkTwo);

        Assertions.assertFalse(sinkOneJsonObject.isEmpty());
        Assertions.assertFalse(sinkTwoJsonObject.isEmpty());
        Assertions.assertEquals(expected.hashCode(), actual.hashCode());

    }
}
