package com.teragrep.cfe_41.api;

import java.io.IOException;

public final class APIException extends IOException {
    public APIException(int code, String reason) {
        super(code + ": " + reason);
    }
    public APIException(String message) {
        super(message);
    }
}
