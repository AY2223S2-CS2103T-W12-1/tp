package seedu.address.testutil;

import seedu.address.model.FriendlyLink;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class FriendlyLinkBuilder {

    private final FriendlyLink friendlyLink;

    public FriendlyLinkBuilder() {
        friendlyLink = new FriendlyLink();
    }

    public FriendlyLinkBuilder(FriendlyLink friendlyLink) {
        this.friendlyLink = friendlyLink;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public FriendlyLinkBuilder withPerson(Person person) {
        friendlyLink.addPerson(person);
        return this;
    }

    public FriendlyLink build() {
        return friendlyLink;
    }
}
