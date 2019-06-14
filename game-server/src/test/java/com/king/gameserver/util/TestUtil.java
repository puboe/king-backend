package com.king.gameserver.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestUtil {

    private TestUtil() {
    }

    public static HttpExchange mockHttpExchange(final String uri, final String requestMethod, final String body) throws URISyntaxException {

        final HttpExchange httpExchange = mock(HttpExchange.class);

        final URI requestURI = new URI(uri);

        when(httpExchange.getRequestURI()).thenReturn(requestURI);
        when(httpExchange.getRequestMethod()).thenReturn(requestMethod);

        if (body != null) {
            final InputStream inputStream = new ByteArrayInputStream(body.getBytes());
            when(httpExchange.getRequestBody()).thenReturn(inputStream);
        }

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(httpExchange.getResponseBody()).thenReturn(out);

        return httpExchange;
    }
}
