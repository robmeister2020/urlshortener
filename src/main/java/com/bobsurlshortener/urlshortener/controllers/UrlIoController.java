package com.bobsurlshortener.urlshortener.controllers;

import com.bobsurlshortener.urlshortener.models.RawUrl;
import com.bobsurlshortener.urlshortener.services.UrlShorteningService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/")
public class UrlIoController {

    private static final Logger log = LoggerFactory.getLogger(UrlIoController.class);

    private final UrlShorteningService urlShorteningService = new UrlShorteningService();

    /**
     *
     * @param rawUrl - The user-submitted URL
     * @return ResponseEntity - Contains a shortened URL or an error message
     */
    @RequestMapping(
            value = "createurl",
            method = RequestMethod.GET
    )

    public @ResponseBody
    ResponseEntity<String> shortenUrl(@RequestParam("url") String url) {
        RawUrl rawUrl = new RawUrl(url);
        log.info("Raw URL Received: {}", rawUrl.getUrl());

        ResponseEntity<String> urlShortenerResponse = urlShorteningService.shortenUrl(rawUrl);

        return urlShortenerResponse;
    }

    /**
     *
     * @param urlExtension - Path variable containing the unique section of a previously generated URL
     * @return ResponseEntity - Contains a redirect header to the original URL or an error message if
     * the URL was not found in the DB
     */
    @RequestMapping(
            value = "/{urlExtension}",
            method = RequestMethod.GET
    )

    public @ResponseBody
    ResponseEntity<String> performRedirect(@PathVariable("urlExtension") String urlExtension) {
        log.info("URL Extension Received: {}", urlExtension);
        ResponseEntity<String> urlShortenerResponse = urlShorteningService.getFullUrl(urlExtension);
        return urlShortenerResponse;
    }
}
