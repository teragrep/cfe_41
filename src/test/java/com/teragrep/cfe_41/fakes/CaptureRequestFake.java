package com.teragrep.cfe_41.fakes;

import com.teragrep.cfe_41.capture.CaptureRequest;
import com.teragrep.cfe_41.capture.CaptureResponse;
import jakarta.json.Json;

import java.io.IOException;

public final class CaptureRequestFake implements CaptureRequest {
    @Override
    public CaptureResponse captureResponse(final int id, final String captureType) throws IOException {
        return new CaptureResponse(
                Json.createObjectBuilder()
                        .add("id", id)
                        .add("tag","fake-tag")
                        .add("retention_time","fake-retention")
                        .add("category","fake-category")
                        .add("application","fake-app")
                        .add("index","fake-index")
                        .add("source_type","fake-source")
                        .add("protocol","fake-protocol")
                        .add("flow","fake-flow")
                        .build()
        );
    }
}
