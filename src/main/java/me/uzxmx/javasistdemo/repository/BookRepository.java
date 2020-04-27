package me.uzxmx.javasistdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.uzxmx.javasistdemo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
