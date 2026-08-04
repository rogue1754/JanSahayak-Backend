package JanSahayak.Hackathon.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EligibilityStatus {
    ELIGIBLE,
    NOT_ELIGIBLE,
    NEEDS_INFORMATION;


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
