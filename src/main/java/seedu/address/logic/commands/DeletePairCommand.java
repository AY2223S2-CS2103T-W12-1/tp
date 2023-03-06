package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_ELDERLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_VOLUNTEER;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pair.Pair;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.exceptions.ElderlyNotFoundException;
import seedu.address.model.person.exceptions.VolunteerNotFoundException;
import seedu.address.model.person.information.Nric;

/**
 * Deletes the pair identified using NRIC of its elderly and volunteer from the FriendlyLink database.
 */
public class DeletePairCommand extends Command {
    public static final String COMMAND_WORD = "delete_pair";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the pair identified by the nric of an elderly and their volunteer.\n"
            + "Parameters: "
            + PREFIX_NRIC_ELDERLY + "ELDERLY ID "
            + PREFIX_NRIC_VOLUNTEER + "VOLUNTEER ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC_ELDERLY + "s02133334I "
            + PREFIX_NRIC_VOLUNTEER + "T2245343a ";

    public static final String MESSAGE_DELETE_PAIR_SUCCESS = "Deleted Pair: %1$s";

    public static final String MESSAGE_ELDERLY_NOT_FOUND =
            "The elderly with NRIC %1$s does not exist in FriendlyLink";

    public static final String MESSAGE_VOLUNTEER_NOT_FOUND =
            "The volunteer with NRIC %1$s does not exist in FriendlyLink";

    public static final String MESSAGE_PAIR_NOT_FOUND =
            "The pair consisting of elderly with NRIC %1$s and volunteer with NRIC %2$s"
                    + " does not exist in FriendlyLink";

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
        Elderly elderly;
        Volunteer volunteer;

        try {
            elderly = model.getElderly(elderlyNric);
            volunteer = model.getVolunteer(volunteerNric);
        } catch (ElderlyNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_ELDERLY_NOT_FOUND, elderlyNric));
        } catch (VolunteerNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, volunteerNric));
        }

        Pair toDelete = new Pair(elderly, volunteer);
        if (!model.hasPair(toDelete)) {
            throw new CommandException(String.format(MESSAGE_PAIR_NOT_FOUND, elderlyNric, volunteerNric));
        }

        model.deletePair(toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PAIR_SUCCESS, toDelete));
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
