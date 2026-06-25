package com.nikhil.urlshortener.controller;

import com.nikhil.urlshortener.entity.Url;
import com.nikhil.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String originalUrl,
                             Model model) {

        Url url = urlService.shortenUrl(originalUrl);

        String shortUrl =
                "http://localhost:8080/api/url/"
                        + url.getShortCode();

        model.addAttribute("shortUrl", shortUrl);

        return "result";
    }
}