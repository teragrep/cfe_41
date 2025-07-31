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

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.capture.CaptureRequestImpl;
import com.teragrep.cfe_41.capture.CaptureResponse;
import com.teragrep.cfe_41.captureGroup.*;
import com.teragrep.cfe_41.flow.Flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FlowGroups {

    private final CaptureGroupAllRequest captureGroups;
    private final CaptureRequest captureRequest;

    public FlowGroups(final ApiConfig apiConfig) {
        this(new CaptureGroupAllRequestImpl(apiConfig), new CaptureRequestImpl(apiConfig));
    }

    public FlowGroups(final CaptureGroupAllRequest captureGroups, final CaptureRequest captureRequest) {
        this.captureGroups = captureGroups;
        this.captureRequest = captureRequest;
    }

    public List<FlowWithGroup> RequestAndReturnAllCombinations(final Flow flow) throws IOException {

        // Request All Captures included in the groups
        final List<PartialCaptureGroupResponse> captureGroupsCaptures = captureGroups
                .captureGroupResponse()
                .partialCaptureResponses();

        final List<FlowWithGroup> flowWithGroup = new ArrayList<>();
        // If Flows match between given Flow and Flow inside the group then append that into a list
        for (final CaptureGroup capture : captureGroupsCaptures) {
            final CaptureResponse captureResponse = captureRequest
                    .captureResponse(capture.captureDefinitionId(), capture.captureGroupType());
            // if Capture Flow matches the current Flow
            if (captureResponse.flow().equals(flow.flowName())) {
                flowWithGroup.add(new FlowWithGroup(flow, capture));
            }
        }
        return flowWithGroup;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FlowGroups that = (FlowGroups) o;
        return Objects.equals(captureGroups, that.captureGroups) && Objects.equals(captureRequest, that.captureRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(captureGroups, captureRequest);
    }
}
