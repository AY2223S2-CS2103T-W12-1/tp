package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddElderlyCommand;
import seedu.address.logic.commands.AddPairCommand;
import seedu.address.logic.commands.AddVolunteerCommand;
import seedu.address.logic.commands.AutoPairCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteElderlyCommand;
import seedu.address.logic.commands.DeletePairCommand;
import seedu.address.logic.commands.DeleteVolunteerCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditElderlyCommand;
import seedu.address.logic.commands.EditVolunteerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class FriendlyLinkParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput Full user input string.
     * @return The command based on the user input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddElderlyCommand.COMMAND_WORD:
            return new AddElderlyCommandParser().parse(arguments);

        case AddVolunteerCommand.COMMAND_WORD:
            return new AddVolunteerCommandParser().parse(arguments);

        case AddPairCommand.COMMAND_WORD:
            return new AddPairCommandParser().parse(arguments);

        case AutoPairCommand.COMMAND_WORD:
            return new AutoPairCommand();

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditElderlyCommand.COMMAND_WORD:
            return new EditElderlyCommandParser().parse(arguments);

        case EditVolunteerCommand.COMMAND_WORD:
            return new EditVolunteerCommandParser().parse(arguments);

        case DeleteElderlyCommand.COMMAND_WORD:
            return new DeleteElderlyCommandParser().parse(arguments);

        case DeleteVolunteerCommand.COMMAND_WORD:
            return new DeleteVolunteerCommandParser().parse(arguments);

        case DeletePairCommand.COMMAND_WORD:
            return new DeletePairCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
