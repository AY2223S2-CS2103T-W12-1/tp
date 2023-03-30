package seedu.address.model.person.information;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.Parser.FIELD_NOT_SPECIFIED;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    public void constructor_invalidRegion_throwsIllegalArgumentException() {
        String invalidRegion = "abc";
        assertThrows(IllegalArgumentException.class, () -> new Region(invalidRegion));
    }

    @Test
    public void isValidRegion() {
        // null address
        assertFalse(Region.isValidRegion(null));

        // invalid region
        assertFalse(Region.isValidRegion(" ")); // spaces only
        assertFalse(Region.isValidRegion("hello")); // random letters
        assertFalse(Region.isValidRegion(Region.Place.NOT_SPECIFIED.name())); // "not specified" enum

        // valid region
        assertTrue(Region.isValidRegion(FIELD_NOT_SPECIFIED));
        assertTrue(Region.isValidRegion("north"));
        assertTrue(Region.isValidRegion("NORTHEAST"));
        assertTrue(Region.isValidRegion("CenTraL"));
    }

    @Test
    public void isMatch() {
        Region targetRegion = new Region("NORTH");
        assertFalse(targetRegion.isMatch(new Region("NORTHEAST")));
        assertFalse(targetRegion.isMatch(new Region("EAST")));
        assertTrue(targetRegion.isMatch(new Region("NORTH")));
        assertTrue(targetRegion.isMatch(new Region(FIELD_NOT_SPECIFIED)));
    }

}
