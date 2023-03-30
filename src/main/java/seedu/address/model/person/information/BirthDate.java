package seedu.address.model.person.information;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;


/**
 * Represents a Person's BirthDate in FriendlyLink.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthDate(String)}
 */
public class BirthDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Please ensure the specified birth date follow this format: YYYY-MM-DD.\n"
                + "Also ensure that it is a valid calendar date, "
                + "and that it is before the current date.";
    public static final String VALIDATION_REGEX =
            "^(?<year>\\d{4})-(?<month>0[0-9]|1[0-2])-(?<day>0[0-9]|1[0-9]|2[0-9]|3[0-1])$";

    public final LocalDate birthDate;

    /**
     * Constructs an {@code BirthDate}.
     *
     * @param birthDate A valid BirthDate.
     */
    public BirthDate(String birthDate) {
        requireNonNull(birthDate);
        checkArgument(isValidBirthDate(birthDate), MESSAGE_CONSTRAINTS);
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        if (!this.birthDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid BirthDate.
     *
     * @param date Birth Date to be tested.
     * @return True if {@code date} is a valid BirthDate and false otherwise.
     */
    public static boolean isValidBirthDate(String date) {
        if (!date.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDate dateToCheck = LocalDate.parse(date);
            if (!dateToCheck.isBefore(LocalDate.now())) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Get the current age of the person, calculated based on current year.
     */
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public String toString() {
        return birthDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BirthDate
                && birthDate.equals(((BirthDate) other).birthDate));
    }

    @Override
    public int hashCode() {
        return birthDate.hashCode();
    }
}
