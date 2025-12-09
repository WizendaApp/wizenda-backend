package ao.wizenda.backend.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document(collection = "user_verifications")
public class UserVerification {
  @Id
  private String id;
  @DocumentReference
  private User user;
  @Indexed(name = "code")
  private String code;
  private boolean isActive;
  @CreatedDate
  @Indexed(expireAfter = "3m")
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;


  public void active() {
    isActive = true;
  }

  public void inactive() {
    isActive = false;
  }
}
