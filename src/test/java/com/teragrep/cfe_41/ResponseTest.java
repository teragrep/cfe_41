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
package com.teragrep.cfe_41;

import com.teragrep.cnf_01.ArgsConfiguration;
import com.teragrep.cnf_01.Configuration;
import jakarta.json.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ResponseTest {

    private static ClientAndServer mockServer;
    private static MockServerClient mockClient;

    @Test
    public void testContract() {
        EqualsVerifier.forClass(Response.class).verify();
    }

    @BeforeAll
    public static void startMockServer() {
        mockServer = ClientAndServer.startClientAndServer(1080);
        mockClient = new MockServerClient("localhost", 1080);
    }

    @AfterAll
    public static void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void testResponseArray() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder
                .add(Json.createObjectBuilder().add("id", 1).add("object1", "oneJsonObject"))
                .add(Json.createObjectBuilder().add("id", 2).add("object2", "secondJsonObject"));
        JsonArray flowsArray = arrayBuilder.build();

        // Simulates successful return on endpoint
        mockClient
                .when(request().withMethod("GET").withPath("/testArray"))
                .respond(response().withStatusCode(200).withBody(flowsArray.toString()));

        String[] args = new String[] {
                "url=http://localhost:1080", "test=test"
        };

        Configuration cfg = new ArgsConfiguration(args);

        ApiConfig apiConfig = Assertions.assertDoesNotThrow(() -> new ApiConfig(cfg.asMap()));

        RequestData requestData = new RequestData("/testArray", apiConfig);

        Response response = Assertions.assertDoesNotThrow(() -> new Response(requestData.doRequest()));

        JsonArray result = Assertions.assertDoesNotThrow(() -> response.asJsonArray());

        Assertions.assertEquals(flowsArray.hashCode(), result.hashCode());
    }

    @Test
    public void testResponseObject() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("id", 1);
        JsonObject expected = jsonObjectBuilder.build();

        // Simulates successful return on endpoint
        mockClient
                .when(request().withMethod("GET").withPath("/testObject"))
                .respond(response().withStatusCode(200).withBody(expected.toString()));

        String[] args = new String[] {
                "url=http://localhost:1080", "test=test"
        };

        Configuration cfg = new ArgsConfiguration(args);

        ApiConfig apiConfig = Assertions.assertDoesNotThrow(() -> new ApiConfig(cfg.asMap()));

        RequestData requestData = new RequestData("/testObject", apiConfig);

        Response response = Assertions.assertDoesNotThrow(() -> new Response(requestData.doRequest()));

        JsonObject actual = Assertions.assertDoesNotThrow(() -> response.asJsonObject());

        Assertions.assertEquals(expected.hashCode(), actual.hashCode());
    }

}
