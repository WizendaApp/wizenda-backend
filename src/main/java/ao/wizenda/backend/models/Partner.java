package ao.wizenda.backend.models;

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
  private PartnerType type;

  @DocumentReference
  private User owner;

  @DocumentReference(lazy = true)
  private Set<User> employees;

  @DocumentReference(lazy = true)
  private Set<Post> posts;

  @CreatedDate
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
