package JanSahayak.Hackathon.Service;

import JanSahayak.Hackathon.DTOs.EligibilityRequest;
import JanSahayak.Hackathon.Entities.EligibilityRule;
import org.springframework.stereotype.Service;

@Service
public class EligibilityRuleEvaluator {

    public boolean evaluate(
            EligibilityRule rule,
            EligibilityRequest user
    ) {

        // Rule cannot be checked using frontend answers.
        // Don't reject the scheme because of it.

        // Safety check
        if (rule.getField() == null || rule.getOperator() == null) {
            return true;
        }

        String actualValue = getUserValue(rule, user);

        // User didn't provide a value
        if (actualValue == null) {
            return !Boolean.TRUE.equals(rule.getMandatory());
        }

        String expectedValue = rule.getExpectedValue();

        if (expectedValue == null) {
            return true;
        }

        return switch (rule.getOperator()) {

            case EQUALS ->
                    actualValue.equalsIgnoreCase(expectedValue);

            case NOT_EQUALS ->
                    !actualValue.equalsIgnoreCase(expectedValue);

            case GREATER_THAN ->
                    Double.parseDouble(actualValue)
                            > Double.parseDouble(expectedValue);

            case GREATER_THAN_OR_EQUAL ->
                    Double.parseDouble(actualValue)
                            >= Double.parseDouble(expectedValue);

            case LESS_THAN ->
                    Double.parseDouble(actualValue)
                            < Double.parseDouble(expectedValue);

            case LESS_THAN_OR_EQUAL ->
                    Double.parseDouble(actualValue)
                            <= Double.parseDouble(expectedValue);

            case IN ->
                    containsValue(expectedValue, actualValue);

            case NOT_IN ->
                    !containsValue(expectedValue, actualValue);
        };
    }


    private String getUserValue(
            EligibilityRule rule,
            EligibilityRequest user
    ) {

        return switch (rule.getField()) {

            case AGE ->
                    valueOf(user.getAge());

            case GENDER ->
                    user.getGender();

            case STATE ->
                    user.getState();

            case CITY ->
                    user.getCity();

            case ANNUAL_INCOME ->
                    valueOf(user.getAnnualIncome());

            case CATEGORY ->
                    valueOf(user.getCategory());

            case MINORITY ->
                    valueOf(user.getMinority());

            case DISABLED ->
                    valueOf(user.getDisabled());

            case STUDENT ->
                    valueOf(user.getStudent());

            case EMPLOYMENT_STATUS ->
                    valueOf(user.getEmploymentStatus());

            case HIGHEST_EDUCATION ->
                    valueOf(user.getHighestEducation());
        };
    }


    private String valueOf(Object value) {
        return value == null ? null : String.valueOf(value);
    }


    private boolean containsValue(
            String expectedValues,
            String actualValue
    ) {

        String[] values = expectedValues.split(",");

        for (String value : values) {

            if (value.trim().equalsIgnoreCase(actualValue.trim())) {
                return true;
            }
        }

        return false;
    }
}