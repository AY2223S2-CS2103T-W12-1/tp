package seedu.address.storage.volunteer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.ReadOnlyVolunteer;
import seedu.address.model.person.Volunteer;
import seedu.address.storage.JsonSerializable;

/**
 * An Immutable Volunteer that is serializable to JSON format.
 */
@JsonRootName(value = "volunteers")
public class JsonSerializableVolunteer implements JsonSerializable<FriendlyLink> {
    public static final String MESSAGE_DUPLICATE_VOLUNTEER = "Volunteer list contains duplicate volunteer(s).";

    private final List<JsonAdaptedVolunteer> volunteers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableVolunteer} with the given volunteer.
     */
    @JsonCreator
    public JsonSerializableVolunteer(@JsonProperty("volunteers") List<JsonAdaptedVolunteer> volunteer) {
        serializeEntities(this.volunteers, volunteer);
    }

    /**
     * Converts a given {@code ReadOnlyVolunteer} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableVolunteer}.
     */
    public JsonSerializableVolunteer(ReadOnlyVolunteer source) {
        serializeEntities(volunteers,
                source.getVolunteerList().stream().map(JsonAdaptedVolunteer::new).collect(Collectors.toList()));
    }

    private void serializeEntities(List<JsonAdaptedVolunteer> entities, List<JsonAdaptedVolunteer> source) {
        entities.addAll(source);
    }

    /**
     * Converts this address book into the model's {@code Volunteer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FriendlyLink toModelType() throws IllegalValueException {
        FriendlyLink friendlyLink = new FriendlyLink();
        unserializeEntities(volunteers, friendlyLink);
        return friendlyLink;
    }

    private void unserializeEntities(
            List<JsonAdaptedVolunteer> entities, FriendlyLink friendlyLink) throws IllegalValueException {
        for (JsonAdaptedVolunteer jsonAdaptedVolunteer : entities) {
            Volunteer volunteer = jsonAdaptedVolunteer.toModelType();
            if (friendlyLink.hasVolunteer(volunteer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_VOLUNTEER);
            }
            friendlyLink.addVolunteer(volunteer);
        }
    }

}
