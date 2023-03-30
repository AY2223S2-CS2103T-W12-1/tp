package seedu.address.model.person.information;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.Parser.FIELD_NOT_SPECIFIED;

/**
 * Represents an Elderly's risk level in FriendlyLink.
 * Guarantees: immutable; is valid as declared in {@link #isValidRisk(String)}
 */
public class RiskLevel {

    public static final String MESSAGE_CONSTRAINTS = "Invalid arguments. \n"
            + "Risk level should only contain 3 types of values, low, medium or high";

    public final Risk riskStatus;

    /**
     * The enum for Risk levels: low, medium or high.
     */
    public enum Risk {
        LOW,
        MEDIUM,
        HIGH,
        NOT_SPECIFIED
    }

    /**
     * Constructs an {@code RiskLevel}.
     *
     * @param risk A valid risk level.
     */
    public RiskLevel(String risk) {
        requireNonNull(risk);
        checkArgument(isValidRisk(risk), MESSAGE_CONSTRAINTS);
        if (risk.equals(FIELD_NOT_SPECIFIED)) {
            riskStatus = Risk.NOT_SPECIFIED;
        } else {
            riskStatus = Risk.valueOf(risk.toUpperCase());
        }
    }

    /**
     * Returns true if a given string is a valid risk level.
     *
     * @param risk Risk level to be tested.
     * @return True if {@code test} is a valid risk level and false otherwise.
     */
    public static boolean isValidRisk(String risk) {
        if (risk == null || risk.toLowerCase().equals(Risk.NOT_SPECIFIED.name().toLowerCase())) {
            return false;
        } else if (risk.equals(FIELD_NOT_SPECIFIED)) {
            return risk.equals(FIELD_NOT_SPECIFIED);
        }
        try {
            Risk.valueOf(risk.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (riskStatus == Risk.NOT_SPECIFIED) {
            return FIELD_NOT_SPECIFIED;
        }
        return riskStatus.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RiskLevel // instanceof handles nulls
                && riskStatus.equals(((RiskLevel) other).riskStatus)); // state check
    }

    @Override
    public int hashCode() {
        return riskStatus.hashCode();
    }

}
