package ao.wizenda.backend.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Document(collection = "posts")
public class Post {
  @Id
  private String id;
  private String title;
  private BigDecimal price;
  private LocalDate date;

  @DocumentReference
  private Partner partner;

  @CreatedDate
  private LocalDateTime postedAt;
}
