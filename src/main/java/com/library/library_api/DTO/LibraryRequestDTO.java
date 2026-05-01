package com.library.library_api.DTO;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class LibraryRequestDTO {
    private String title;
    private String author;
    private Long isbn;
    @NonNull
    private Boolean available;
}
