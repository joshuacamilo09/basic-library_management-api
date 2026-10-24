package com.library.library_api.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LibraryResponseDTO {
    private Long Id;
    private String title;
    private String author;
    private Long isbn;
    private Boolean available;
}
