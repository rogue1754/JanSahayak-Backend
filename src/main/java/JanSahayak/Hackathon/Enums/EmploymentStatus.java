package JanSahayak.Hackathon.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EmploymentStatus {
    STUDENT,
    EMPLOYED,
    SELF_EMPLOYED,
    UNEMPLOYED,
    RETIRED,
    HOMEMAKER;

    @JsonCreator
    public static EmploymentStatus fromValue(String value) {

        if (value == null) {
            return null;
        }

        return EmploymentStatus.valueOf(
                value.trim()
                        .toUpperCase()
                        .replace(" ", "_")
        );
    }

}