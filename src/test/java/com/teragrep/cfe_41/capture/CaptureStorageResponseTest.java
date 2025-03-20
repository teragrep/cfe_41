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

import jakarta.json.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CaptureStorageResponseTest {

    @Test
    public void testContract() {
        EqualsVerifier.forClass(CaptureStorageResponse.class).verify();
    }

    @Test
    public void captureStorageResponseTest() {

        // Expected to have capture with ID of 1 to have storages 1 and 2 (cfe_10 and cfe_11)
        JsonArrayBuilder captureStorageBuilder = Json.createArrayBuilder();
        captureStorageBuilder
                .add(Json.createObjectBuilder().add("storage_id", 1).add("capture_id", 1).add("storage_name", "cfe_10"))
                .add(Json.createObjectBuilder().add("storage_id", 2).add("capture_id", 1).add("storage_name", "cfe_11"));
        JsonArray captureStoragesArray = captureStorageBuilder.build();

        CaptureStorageResponse captureStorageResponse = new CaptureStorageResponse(captureStoragesArray);
        List<PartialCaptureStorageResponse> actualPartialCaptureStorageResponses = captureStorageResponse
                .partialCaptureStorageResponses();

        JsonObjectBuilder captureStorageResponseBuilder1 = Json.createObjectBuilder();
        captureStorageResponseBuilder1.add("storage_id", 1);
        captureStorageResponseBuilder1.add("capture_id", 1);
        captureStorageResponseBuilder1.add("storage_name", "cfe_10");
        JsonObject expected1 = captureStorageResponseBuilder1.build();
        PartialCaptureStorageResponse expectedPartialCaptureStorageResponse1 = new PartialCaptureStorageResponse(
                expected1
        );

        JsonObjectBuilder captureStorageResponseBuilder2 = Json.createObjectBuilder();
        captureStorageResponseBuilder2.add("storage_id", 2);
        captureStorageResponseBuilder2.add("capture_id", 1);
        captureStorageResponseBuilder2.add("storage_name", "cfe_11");
        JsonObject expected2 = captureStorageResponseBuilder2.build();
        PartialCaptureStorageResponse expectedPartialCaptureStorageResponse2 = new PartialCaptureStorageResponse(
                expected2
        );

        List<PartialCaptureStorageResponse> expectedPartialCaptureStorageResponses = new ArrayList<>();
        expectedPartialCaptureStorageResponses.add(expectedPartialCaptureStorageResponse1);
        expectedPartialCaptureStorageResponses.add(expectedPartialCaptureStorageResponse2);

        Assertions.assertEquals(2, actualPartialCaptureStorageResponses.size());
        int loopsExecuted = 0;
        for (PartialCaptureStorageResponse response : actualPartialCaptureStorageResponses) {
            Assertions
                    .assertEquals(expectedPartialCaptureStorageResponses.get(loopsExecuted).captureId(), response.captureId());
            Assertions
                    .assertEquals(expectedPartialCaptureStorageResponses.get(loopsExecuted).storageId(), response.storageId());
            Assertions
                    .assertEquals(expectedPartialCaptureStorageResponses.get(loopsExecuted).storageName(), response.storageName());
            loopsExecuted++;
        }
        Assertions.assertEquals(2, loopsExecuted);

    }

}
