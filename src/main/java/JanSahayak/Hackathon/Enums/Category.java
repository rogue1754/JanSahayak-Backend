package JanSahayak.Hackathon.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {

    GENERAL,
    EWS,
    OBC,
    SC,
    ST,
    PVTG,
    DNT,
    OTHER;

    @JsonCreator
    public static Category fromValue(String value) {

        if (value == null) {
            return null;
        }

        switch (value.trim().toLowerCase()) {

            case "general":
                return GENERAL;

            case "ews":
                return EWS;

            case "obc":
                return OBC;

            case "sc":
                return SC;

            case "st":
                return ST;

            case "pvtg":
                return PVTG;

            case "dnt":
                return DNT;

            case "other":
                return OTHER;

            default:
                throw new IllegalArgumentException("Unknown category: " + value);
        }
    }
}