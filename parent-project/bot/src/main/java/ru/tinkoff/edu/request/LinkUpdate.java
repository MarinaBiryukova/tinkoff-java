package ru.tinkoff.edu.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdate {
    private Integer id;
    private String url;
    private String description;
    private List<Integer> tgChatIds;
}
