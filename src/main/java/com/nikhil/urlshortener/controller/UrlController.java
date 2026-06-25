package com.nikhil.urlshortener.controller;

import com.nikhil.urlshortener.entity.Url;
import com.nikhil.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    // Create Short URL
    @PostMapping("/shorten")
    public String shortenUrl(
            @RequestParam String originalUrl,
            @RequestParam(required = false) String customCode,
            Model model) {

        Url url =
                urlService.shortenUrl(
                        originalUrl,
                        customCode
                );

        String shortUrl =
                "http://localhost:8080/api/url/"
                        + url.getShortCode();

        model.addAttribute(
                "shortUrl",
                shortUrl
        );

        return "result";
    }

    // Redirect to Original URL
    @GetMapping("/{shortCode}")
    public String redirectUrl(
            @PathVariable String shortCode) {

        Url url =
                urlService.getOriginalUrl(shortCode);

        return "redirect:" +
                url.getOriginalUrl();
    }

    // Statistics Page
    @GetMapping("/stats/{shortCode}")
    public String getStats(
            @PathVariable String shortCode,
            Model model) {

        Url url =
                urlService.getUrlByShortCode(shortCode);

        model.addAttribute("url", url);

        return "stats";
    }

    // History Page
    @GetMapping("/history")
    public String history(Model model) {

        model.addAttribute(
                "urls",
                urlService.getAllUrls()
        );

        return "history";
    }

    // Delete URL
    @GetMapping("/delete/{id}")
    public String deleteUrl(
            @PathVariable Long id) {

        urlService.deleteUrl(id);

        return "redirect:/api/url/history";
    }

    // Test API
    @ResponseBody
    @GetMapping("/test")
    public Url test() {

        return urlService.shortenUrl(
                "https://google.com"
        );
    }
}