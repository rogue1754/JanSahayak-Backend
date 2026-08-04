package JanSahayak.Hackathon.Entities;

import JanSahayak.Hackathon.Enums.SchemeCategory;
import JanSahayak.Hackathon.Enums.SchemeLevel;
import JanSahayak.Hackathon.Enums.SourceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schemes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String benefits;

    @Enumerated(EnumType.STRING)
    private SchemeCategory category;

    private String ministry;

    @Enumerated(EnumType.STRING)
    private SchemeLevel schemeLevel;

    private String state;

    private String applicationUrl;

    private String applicationDeadline;

    private String sourceUrl;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    private Boolean active;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}