package com.library.library_api.Service;

import com.library.library_api.DTO.LibraryRequestDTO;
import com.library.library_api.DTO.LibraryResponseDTO;
import com.library.library_api.DTO.PagedResponseDTO;
import com.library.library_api.Entity.Library;
import com.library.library_api.Exception.ResourceNotFoundException;
import com.library.library_api.Repository.LibraryRepository;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Builder
public class LibraryService {

    private static final Logger log = LoggerFactory.getLogger(LibraryService.class);
    private final LibraryRepository repository;

    public LibraryService(LibraryRepository repository){
        this.repository = repository;
    }

    // CRUD

    //create
    public LibraryResponseDTO create(LibraryRequestDTO dto){
        Library library = Library.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .available(dto.getAvailable())
                .build();

        Library saved = repository.save(library);
        return toResponseDTO(saved);
    }

    //read (ALL)
    public PagedResponseDTO<LibraryResponseDTO> findAll(Pageable pageable){
        Page<Library> page = repository.findAll(pageable);
        return getLibraryResponseDTOpagedResponseDTO(page);
    }

    //read (ID)
    public LibraryResponseDTO findById(Long Id){
        Library book = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + Id + " not found"));
        return toResponseDTO(book);
    }

    //update
    public LibraryResponseDTO update(Long Id, LibraryRequestDTO dto){
        Library book = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + Id + " not found"));

        if(dto.getTitle() != null){
            book.setTitle(dto.getTitle());
        }
        if (dto.getAuthor() != null){
            book.setAuthor(dto.getAuthor());
        }
        if (dto.getIsbn() != null){
            book.setIsbn(dto.getIsbn());
        }
        book.setAvailable(dto.getAvailable());

        return toResponseDTO(repository.save(book));
    }

    //delete
    public void delete(Long Id){
        Library book = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        repository.delete(book);
    }


    //END-POINTS service

    public void loanBook(Long Id){
        Library book = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (book.getAvailable() != true){
            throw new IllegalStateException("You can't loan a unavailable book");
        } else {
            book.setAvailable(false);
            repository.save(book);
        }
    }

    public void returnBook(Long Id){
        Library book = repository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if(book.getAvailable() == true){
            throw new IllegalStateException("You can't return a available book");
        } else {
            book.setAvailable(true);
            repository.save(book);
        }
    }

    public PagedResponseDTO<LibraryResponseDTO> findAllAvailableTrue(Pageable pageable){
        Page<Library> page = repository.findAllByAvailableIsTrue(pageable);
        return getLibraryResponseDTOpagedResponseDTO(page);
    }

    public PagedResponseDTO<LibraryResponseDTO> findActiveLoans(Pageable pageable){
        Page<Library> page = repository.findAllByAvailableIsFalse(pageable);
        return getLibraryResponseDTOpagedResponseDTO(page);
    }



    //HELPERS

    private LibraryResponseDTO toResponseDTO(Library library){
        return LibraryResponseDTO.builder()
                .Id(library.getId())
                .title(library.getTitle())
                .author(library.getAuthor())
                .isbn(library.getIsbn())
                .available(library.getAvailable())
                .build();
    }

    private PagedResponseDTO<LibraryResponseDTO> getLibraryResponseDTOpagedResponseDTO(Page<Library> pageResult){
        return PagedResponseDTO.<LibraryResponseDTO>builder()
                .content(pageResult.getContent().stream()
                        .map(this::toResponseDTO)
                        .toList()
                )
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }
}