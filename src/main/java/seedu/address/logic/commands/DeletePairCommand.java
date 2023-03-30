package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ELDERLY_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_PAIR_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_VOLUNTEER_NOT_FOUND;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_ELDERLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_VOLUNTEER;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.pair.exceptions.PairNotFoundException;
import seedu.address.model.person.exceptions.ElderlyNotFoundException;
import seedu.address.model.person.exceptions.VolunteerNotFoundException;
import seedu.address.model.person.information.Nric;

/**
 * Deletes the pair identified using NRIC of its elderly and volunteer from the FriendlyLink database.
 */
public class DeletePairCommand extends Command {
    public static final String COMMAND_WORD = "unpair";
    public static final HashMap<Prefix, String> COMMAND_PROMPTS = new HashMap<>();

    static {
        COMMAND_PROMPTS.put(PREFIX_NRIC_ELDERLY, "ELDERLY_NRIC");
        COMMAND_PROMPTS.put(PREFIX_NRIC_VOLUNTEER, "VOLUNTEER_NRIC");
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pair identified by the nric of an elderly and their volunteer.\n"
            + "Parameters: "
            + PREFIX_NRIC_ELDERLY + "ELDERLY ID "
            + PREFIX_NRIC_VOLUNTEER + "VOLUNTEER ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC_ELDERLY + "s02133334I "
            + PREFIX_NRIC_VOLUNTEER + "T2245343a ";

    public static final String MESSAGE_DELETE_PAIR_SUCCESS = "Deleted Pair consisting of"
            + " elderly with NRIC %1$s and volunteer with NRIC %2$s";

    private final Nric elderlyNric;
    private final Nric volunteerNric;

    /**
     * Constructs a DeletePairCommand to delete a pair.
     *
     * @param elderlyNric Nric of the elderly of the pair.
     * @param volunteerNric Nric of the volunteer of the pair.
     */
    public DeletePairCommand(Nric elderlyNric, Nric volunteerNric) {
        requireAllNonNull(elderlyNric, volunteerNric);
        this.elderlyNric = elderlyNric;
        this.volunteerNric = volunteerNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.deletePair(elderlyNric, volunteerNric);
            return new CommandResult(String.format(MESSAGE_DELETE_PAIR_SUCCESS, elderlyNric, volunteerNric));
        } catch (ElderlyNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_ELDERLY_NOT_FOUND, elderlyNric));
        } catch (VolunteerNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, volunteerNric));
        } catch (PairNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_PAIR_NOT_FOUND, elderlyNric, volunteerNric));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeletePairCommand
                && elderlyNric.equals(((DeletePairCommand) other).elderlyNric)
                && volunteerNric.equals(((DeletePairCommand) other).volunteerNric));
    }

    @Override
    public int hashCode() {
        return Objects.hash(elderlyNric, volunteerNric);
    }

}
