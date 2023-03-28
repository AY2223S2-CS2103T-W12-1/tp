package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddElderlyCommandParser;
import seedu.address.logic.parser.AddPairCommandParser;
import seedu.address.logic.parser.AddVolunteerCommandParser;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ClearCommandParser;
import seedu.address.logic.parser.DeleteElderlyCommandParser;
import seedu.address.logic.parser.DeletePairCommandParser;
import seedu.address.logic.parser.DeleteVolunteerCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.EditElderlyCommandParser;
import seedu.address.logic.parser.EditVolunteerCommandParser;
import seedu.address.logic.parser.ExitCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.HelpCommandParser;
import seedu.address.logic.parser.ListCommandParser;
import seedu.address.logic.parser.Prefix;

public class CommandRecommendationEngineTest {
    private final CommandRecommendationEngine commandRecommendationEngine = new CommandRecommendationEngine();

    @Test
    public void recommendCommand_validCommand_success() {
        try {
            String expected = "add_volunteer n/<name> nr/<nric> bd/<birth_date> re/<region> "
                    + "a/<address> p/<phone> e/<email> "
                    + "mt/<medical_qualification> dr/<start_date,end_date> t/<tag>";
            String actual = commandRecommendationEngine.recommendCommand("add_volunteer");
            assertEquals(expected, actual);

            expected = "add_elderly n/Zong Xun nr/<nric> bd/<birth_date> re/<region> r/<risk> "
                    + "a/<address> p/<phone> e/<email> dr/<start_date,end_date> t/<tag>";
            actual = commandRecommendationEngine.recommendCommand("add_elderly n/Zong Xun");
            assertEquals(expected, actual);

            expected = "exit";
            actual = commandRecommendationEngine.recommendCommand("ex");
            assertEquals(expected, actual);

            expected = "clear";
            actual = commandRecommendationEngine.recommendCommand("clea");
            assertEquals(expected, actual);
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void recommendCommand_invalidCommand_fail() {
        assertThrows(CommandException.class, () ->
                commandRecommendationEngine.recommendCommand("god_tier_anime"));

        assertThrows(CommandException.class, () ->
                commandRecommendationEngine.recommendCommand("god_tier_anime n/boku no pico"));
    }


    @Test
    public void parseArguments_invalidArgs_fail() {
        String userArgs = "notValidInput n/Zong Xun";
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(AddVolunteerCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(AddVolunteerCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(AddElderlyCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(AddElderlyCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(AddPairCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(AddPairCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(ClearCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(ClearCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(ExitCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(ExitCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(ListCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(ListCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(HelpCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(HelpCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(DeletePairCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(DeletePairCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(FindCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertTrue(FindCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(EditCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertTrue(EditCommandParser.validate(argumentMultimap));

        userArgs = "dadiaodhawoid hoidahwodiwaod";
        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(DeleteElderlyCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(DeleteElderlyCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(DeleteVolunteerCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(DeleteVolunteerCommandParser.validate(argumentMultimap));

        userArgs = "n/Zon hello/<nric>";
        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(AddVolunteerCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertFalse(AddVolunteerCommandParser.validate(argumentMultimap));

        userArgs = "1";
        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(EditElderlyCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertTrue(EditElderlyCommandParser.validate(argumentMultimap));

        argumentMultimap = ArgumentTokenizer.tokenize(userArgs, CommandRecommendationEngine
                .COMMAND_INFO_MAP.get(EditVolunteerCommand.COMMAND_WORD).getCmdPrompts().keySet()
                .toArray(new Prefix[]{}));
        assertTrue(EditVolunteerCommandParser.validate(argumentMultimap));
    }

    @Test
    public void parseUserInputForAutocompletion_validInput_success() {
        try {
            String userInput = "add_v";
            String recommended = commandRecommendationEngine.recommendCommand(userInput);
            String autocomplete = commandRecommendationEngine.autocompleteCommand(userInput, recommended);
            assertEquals("add_volunteer", autocomplete);

            userInput = "clea";
            recommended = commandRecommendationEngine.recommendCommand(userInput);
            autocomplete = commandRecommendationEngine.autocompleteCommand(userInput, recommended);
            assertEquals("clear", autocomplete);

            userInput = "add_elderly";
            recommended = commandRecommendationEngine.recommendCommand(userInput);
            autocomplete = commandRecommendationEngine.autocompleteCommand(userInput, recommended);
            assertEquals("add_elderly n/", autocomplete);
        } catch (CommandException e) {
            fail();
        }
    }
}
