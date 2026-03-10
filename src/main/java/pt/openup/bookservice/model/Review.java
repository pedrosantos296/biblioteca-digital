package pt.openup.bookservice.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private Long bookId; // referência ao ID do livro no PostgreSQL
    private String reviewer;
    private int rating; // 1 a 5
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}