package com.group.libraryapp.repository.book;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookMemoryRepository implements BookRepository{
	private final List<Book> books = new ArrayList<>();

	public void saveBook() {
		books.add(new Book());
	}
}
