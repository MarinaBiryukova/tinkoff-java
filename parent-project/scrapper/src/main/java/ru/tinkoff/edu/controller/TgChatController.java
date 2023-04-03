package ru.tinkoff.edu.controller;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping(value = "/{id}")
    public void registerChat(@PathVariable("id") Long id) {
        System.out.println("Chat with id '" + id.toString() + "' was registered");
    }

    @DeleteMapping(value = "{id}")
    public void deleteChat(@PathVariable("id") Long id) {
        System.out.println("Chat with id '" + id.toString() + "' was deleted");
    }
}
