package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTH_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;

/**
 * Adds a volunteer to the database.
 */
public class AddVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "add_volunteer";
    public static final HashMap<Prefix, String> COMMAND_PROMPTS = new LinkedHashMap<>();

    static {
        COMMAND_PROMPTS.put(PREFIX_NAME, "NAME");
        COMMAND_PROMPTS.put(PREFIX_NRIC, "NRIC");
        COMMAND_PROMPTS.put(PREFIX_BIRTH_DATE, "BIRTH_DATE");
        COMMAND_PROMPTS.put(PREFIX_REGION, "[REGION]");
        COMMAND_PROMPTS.put(PREFIX_ADDRESS, "[ADDRESS]");
        COMMAND_PROMPTS.put(PREFIX_PHONE, "[PHONE]");
        COMMAND_PROMPTS.put(PREFIX_EMAIL, "[EMAIL]");
        COMMAND_PROMPTS.put(PREFIX_MEDICAL_TAG, "[MEDICAL_QUALIFICATION]");
        COMMAND_PROMPTS.put(PREFIX_AVAILABILITY, "[AVAILABLE_DATE_START, AVAILABLE_DATE_END]");
        COMMAND_PROMPTS.put(PREFIX_TAG, "[TAG]");
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer to the database. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_BIRTH_DATE + "BIRTHDATE "
            + "[" + PREFIX_REGION + "REGION] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_MEDICAL_TAG + "MEDICAL_TAG] "
            + "[" + PREFIX_TAG + "TAG] "
            + "[" + PREFIX_AVAILABILITY + "AVAILABLE_DATE_START,AVAILABLE_DATE_END]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_BIRTH_DATE + "2002-12-01 "
            + PREFIX_REGION + "NORTH "
            + PREFIX_MEDICAL_TAG + "CPR BASIC "
            + PREFIX_TAG + "new "
            + PREFIX_TAG + "undergradStudent "
            + PREFIX_AVAILABILITY + "2023-05-11,2023-05-12";

    public static final String MESSAGE_SUCCESS = "New volunteer added: %1$s";

    private final Volunteer toAdd;

    /**
     * Creates an AddVolunteerCommand to add the specified {@code volunteer}
     *
     * @param volunteer Volunteer to add.
     */
    public AddVolunteerCommand(Volunteer volunteer) {
        requireNonNull(volunteer);
        toAdd = volunteer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Nric newNric = toAdd.getNric();
        if (model.hasElderly(newNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
        }
        if (model.hasVolunteer(newNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
        }

        model.addVolunteer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddVolunteerCommand // instanceof handles nulls
                        && toAdd.equals(((AddVolunteerCommand) other).toAdd));
    }

    @Override
    public int hashCode() {
        return Objects.hash(toAdd);
    }
}
