package JanSahayak.Hackathon.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSeedDTO {

    private String name;
    private String description;
    private boolean mandatory;
}