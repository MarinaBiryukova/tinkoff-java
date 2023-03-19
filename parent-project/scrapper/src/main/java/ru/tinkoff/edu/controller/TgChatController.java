package ru.tinkoff.edu.controller;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tg-chat")
public class TgChatController {

    @PostMapping(value = "/{id}")
    public void registerChat(@PathVariable("id") Integer id) {

    }

    @DeleteMapping(value = "{id}")
    public void deleteChat(@PathVariable("id") Integer id) {

    }
}
