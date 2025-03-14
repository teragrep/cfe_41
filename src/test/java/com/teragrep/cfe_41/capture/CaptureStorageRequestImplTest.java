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

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cnf_01.ArgsConfiguration;
import com.teragrep.cnf_01.Configuration;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class CaptureStorageRequestImplTest {

    private static ClientAndServer mockServer;
    private static MockServerClient mockClient;

    @Test
    public void testContract() {
        EqualsVerifier.forClass(CaptureStorageRequestImpl.class).verify();
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
    public void captureStorageRequestTest() {

        // Expected to have capture with ID of 1 to have storages 1 and 2 (cfe_10 and cfe_11)
        JsonArrayBuilder captureStorageBuilder = Json.createArrayBuilder();
        captureStorageBuilder
                .add(Json.createObjectBuilder().add("storage_id", 1).add("capture_id", 1).add("storage_name", "cfe_10"))
                .add(Json.createObjectBuilder().add("storage_id", 2).add("capture_id", 1).add("storage_name", "cfe_11"));
        JsonArray captureStoragesArray = captureStorageBuilder.build();

        // Mock client that simulates successful request from cfe_18. Request should ask with capture_id of 1
        mockClient
                .when(request().withMethod("GET").withPath("/storage/capture/1"))
                .respond(response().withStatusCode(200).withBody(captureStoragesArray.toString()));

        String[] args = new String[] {
                "url=http://localhost:1080", "test=test"
        };
        Configuration cfg = new ArgsConfiguration(args);

        // Create ApiConfig so that request can be made
        ApiConfig apiConfig = Assertions.assertDoesNotThrow(() -> new ApiConfig(cfg.asMap()));

        CaptureStorageRequest captureStorageRequest = new CaptureStorageRequestImpl(apiConfig);

        CaptureStorageResponse actualCaptureStorageResponse = Assertions
                .assertDoesNotThrow(() -> captureStorageRequest.captureStorageResponse(1));

        CaptureStorageResponse expectedCaptureStorageResponse = new CaptureStorageResponse(captureStoragesArray);

        // Assertions
        Assertions.assertFalse(captureStoragesArray.isEmpty());
        // Asserting that contents equal to each other
        Assertions.assertEquals(actualCaptureStorageResponse.hashCode(), expectedCaptureStorageResponse.hashCode());
    }
}
