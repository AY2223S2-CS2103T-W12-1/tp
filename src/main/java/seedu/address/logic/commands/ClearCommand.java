package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.FriendlyLink;
import seedu.address.model.Model;

/**
 * Clears FriendlyLink data.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFriendlyLink(new FriendlyLink());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
