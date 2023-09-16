package com.group.libraryapp.domain.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> { // id값은 필수
	Optional<Book> findByName(String name);
}
 