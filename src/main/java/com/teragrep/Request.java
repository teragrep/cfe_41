package com.teragrep;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface Request {

    HttpResponse doRequest() throws IOException;
}
