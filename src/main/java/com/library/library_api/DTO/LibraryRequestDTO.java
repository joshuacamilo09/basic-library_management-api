package com.library.library_api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class LibraryRequestDTO {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @Positive(message = "isbn must be a positive number")
    private Long isbn;

    @NotNull(message = "available is required")
    private Boolean available;
}
