package ru.tinkoff.edu.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.request.AddLinkRequest;
import ru.tinkoff.edu.request.RemoveLinkRequest;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;

import java.util.List;

@RestController()
@RequestMapping("/links")
public class LinksController {
    private final LinkResponse defaultResponse = new LinkResponse(1L, null);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ListLinksResponse getTrackedLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        return new ListLinksResponse(List.of(defaultResponse), 1);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkResponse addTrackedLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody AddLinkRequest request) {
        System.out.println("For chat '" + id +"' link '" + request.getLink() + "' was added");
        return defaultResponse;
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public LinkResponse deleteTrackedLink(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody RemoveLinkRequest request) {
        System.out.println("For chat '" + id +"' link '" + request.getLink() + "' was deleted");
        return defaultResponse;
    }
}
