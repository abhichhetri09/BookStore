package fi.haagahelia.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.course.domain.Book;
import fi.haagahelia.course.domain.Category;
import fi.haagahelia.course.domain.BookRepository;
import fi.haagahelia.course.domain.CategoryRepository;


@SpringBootApplication
public class BookStoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);
    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            log.info("Saving a couple of categories");
            categoryRepository.save(new Category("Fiction"));
            categoryRepository.save(new Category("Non-Fiction"));

            log.info("Save a couple of books");
            Category fictionCategory = categoryRepository.findByName("Fiction");
            bookRepository.save(new Book("Book1", "Johnson", 1990, "1234", 29, fictionCategory));
            bookRepository.save(new Book("Book2", "ABC", 1991, "1991", 35, fictionCategory));

            Category nonFictionCategory = categoryRepository.findByName("Non-Fiction");
            bookRepository.save(new Book("Book3", "XYZ", 1992, "1992", 40, nonFictionCategory));

            log.info("Fetch all books");
            for (Book book : bookRepository.findAll()) {
                log.info(book.toString());
            }
        };
    }
}
