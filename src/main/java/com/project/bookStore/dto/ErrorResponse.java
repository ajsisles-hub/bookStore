package com.project.bookStore.dto;

import java.util.Map;

public record ErrorResponse(String message, Map<String, Object> errors) {}
