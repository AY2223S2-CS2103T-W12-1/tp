package seedu.address.model.person.information;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.Parser.FIELD_NOT_SPECIFIED;

/**
 * Represents a Person's region in FriendlyLink.
 * Guarantees: immutable; is valid as declared in {@link #isValidRegion(String)}
 */
public class Region {
    public static final String MESSAGE_CONSTRAINTS = "Invalid arguments. \n"
            + "Region should only contain 5 types of values, "
            + "north, northeast, east, west, central";
    public final Place region;

    /**
     * The enum for Region: north, northeast, east, west, central
     */
    public enum Place {
        NORTH,
        NORTHEAST,
        EAST,
        WEST,
        CENTRAL,
        NOT_SPECIFIED
    }

    /**
     * Constructs an {@code Region}.
     *
     * @param region A valid region.
     */
    public Region(String region) {
        requireNonNull(region);
        checkArgument(isValidRegion(region), MESSAGE_CONSTRAINTS);
        if (region.equals(FIELD_NOT_SPECIFIED)) {
            this.region = Place.NOT_SPECIFIED;
        } else {
            this.region = Place.valueOf(region.toUpperCase());
        }
    }

    /**
     * Returns true if a given string is a valid region.
     *
     * @param region Region to be tested.
     * @return True if {@code test} is a valid region and false otherwise.
     */
    public static boolean isValidRegion(String region) {
        if (region == null || region.toLowerCase().equals(Region.Place.NOT_SPECIFIED.name().toLowerCase())) {
            return false;
        } else if (region.equals(FIELD_NOT_SPECIFIED)) {
            return true;
        }
        try {
            Place.valueOf(region.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks whether this region mathces with another region.
     * Two regions match if they are equal or if one of the region is unspecified.
     *
     * @param anotherRegion Other region.
     * @return True if the 2 regions match, false otherwise.
     */
    public boolean isMatch(Region anotherRegion) {
        return region.equals(Place.NOT_SPECIFIED) || anotherRegion.region.equals(Place.NOT_SPECIFIED)
                || region.equals(anotherRegion.region);
    }

    @Override
    public String toString() {
        if (region.equals(Place.NOT_SPECIFIED)) {
            return FIELD_NOT_SPECIFIED;
        }
        return region.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Region
                && region.equals(((Region) other).region));
    }

    @Override
    public int hashCode() {
        return region.hashCode();
    }

}
