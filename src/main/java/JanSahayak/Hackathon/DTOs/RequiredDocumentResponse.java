package JanSahayak.Hackathon.DTOs;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequiredDocumentResponse {

    private Long id;

    private String name;

    private String description;

    private boolean mandatory;
}
