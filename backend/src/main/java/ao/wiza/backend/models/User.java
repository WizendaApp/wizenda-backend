package ao.wiza.backend.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@Document(collection = "users")
public class User {
  @Id
  private String id;
  private String name;
  @Indexed(unique = true)
  private String email;
  @Indexed(unique = true)
  private String username;
  @Indexed(unique = true)
  private String phone;
  private String password;

  private Role role;

  private boolean isActive;

  @CreatedDate
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
