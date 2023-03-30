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
            String expected = "add_volunteer n/NAME ic/NRIC bd/BIRTH_DATE re/[REGION] a/[ADDRESS] p/[PHONE]"
                    + " e/[EMAIL] mt/[MEDICAL_QUALIFICATION] dr/[AVAILABLE_DATE_START, AVAILABLE_DATE_END] t/[TAG]";
            String actual = commandRecommendationEngine.recommendCommand("add_volunteer");
            assertEquals(expected, actual);

            expected = "add_elderly n/Zong Xun ic/NRIC bd/BIRTH_DATE re/[REGION] r/[RISK] a/[ADDRESS]"
                    + " p/[PHONE] e/[EMAIL] dr/[AVAILABLE_DATE_START, AVAILABLE_DATE_END] t/[TAG]";
            actual = commandRecommendationEngine.recommendCommand("add_elderly n/Zong Xun");
            assertEquals(expected, actual);

            expected = "exit";
            actual = commandRecommendationEngine.recommendCommand("ex");
            assertEquals(expected, actual);

            expected = "auto_pair";
            actual = commandRecommendationEngine.recommendCommand("auto_p");
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

            userInput = "auto_p";
            recommended = commandRecommendationEngine.recommendCommand(userInput);
            autocomplete = commandRecommendationEngine.autocompleteCommand(userInput, recommended);
            assertEquals("auto_pair", autocomplete);

            userInput = "add_elderly";
            recommended = commandRecommendationEngine.recommendCommand(userInput);
            autocomplete = commandRecommendationEngine.autocompleteCommand(userInput, recommended);
            assertEquals("add_elderly n/", autocomplete);
        } catch (CommandException e) {
            fail();
        }
    }
}
