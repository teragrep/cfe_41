package com.teragrep.cfe_41.sourcetypes;

import com.teragrep.cfe_41.ApiConfig;
import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.capture.CaptureRequestImpl;
import com.teragrep.cfe_41.capture.CaptureResponse;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupRequestImpl;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import com.teragrep.cfe_41.captureGroup.PartialCaptureResponse;
import com.teragrep.cfe_41.target.PrintableCaptures;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class SourcetypesJson {
    private final ApiConfig apiConfig;
    private final String groupName;
    private final CaptureGroupRequest captureGroupRequest;
    private final CaptureRequest captureRequest;

    public SourcetypesJson(final ApiConfig apiConfig, final String groupName) {
        this(apiConfig, groupName, new CaptureGroupRequestImpl(apiConfig), new CaptureRequestImpl(apiConfig));
    }

    public SourcetypesJson(final ApiConfig apiConfig, final String groupName, final CaptureGroupRequest captureGroupRequest, final CaptureRequest captureRequest) {
        this.apiConfig = apiConfig;
        this.groupName = groupName;
        this.captureGroupRequest = captureGroupRequest;
        this.captureRequest = captureRequest;
    }

    public PrintableCaptures asPrintableCaptures() throws IOException {
        PrintableCaptures rv = new PrintableSourcetypesCaptures();

        // get captureGroupResponse for specified capture group name
        final CaptureGroupResponse cgResponse = captureGroupRequest.captureGroupResponse(groupName);
        final List<PartialCaptureResponse> partialCaptureResponses = cgResponse.partialCaptureResponses();

        for (final PartialCaptureResponse partialCaptureResponse : partialCaptureResponses) {
            // for each PartialCaptureResponse get CaptureResponses
            final int captureDefinitionId = partialCaptureResponse.captureDefinitionId();
            final String captureGroupType = partialCaptureResponse.captureGroupType();

            final CaptureResponse captureResponse = captureRequest.captureResponse(
                captureDefinitionId, captureGroupType
            );

            rv = rv.withCapture(captureResponse);
        }

        return rv;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourcetypesJson that = (SourcetypesJson) o;
        return Objects.equals(apiConfig, that.apiConfig) && Objects.equals(groupName, that.groupName) && Objects.equals(captureGroupRequest, that.captureGroupRequest) && Objects.equals(captureRequest, that.captureRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiConfig, groupName, captureGroupRequest, captureRequest);
    }
}
