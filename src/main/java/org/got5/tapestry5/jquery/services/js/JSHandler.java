package org.got5.tapestry5.jquery.services.js;

import java.io.IOException;

import org.apache.tapestry5.services.Response;

public interface JSHandler {
    public boolean handleRequest(String path, Response response) throws IOException;
}
