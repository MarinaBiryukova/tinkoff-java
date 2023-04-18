package ru.tinkoff.edu.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.request.AddLinkRequest;
import ru.tinkoff.edu.request.RemoveLinkRequest;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;
import ru.tinkoff.edu.service.LinkService;


@AllArgsConstructor
@RestController()
@RequestMapping("/links")
public class LinksController {
    private final LinkService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ListLinksResponse getTrackedLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        return service.listAll(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkResponse addTrackedLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest request) {
        return service.add(id, request.getUrl());
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkResponse deleteTrackedLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody RemoveLinkRequest request) {
        return service.remove(id, request.getUrl());
    }
}
