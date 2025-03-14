package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.capture.CaptureStorageRequest;
import com.teragrep.cfe_41.capture.CaptureStorageResponse;
import jakarta.json.Json;

import java.io.IOException;

public final class CaptureStorageRequestFake implements CaptureStorageRequest {
    @Override
    public CaptureStorageResponse captureStorageResponse(final int captureDefinitionId) throws IOException {
        return new CaptureStorageResponse(
                Json.createArrayBuilder().
                        add(Json.createObjectBuilder().add("storage_id", 1).add("capture_id", captureDefinitionId).add("storage_name", "cfe_10"))
                .add(Json.createObjectBuilder().add("storage_id", 2).add("capture_id", captureDefinitionId).add("storage_name", "cfe_11"))
                        .build()
        );
    }
}
