package seedu.address.storage.elderly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.ReadOnlyElderly;
import seedu.address.model.person.Elderly;
import seedu.address.storage.SerializableEntity;

/**
 * An Immutable Elderly that is serializable to JSON format.
 */
@JsonRootName(value = "elderly")
public class JsonSerializableElderly implements SerializableEntity<FriendlyLink> {
    public static final String MESSAGE_DUPLICATE_ELDERLY = "Elderly list contains duplicate elderly.";

    private final List<JsonAdaptedElderly> elderly = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableElderly} with the given elderly.
     */
    @JsonCreator
    public JsonSerializableElderly(@JsonProperty("elderly") List<JsonAdaptedElderly> elderly) {
        serializeEntities(this.elderly, elderly);
    }

    /**
     * Converts a given {@code ReadOnlyElderly} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableElderly}.
     */
    public JsonSerializableElderly(ReadOnlyElderly source) {
        serializeEntities(elderly,
                source.getElderlyList().stream().map(JsonAdaptedElderly::new).collect(Collectors.toList()));
    }

    private void serializeEntities(List<JsonAdaptedElderly> entities, List<JsonAdaptedElderly> source) {
        entities.addAll(source);
    }

    /**
     * Converts this elderly list into the model's {@code Elderly} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FriendlyLink toModelType() throws IllegalValueException {
        FriendlyLink friendlyLink = new FriendlyLink();
        unserializeEntities(elderly, friendlyLink);
        return friendlyLink;
    }

    private void unserializeEntities(
            List<JsonAdaptedElderly> entity, FriendlyLink friendlyLink) throws IllegalValueException {
        for (JsonAdaptedElderly jsonAdaptedElderly : entity) {
            // TODO: Check if there is a need to cast
            Elderly elderly = jsonAdaptedElderly.toModelType();
            if (friendlyLink.hasElderly(elderly)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ELDERLY);
            }
            friendlyLink.addElderly(elderly);
        }
    }

}
