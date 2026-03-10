package pt.openup.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.openup.bookservice.model.Book;
import pt.openup.bookservice.repository.BookRepository;
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final BookRepository repo;
    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(new Book("Os Maias", "Eca de Queiros",
                    "978-972-1-05280-7", 1888, "Romance"));
            repo.save(new Book("O Nome da Rosa", "Umberto Eco",
                    "978-972-1-03100-0", 1980, "Mistério"));
            repo.save(new Book("Dune", "Frank Herbert",
                    "978-0-441-17271-9", 1965, "Ficção Científica"));
            repo.save(new Book("1984", "George Orwell",
                    "978-0-14-303943-3", 1949, "Distopia"));
            repo.save(new Book("Dom Quixote", "Miguel de Cervantes",
                    "978-972-1-06100-7", 1605, "Aventura"));
            System.out.println("[DataSeeder] 5 livros inseridos!");
        }
    }
}