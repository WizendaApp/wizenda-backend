package ao.wiza.backend.repository;

import ao.wiza.backend.models.Notification;
import org.jspecify.annotations.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<@NonNull Notification, @NonNull String> {
}
