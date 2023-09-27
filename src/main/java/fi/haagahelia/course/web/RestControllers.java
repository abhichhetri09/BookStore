package fi.haagahelia.course.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.course.domain.Book;
import fi.haagahelia.course.domain.BookRepository;
import fi.haagahelia.course.domain.CategoryRepository;

@RestController
public class RestControllers {
	@Autowired
	private BookRepository repository; 
    @Autowired
    private CategoryRepository categoryRepository;
    
    @RequestMapping(value= {"/", "/api/books"})
    public List<Book> getAllBooks() {
    	Iterable<Book> bookIterable = repository.findAll();
        List<Book> books = new ArrayList<>();
    	bookIterable.forEach(books::add);

        books.forEach(book -> {
            book.setCategory(null); 
        });

        return books;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> optionalBook = repository.findById(id);
        
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setCategory(null);
            
            return ResponseEntity.ok(book);
        } else {
            // Handle book not found error here
            // You can throw an exception or return a specific response
            return ResponseEntity.notFound().build();
        }
    }
}