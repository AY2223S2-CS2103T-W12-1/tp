package seedu.address.logic.commands.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.information.Address;
import seedu.address.model.person.information.Age;
import seedu.address.model.person.information.AvailableDate;
import seedu.address.model.person.information.Email;
import seedu.address.model.person.information.Name;
import seedu.address.model.person.information.Nric;
import seedu.address.model.person.information.Phone;
import seedu.address.model.person.information.Region;
import seedu.address.model.tag.Tag;
/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class EditPersonDescriptor {
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Nric nric;
    private Age age;
    private Region region;
    private Set<Tag> tags;
    private Set<AvailableDate> availableDates;

    /**
     * Default empty constructor.
     */
    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     *
     * @param toCopy {@code EditPersonDescriptor} for copying.
     */
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setNric(toCopy.nric);
        setAge(toCopy.age);
        setRegion(toCopy.region);
        setTags(toCopy.tags);
        setAvailableDates(toCopy.availableDates);
    }

    /**
     * Returns true if at least one field is edited.
     *
     * @return True if at least one field is edited and false otherwise.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, phone,
                email, address, nric, age, region, tags, availableDates);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setNric(Nric nric) {
        this.nric = nric;
    }

    public Optional<Nric> getNric() {
        return Optional.ofNullable(nric);
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Optional<Age> getAge() {
        return Optional.ofNullable(age);
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Optional<Region> getRegion() {
        return Optional.ofNullable(region);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     *
     * @param tags Tags to set.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     *
     * @return {@code Optional} of the set of tags.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Sets {@code availableDates} to this object's {@code availableDates}.
     *
     * @param availableDates Dates to set.
     */
    public void setAvailableDates(Set<AvailableDate> availableDates) {
        this.availableDates = (availableDates != null) ? new HashSet<>(availableDates) : null;
    }

    /**
     * Returns an unmodifiable date set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code availableDates} is null.
     *
     * @return {@code Optional} of the set of dates.
     */
    public Optional<Set<AvailableDate>> getAvailableDates() {
        return (availableDates != null)
                ? Optional.of(Collections.unmodifiableSet(availableDates))
                : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonDescriptor)) {
            return false;
        }

        // state check
        EditPersonDescriptor e = (EditPersonDescriptor) other;

        return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getNric().equals(e.getNric())
                && getAge().equals(e.getAge())
                && getRegion().equals(e.getRegion())
                && getTags().equals(e.getTags())
                && getAvailableDates().equals(e.getAvailableDates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, address, nric,
                age, region, tags);
    }
}
