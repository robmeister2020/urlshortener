package com.bobsurlshortener.urlshortener.services;

import com.bobsurlshortener.urlshortener.controllers.UrlIoController;
import com.bobsurlshortener.urlshortener.models.RawUrl;
import com.bobsurlshortener.urlshortener.utils.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UrlShorteningService {

    private static final Logger log = LoggerFactory.getLogger(UrlShorteningService.class);

    private UrlUtil urlUtil;

    /**
     *
     * @param rawUrl - User-submitted URL
     * @return ResponseEntity - Contains either a shortened URL or an error message
     * Handles URL shortening
     */
    public ResponseEntity<String> shortenUrl(RawUrl rawUrl) {
        urlUtil = new UrlUtil();

        if(urlUtil.isUrlValid(rawUrl.getUrl())) {
            return new ResponseEntity<String>(urlUtil.shortenUrl(rawUrl.getUrl()), HttpStatus.OK);
        } else {
            log.info("Invalid URL: {}", rawUrl.getUrl());
            return new ResponseEntity<String>("Invalid URL, please enter a valid URL.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *
     * @param urlExtension - the unique section of the url that was generated previously
     * @return ResponseEntity - Contains either a redirect to the original URL or a failure message
     * Redirects the user or gives them an error message if the URL doesn't exist in the DB
     */
    public ResponseEntity<String> getFullUrl(String urlExtension) {
        urlUtil = new UrlUtil();
        return urlUtil.getFullUrl(urlExtension);
    }
}
