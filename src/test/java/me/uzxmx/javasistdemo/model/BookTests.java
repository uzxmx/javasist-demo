package me.uzxmx.javasistdemo.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.uzxmx.javasistdemo.repository.BookRepository;

@DataJpaTest
public class BookTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testOKWhenIdNotSpecified() {
        Book book = new Book();
        book.setName("Learn Spring");
        bookRepository.saveAndFlush(book);

        Book b = bookRepository.findAll().get(0);
        assertTrue(b.getName().equals("Learn Spring"));
    }
}
