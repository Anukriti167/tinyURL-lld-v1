package com.anukriti.tinyURL.controller;

import com.anukriti.tinyURL.service.TinyUrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/tiny")
@AllArgsConstructor
public class TinyUrlController {
    private TinyUrlService tinyUrlService;

    @PostMapping("/shorten") /*Create a short URL*/
    public String shortenUrl(@RequestParam String longUrl){
        return tinyUrlService.getTinyUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = tinyUrlService.getLongUrl(shortUrl);

        if(longUrl == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.sendRedirect(longUrl);
    }
}
