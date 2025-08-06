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
package com.teragrep.cfe_41.hostMeta;

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cnf_01.ArgsConfiguration;
import com.teragrep.cnf_01.Configuration;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class HostMetaRequestImplTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(HostMetaRequestImpl.class).verify();
    }

    @Test
    public void hostMetaRequest() {
        final ClientAndServer mockServer = ClientAndServer.startClientAndServer(1080);
        final MockServerClient mockClient = new MockServerClient("localhost", 1080);

        // Build the expected outcome
        final JsonObjectBuilder hostMetaBuilder = Json.createObjectBuilder();
        hostMetaBuilder.add("id", 1);
        hostMetaBuilder.add("arch", "fake-arch");
        hostMetaBuilder.add("releaseVersion", "fake-relase-version");
        hostMetaBuilder.add("flavor", "fake-flavor");
        hostMetaBuilder.add("os", "fake-os");
        hostMetaBuilder.add("hostName", "fake-hostname");
        hostMetaBuilder.add("hostId", 1);
        final JsonObject hostMetaAsJsonObject = hostMetaBuilder.build();

        // Mock client that simulates successful request from cfe_18
        mockClient
                .when(request().withMethod("GET").withPath("/host/meta/1"))
                .respond(response().withStatusCode(200).withBody(hostMetaAsJsonObject.toString()));

        final String[] args = new String[] {
                "url=http://localhost:1080", "test=test"
        };
        final Configuration cfg = new ArgsConfiguration(args);

        // Create ApiConfig so that request can be made
        final ApiConfig apiConfig = Assertions.assertDoesNotThrow(() -> new ApiConfig(cfg.asMap()));

        final HostMetaRequest hostMetaRequest = new HostMetaRequestImpl(apiConfig);

        final HostMeta actualHostMeta = Assertions.assertDoesNotThrow(() -> hostMetaRequest.hostMetaResponse(1));

        final HostMeta expectedHostMeta = new HostMetaResponse(hostMetaAsJsonObject);

        Assertions.assertFalse(hostMetaAsJsonObject.isEmpty());

        Assertions.assertEquals(expectedHostMeta.hashCode(), actualHostMeta.hashCode());
        Assertions.assertEquals(expectedHostMeta, actualHostMeta);
        mockServer.stop();
    }

}
