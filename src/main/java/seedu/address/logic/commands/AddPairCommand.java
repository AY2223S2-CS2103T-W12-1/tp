package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PAIR;
import static seedu.address.commons.core.Messages.MESSAGE_ELDERLY_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_VOLUNTEER_NOT_FOUND;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_ELDERLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_VOLUNTEER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.person.exceptions.ElderlyNotFoundException;
import seedu.address.model.person.exceptions.VolunteerNotFoundException;
import seedu.address.model.person.information.Nric;

/**
 * Adds a person to FriendlyLink.
 */
public class AddPairCommand extends Command {

    public static final String COMMAND_WORD = "pair";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pairs an elderly and volunteer in FriendlyLink. "
            + "Parameters: "
            + PREFIX_NRIC_ELDERLY + "ELDERLY ID "
            + PREFIX_NRIC_VOLUNTEER + "VOLUNTEER ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC_ELDERLY + "s02133334I "
            + PREFIX_NRIC_VOLUNTEER + "T2245343a ";

    public static final String MESSAGE_ADD_PAIR_SUCCESS = "New pair consisting of elderly with NRIC %1$s"
            + " and volunteer with NRIC %2$s added";

    private final Nric elderlyNric;
    private final Nric volunteerNric;

    /**
     * Creates an AddPairCommand to add the specified {@code Pair}
     */
    public AddPairCommand(Nric elderlyNric, Nric volunteerNric) {
        requireAllNonNull(elderlyNric, volunteerNric);
        this.elderlyNric = elderlyNric;
        this.volunteerNric = volunteerNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.addPair(elderlyNric, volunteerNric);
            return new CommandResult(String.format(MESSAGE_ADD_PAIR_SUCCESS, elderlyNric, volunteerNric));
        } catch (ElderlyNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_ELDERLY_NOT_FOUND, elderlyNric));
        } catch (VolunteerNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, volunteerNric));
        } catch (DuplicatePairException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PAIR, elderlyNric, volunteerNric));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPairCommand // instanceof handles nulls
                && elderlyNric.equals(((AddPairCommand) other).elderlyNric)
                && volunteerNric.equals(((AddPairCommand) other).volunteerNric));
    }
}
