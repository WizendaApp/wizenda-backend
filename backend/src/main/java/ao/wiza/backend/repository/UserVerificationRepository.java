package ao.wiza.backend.repository;

import ao.wiza.backend.models.User;
import ao.wiza.backend.models.UserVerification;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationRepository extends MongoRepository<@NonNull UserVerification, @NonNull String> {
  @Query(value = "{ 'user': ObjectId(?0), 'code': ?1, 'isActive': true }", exists = true)
  boolean existsValidCode(@NonNull User user, @NonNull String code);

  @Query("{ 'user': ObjectId(?0), 'code': ?1, 'isActive': true }")
  @Nullable UserVerification findValidCode(@NonNull String user, @NonNull String code);
}
