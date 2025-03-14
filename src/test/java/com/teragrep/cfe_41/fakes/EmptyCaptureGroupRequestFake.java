package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.captureGroup.CaptureGroupRequest;
import com.teragrep.cfe_41.captureGroup.CaptureGroupResponse;
import jakarta.json.JsonValue;

public final class EmptyCaptureGroupRequestFake implements CaptureGroupRequest {
    @Override
    public CaptureGroupResponse captureGroupResponse(final String groupName) {
        return new CaptureGroupResponse(JsonValue.EMPTY_JSON_ARRAY);
    }
}
