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
package com.teragrep.cfe_41.indexes;

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cfe_41.capture.*;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequestImpl;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import com.teragrep.cfe_41.captureGroup.PartialCaptureResponse;
import com.teragrep.cfe_41.target.PrintableCaptures;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class IndexesJson {

    private final ApiConfig apiConfig;
    private final String groupName;
    private final CaptureGroupRequest captureGroupRequest;
    private final CaptureStorageRequest captureStorageRequest;
    private final CaptureRequest captureRequest;

    public IndexesJson(final ApiConfig apiConfig, final String groupName) {
        this(
                apiConfig,
                groupName,
                new CaptureGroupRequestImpl(apiConfig),
                new CaptureStorageRequestImpl(apiConfig),
                new CaptureRequestImpl(apiConfig)
        );
    }

    public IndexesJson(
            final ApiConfig apiConfig,
            final String groupName,
            final CaptureGroupRequest captureGroupRequest,
            final CaptureStorageRequest captureStorageRequest,
            final CaptureRequest captureRequest
    ) {
        this.apiConfig = apiConfig;
        this.groupName = groupName;
        this.captureGroupRequest = captureGroupRequest;
        this.captureStorageRequest = captureStorageRequest;
        this.captureRequest = captureRequest;
    }

    public PrintableCaptures asPrintableCaptures() throws IOException {
        PrintableCaptures rv = new PrintableIndexesCaptures();
        // get CaptureGroupResponse for specified capture group name
        final CaptureGroupResponse cgResponse = captureGroupRequest.captureGroupResponse(groupName);
        final List<PartialCaptureResponse> partialCaptureResponses = cgResponse.partialCaptureResponses();

        for (final PartialCaptureResponse partialCaptureResponse : partialCaptureResponses) {
            // For each PartialCaptureResponse get CaptureResponses
            final int captureDefinitionId = partialCaptureResponse.captureDefinitionId();
            final String captureGroupType = partialCaptureResponse.captureGroupType();

            final CaptureResponse captureResponse = captureRequest
                    .captureResponse(captureDefinitionId, captureGroupType);

            rv = rv.withCapture(captureResponse);
        }

        return rv;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IndexesJson that = (IndexesJson) o;
        return Objects.equals(apiConfig, that.apiConfig) && Objects.equals(groupName, that.groupName)
                && Objects.equals(captureGroupRequest, that.captureGroupRequest) && Objects.equals(captureStorageRequest, that.captureStorageRequest) && Objects.equals(captureRequest, that.captureRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiConfig, groupName, captureGroupRequest, captureStorageRequest, captureRequest);
    }
}
