package com.anukriti.tinyURL.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlMapping {
    private final String shortCode;
    private final String longUrl;
}
