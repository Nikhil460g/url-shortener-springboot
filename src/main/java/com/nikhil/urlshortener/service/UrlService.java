package com.nikhil.urlshortener.service;

import com.nikhil.urlshortener.entity.Url;
import com.nikhil.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url shortenUrl(String originalUrl) {

        String shortCode =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 6);

        Url url = new Url();

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setClickCount(0);

        return urlRepository.save(url);
    }

    public Url shortenUrl(String originalUrl,
                          String customCode) {

        if (customCode != null &&
                !customCode.isEmpty()) {

            if (urlRepository.existsByShortCode(customCode)) {

                throw new RuntimeException(
                        "Custom URL already exists!"
                );
            }

            Url url = new Url();

            url.setOriginalUrl(originalUrl);
            url.setShortCode(customCode);
            url.setClickCount(0);

            return urlRepository.save(url);
        }

        return shortenUrl(originalUrl);
    }

    public Url getOriginalUrl(String shortCode) {

        Url url =
                urlRepository.findByShortCode(shortCode)
                        .orElseThrow();

        url.setClickCount(url.getClickCount() + 1);

        urlRepository.save(url);

        return url;
    }

    public Url getUrlByShortCode(String shortCode) {

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow();
    }

    public List<Url> getAllUrls() {

        return urlRepository.findAll();
    }

    public void deleteUrl(Long id) {

        urlRepository.deleteById(id);
    }
}