package ru.tinkoff.edu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.request.LinkUpdate;

@RestController()
public class LinkUpdateController {

    @PostMapping("/updates")
    public void update(@RequestBody LinkUpdate request) {

    }
}
