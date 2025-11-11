package ao.wiza.backend.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@Document(collection = "partners")
public class Partner {
  @Id
  private String id;
  private String name;
  private String taxId;

  @DocumentReference
  private User owner;

  @DocumentReference
  private Set<User> employees;

  @DocumentReference
  private Set<Post> posts;

  @CreatedDate
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
