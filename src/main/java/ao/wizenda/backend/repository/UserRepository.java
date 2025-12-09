package ao.wizenda.backend.repository;

import ao.wizenda.backend.models.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<@NonNull User, @NonNull String> {
  @Query("{ $or: [{ 'username': { $eq: ?0 } }, { 'email': { $eq: ?0 } }] }")
  @Nullable User findByUsernameOrEmail(@NonNull String usernameOrEmail);
}
