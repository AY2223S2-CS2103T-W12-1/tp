package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTH_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.information.Nric;

/**
 * Adds an elderly to the database.
 */
public class AddElderlyCommand extends Command {

    public static final String COMMAND_WORD = "add_elderly";
    public static final HashMap<Prefix, String> COMMAND_PROMPTS = new LinkedHashMap<>();

    static {
        COMMAND_PROMPTS.put(PREFIX_NAME, "NAME");
        COMMAND_PROMPTS.put(PREFIX_NRIC, "NRIC");
        COMMAND_PROMPTS.put(PREFIX_BIRTH_DATE, "BIRTH_DATE");
        COMMAND_PROMPTS.put(PREFIX_REGION, "[REGION]");
        COMMAND_PROMPTS.put(PREFIX_RISK, "[RISK]");
        COMMAND_PROMPTS.put(PREFIX_ADDRESS, "[ADDRESS]");
        COMMAND_PROMPTS.put(PREFIX_PHONE, "[PHONE]");
        COMMAND_PROMPTS.put(PREFIX_EMAIL, "[EMAIL]");
        COMMAND_PROMPTS.put(PREFIX_AVAILABILITY, "[AVAILABLE_DATE_START, AVAILABLE_DATE_END]");
        COMMAND_PROMPTS.put(PREFIX_TAG, "[TAG]");
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an elderly to the database.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_BIRTH_DATE + "BIRTHDATE "
            + "[" + PREFIX_REGION + "REGION] "
            + "[" + PREFIX_RISK + "MEDICAL RISK (LOW, MEDIUM or HIGH)] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_AVAILABILITY + "AVAILABLE_DATE_START,AVAILABLE_DATE_END]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_BIRTH_DATE + "1954-01-01 "
            + PREFIX_REGION + "WEST "
            + PREFIX_RISK + "LOW "
            + PREFIX_TAG + "diabetes "
            + PREFIX_TAG + "lonely "
            + PREFIX_AVAILABILITY + "2023-05-11,2023-05-12";

    public static final String MESSAGE_SUCCESS = "New elderly added: %1$s";

    private final Elderly toAdd;

    /**
     * Creates an AddElderlyCommand to add the specified {@code Elderly}.
     *
     * @param elderly Elderly to add.
     */
    public AddElderlyCommand(Elderly elderly) {
        requireNonNull(elderly);
        toAdd = elderly;
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

        model.addElderly(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddElderlyCommand // instanceof handles nulls
                && toAdd.equals(((AddElderlyCommand) other).toAdd));
    }

    @Override
    public int hashCode() {
        return Objects.hash(toAdd);
    }
}
