package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import jakarta.json.Json;

import java.io.IOException;

public final class CaptureGroupRequestFake implements CaptureGroupRequest {
    @Override
    public CaptureGroupResponse captureGroupResponse(final String groupName) throws IOException {
        return new CaptureGroupResponse(
                Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("capture_def_group_name", groupName)
                                .add("capture_definition_id", 1)
                                .add("capture_group_type", "relp")
                                .add("id", 1)).build()
        );
    }
}
