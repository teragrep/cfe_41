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
package com.teragrep.cfe_41;

import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.capture.CaptureResponse;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import com.teragrep.cfe_41.captureGroup.PartialCaptureResponse;
import com.teragrep.cfe_41.sink.SinkRequest;
import com.teragrep.cfe_41.sink.SinkResponse;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // Creates new ApiConfig from commandline args
        ApiConfig apiConfig = new ApiConfig(new Arguments(args));

        CaptureGroupRequest captureGroupRequest = new CaptureGroupRequest("string", apiConfig);
        CaptureGroupResponse captureGroupResponse = captureGroupRequest.captureGroupResponse();

        List<CaptureRequest> captureRequests = new ArrayList<>();

        for (PartialCaptureResponse captureRequest : captureGroupResponse.partialCaptureResponses()) {
            captureRequests
                    .add(
                            new CaptureRequest(
                                    captureRequest.captureDefinitionId(),
                                    captureRequest.captureGroupType(),
                                    apiConfig
                            )
                    );
        }

        List<CaptureResponse> captureResponses = new ArrayList<>();
        for (CaptureRequest captureResponse : captureRequests) {
            captureResponses.add(captureResponse.captureResponse());
        }

        for (CaptureResponse captureResponse : captureResponses) {
            // Now provides capture_id for later usage
            captureResponse.id();
        }

        List<SinkRequest> sinkRequests = new ArrayList<>();
        // Loop through captures which are in the current group and request sink for each individual capture
        for (CaptureResponse sinkRequest : captureResponses) {
            sinkRequests.add(new SinkRequest(sinkRequest.flow(), sinkRequest.protocol(), apiConfig));
        }

        Set<SinkResponse> sinkResponses = new HashSet<>();
        for (SinkRequest sinkRequest : sinkRequests) {
            sinkResponses.add(sinkRequest.sinkResponse());
        }

    }
}
