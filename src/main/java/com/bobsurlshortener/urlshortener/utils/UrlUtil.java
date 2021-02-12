package com.bobsurlshortener.urlshortener.utils;

import com.bobsurlshortener.urlshortener.database.UrlDatabaseDao;
import com.bobsurlshortener.urlshortener.services.UrlShorteningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class UrlUtil {

    private static final Logger log = LoggerFactory.getLogger(UrlUtil.class);

    private String ROOT_URL = "http://localhost:8090/";

    public boolean isUrlValid(String rawUrl) {
        String urlForValidation = "";

        if(rawUrl.startsWith("http")) {
            urlForValidation = rawUrl;
        } else {
            urlForValidation = "http://" + rawUrl;
        }

        try {
            URL url = new URL(urlForValidation);
            return true;
        } catch(MalformedURLException mue) {
            return false;
        }
    }

    public String shortenUrl(String url) {
        String generatedUrl;

        if(UrlDatabaseDao.checkIfSourceUrlExists(url)) {
            return ROOT_URL + UrlDatabaseDao.getExistingShortenedUrl(url);
        }

        StringBuilder urlPostfix = new StringBuilder(8);

        String[] alphaNumericArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                                                  "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                                                  "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
                                                  "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                                                  "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2",
                                                  "3", "4", "5", "6", "7", "8", "9"};

        Random randomGenerator = new Random();
        int randomNumber;
        while(urlPostfix.length() < 8) {
            randomNumber = randomGenerator.nextInt((alphaNumericArray.length - 1));
            urlPostfix.append(alphaNumericArray[randomNumber]);
        }
        generatedUrl = urlPostfix.toString();

        if(UrlDatabaseDao.checkIfGeneratedUrlExists(urlPostfix.toString())) {
            shortenUrl(url);
        }

        UrlDatabaseDao.persistUrl(url, generatedUrl);
        String fullUrl = ROOT_URL + generatedUrl;
        log.info("Generated URL: {}", fullUrl)
        return fullUrl;
    }

    public ResponseEntity<String> getFullUrl(String urlExtension) {
        String fullUrl = UrlDatabaseDao.getFullUrl(urlExtension);

        if(fullUrl.isEmpty()) {
            log.info("Full URL for {} not found in the DB", ROOT_URL + urlExtension)
            return new ResponseEntity<String>("We don't recognise this URL", HttpStatus.BAD_REQUEST);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", fullUrl);
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        }
    }
}
