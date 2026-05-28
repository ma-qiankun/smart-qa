package com.smartqa.infrastructure.parser;

import java.io.InputStream;

public interface DocumentParser {

    String parse(InputStream inputStream);

    boolean supports(String extension);
}
