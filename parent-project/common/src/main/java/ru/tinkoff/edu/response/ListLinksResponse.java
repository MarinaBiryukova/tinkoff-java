package ru.tinkoff.edu.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private Integer size;
}
