package pt.openup.bookservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.openup.bookservice.model.*;
import pt.openup.bookservice.repository.*;
import java.util.List;
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepo;
    private final ReviewRepository reviewRepo;
    // ── Livros (PostgreSQL) ──────────────────────────────
    @GetMapping
    public List<Book> listAll() {
        return bookRepo.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return bookRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public List<Book> search(@RequestParam String query) {
        List<Book> byTitle = bookRepo
                .findByTitleContainingIgnoreCase(query);
        List<Book> byAuthor = bookRepo
                .findByAuthorContainingIgnoreCase(query);
        byTitle.addAll(byAuthor);
        return byTitle.stream().distinct().toList();
    }
    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepo.save(book);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id, @RequestBody Book updated) {
        return bookRepo.findById(id).map(b -> {
            b.setTitle(updated.getTitle());
            b.setAuthor(updated.getAuthor());
            b.setYear(updated.getYear());
            b.setGenre(updated.getGenre());
            return ResponseEntity.ok(bookRepo.save(b));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // ── Avaliações (MongoDB) ─────────────────────────────
    @GetMapping("/{id}/reviews")
    public List<Review> getReviews(@PathVariable Long id) {
        return reviewRepo.findByBookId(id);
    }
    @PostMapping("/{id}/reviews")
    public Review addReview(
            @PathVariable Long id, @RequestBody Review review) {
        review.setBookId(id);
        return reviewRepo.save(review);
    }
}