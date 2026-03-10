package pt.openup.bookservice.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import pt.openup.bookservice.model.Review;
import java.util.List;
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByBookId(Long bookId);
}