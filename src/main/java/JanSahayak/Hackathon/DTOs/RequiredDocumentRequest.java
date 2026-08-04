package JanSahayak.Hackathon.DTOs;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequiredDocumentRequest {

    private String name;

    private String description;

    private boolean mandatory;
}
