package com.library.library_api.Controller;

import com.library.library_api.DTO.LibraryRequestDTO;
import com.library.library_api.DTO.LibraryResponseDTO;
import com.library.library_api.DTO.PagedResponseDTO;
import com.library.library_api.Service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/library")
public class LibraryController {

    private final LibraryService service;
    public LibraryController(LibraryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<LibraryResponseDTO> create (@Valid @RequestBody LibraryRequestDTO dto){
        LibraryResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/list/all")
    public ResponseEntity<PagedResponseDTO<LibraryResponseDTO>> listAll (Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/list/{Id}")
    public ResponseEntity<LibraryResponseDTO> listById(@PathVariable Long Id){
        return ResponseEntity.ok(service.findById(Id));
    }

    @PutMapping("/update/{Id}")
    public ResponseEntity<LibraryResponseDTO> update(@PathVariable Long Id, @RequestBody LibraryRequestDTO dto){
        return ResponseEntity.ok(service.update(Id, dto));
    }

    @DeleteMapping("/delete/{Id}")
    public String delete(@PathVariable Long Id){
        service.delete(Id);
        return "Book with Id " + Id + " deleted with success.";
    }

    @GetMapping("/loan/{Id}")
    public String loanBook(@PathVariable Long Id){
        service.loanBook(Id);
        return "Book " + Id + " was loaned with success";
    }

    @GetMapping("/return/{Id}")
    public String returnBook(@PathVariable Long Id){
        service.returnBook(Id);
        return "Book " + Id + " was returned with success";
    }

    @GetMapping("/available/true")
    public ResponseEntity<PagedResponseDTO<LibraryResponseDTO>> listAvailableTrue(Pageable pageable){
        return ResponseEntity.ok(service.findAllAvailableTrue(pageable));
    }

    @GetMapping("/loans")
    public ResponseEntity<PagedResponseDTO<LibraryResponseDTO>> listActiveLoans(Pageable pageable){
        return ResponseEntity.ok(service.findActiveLoans(pageable));
    }
}
