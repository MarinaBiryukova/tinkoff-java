package ru.tinkoff.edu.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.request.LinkUpdate;
import ru.tinkoff.edu.service.LinkUpdateReceiver;

@AllArgsConstructor
@RestController
public class LinkUpdateController {
    private LinkUpdateReceiver linkUpdateReceiver;

    @PostMapping("/updates")
    public void update(@RequestBody LinkUpdate request) {
        linkUpdateReceiver.receiveUpdate(request);
    }
}
