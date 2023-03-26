package seedu.address.model.person.information;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's age in FriendlyLink.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {
    public static final String MESSAGE_CONSTRAINTS = "Invalid arguments. \n"
            + "Age should only contain numbers, and it should be 2-3 digits long";
    public static final String VALIDATION_REGEX = "\\d{2,3}";
    public final String value;

    /**
     * Constructs an {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = age;
    }

    /**
     * Returns true if a given string is a valid age.
     *
     * @param test Age to be tested.
     * @return True if {@code test} is a valid age and false otherwise.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value.equals(((Age) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
