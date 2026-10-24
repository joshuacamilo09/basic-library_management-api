package com.library.library_api.Repository;

import com.library.library_api.Entity.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    Page<Library> findAllByAvailableIsTrue(Pageable pageable);

    Page<Library> findAllByAvailableIsFalse(Pageable pageable);
}
