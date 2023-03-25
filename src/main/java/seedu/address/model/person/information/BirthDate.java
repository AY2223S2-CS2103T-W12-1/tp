package seedu.address.model.person.information;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import static java.util.Objects.requireNonNull;
import java.time.format.DateTimeParseException;
import java.time.Period;

public class BirthDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Please ensure the specified birth date follow this format: YYYY-MM-DD";
    public static final String INVALID_BIRTHDATE = "Invalid birth date specified";
    public static final String VALIDATION_REGEX =
            "^(?<year>\\d{4})-(?<month>0[0-9]|1[0-2])-(?<day>0[0-9]|1[0-9]|2[0-9]|3[0-1])$";

    public final LocalDate birthDate;

    public BirthDate(String birthDate) {
        requireNonNull(birthDate);
        checkArgument(isValidBirthDate(birthDate), MESSAGE_CONSTRAINTS);
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_BIRTHDATE);
        }

        if (!this.birthDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(INVALID_BIRTHDATE);
        }
    }

    public static boolean isValidBirthDate(String date) {
        return date.matches(VALIDATION_REGEX);
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

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
