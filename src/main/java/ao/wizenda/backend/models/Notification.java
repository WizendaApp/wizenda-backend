package ao.wizenda.backend.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {
  @Id
  private String id;
  private NotificationType type;
  @Indexed(name = "from")
  private String from;
  @Indexed(name = "to")
  private String to;
  @Indexed(name = "subject")
  private String subject;
  private String content;
  @CreatedDate
  private LocalDateTime createdAt;
}
