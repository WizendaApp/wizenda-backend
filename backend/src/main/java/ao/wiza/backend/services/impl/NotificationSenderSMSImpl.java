package ao.wiza.backend.services.impl;

import ao.wiza.backend.dto.TelcoSMSRequest;
import ao.wiza.backend.dto.TelcoSMSResponse;
import ao.wiza.backend.models.Notification;
import ao.wiza.backend.services.NotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static ao.wiza.backend.models.NotificationType.SMS;

@Service("sms")
public class NotificationSenderSMSImpl implements NotificationSender {
  private final String telcoSMSAPIKey;
  private final RestClient client = RestClient.builder()
      .baseUrl("https://www.telcosms.co.ao")
      .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
      })
      .build();

  public NotificationSenderSMSImpl(@Value("${wiza.services.sms.api-key}") String telcoSMSAPIKey) {
    this.telcoSMSAPIKey = telcoSMSAPIKey;
  }

  @Override
  public void send(Notification notification) {
    if (notification.getType() != SMS) return;

    var response = client.post()
        .uri("/api/v2/send_message")
        .body(new TelcoSMSRequest(
            new TelcoSMSRequest.Message(
                telcoSMSAPIKey,
                notification.getTo(),
                notification.getContent()
            )
        )).retrieve()
        .toEntity(TelcoSMSResponse.class);

    System.out.println(response.getBody());
    System.out.println();
  }

}
