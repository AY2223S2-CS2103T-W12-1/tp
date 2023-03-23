package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
import seedu.address.logic.parser.StatsCommandParser;

/**
 * Recommends a command based on the user input.
 * Solution adapted from
 * https://www.algolia.com/doc/guides/solutions/ecommerce/search/autocomplete/predictive-search-suggestions
 */
public class CommandRecommendationEngine {
    public static final Map<String, CommandInfo> commandInfoMap = new LinkedHashMap<>();
    private static final String INVALID_COMMAND_MESSAGE = "No such command exists!"
            + " Please refer to our user guide for the list of valid commands.";
    private static final String INVALID_PREFIX_MESSAGE = "Invalid prefix!"
            + " Please refer to our user guide for the list of valid arguments.";

    static {
        registerCommandInfo(new CommandInfo(
                DeleteVolunteerCommand.COMMAND_WORD,
                DeleteVolunteerCommand.COMMAND_PROMPTS,
                DeleteVolunteerCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                DeleteElderlyCommand.COMMAND_WORD,
                DeleteElderlyCommand.COMMAND_PROMPTS,
                DeleteElderlyCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                AddVolunteerCommand.COMMAND_WORD,
                AddVolunteerCommand.COMMAND_PROMPTS,
                AddVolunteerCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                EditElderlyCommand.COMMAND_WORD,
                EditElderlyCommand.COMMAND_PROMPTS,
                EditElderlyCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                EditVolunteerCommand.COMMAND_WORD,
                EditVolunteerCommand.COMMAND_PROMPTS,
                EditVolunteerCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                DeletePairCommand.COMMAND_WORD,
                DeletePairCommand.COMMAND_PROMPTS,
                DeletePairCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                AddElderlyCommand.COMMAND_WORD,
                AddElderlyCommand.COMMAND_PROMPTS,
                AddElderlyCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                AddPairCommand.COMMAND_WORD,
                AddPairCommand.COMMAND_PROMPTS,
                AddPairCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                ClearCommand.COMMAND_WORD,
                ClearCommand.COMMAND_PROMPTS,
                ClearCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                FindCommand.COMMAND_WORD,
                FindCommand.COMMAND_PROMPTS,
                FindCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                ExitCommand.COMMAND_WORD,
                ExitCommand.COMMAND_PROMPTS,
                ExitCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                ListCommand.COMMAND_WORD,
                ListCommand.COMMAND_PROMPTS,
                ListCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                EditCommand.COMMAND_WORD,
                EditCommand.COMMAND_PROMPTS,
                EditCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                HelpCommand.COMMAND_WORD,
                HelpCommand.COMMAND_PROMPTS,
                HelpCommandParser::validate));
        registerCommandInfo(new CommandInfo(
                StatsCommand.COMMAND_WORD,
                StatsCommand.COMMAND_PROMPTS,
                StatsCommandParser::validate));
    }

    /**
     * Adds a new command info to the engine
     *
     * @param commandInfo Command info to add
     */
    private static void registerCommandInfo(CommandInfo commandInfo) {
        commandInfoMap.put(commandInfo.getCmdWord(), commandInfo);
    }

    /**
     * Recommends a string with respect to the user input
     *
     * @param userInput Input by user
     * @return Recommended command
     * @throws CommandException If the user input is invalid
     */
    public String recommendCommand(String userInput) throws CommandException {
        String[] userInputArray = userInput.trim().split(" ");
        String commandWord = userInputArray[0];
        CommandInfo commandInfo = findMatchingCommandInfo(commandWord);

        if (commandInfo == null || (commandWord.length() == commandInfo.getCmdWord().length()
                && !Objects.equals(commandWord, commandInfo.getCmdWord()))) {
            throw new CommandException(INVALID_COMMAND_MESSAGE);
        }

        String command = commandInfo.getCmdWord();
        String userArgs = null;
        if (userInputArray.length > 1) {
            String[] userArgsArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);
            userArgs = Arrays.stream(userArgsArray).reduce("", ((a, b) -> a + " " + b));
        }

        String recommendedArguments = generateArgumentRecommendation(commandInfo, userArgs);
        if (userInputArray.length > 1) { // command is specified
            return userInput + recommendedArguments;
        } else {
            return command.length() > userInput.length()
                    ? command + recommendedArguments
                    : userInput + recommendedArguments;
        }
    }

    /**
     * Returns the autocompleted command.
     *
     * @param userInput          Input by user
     * @param recommendedCommand Recommendation string given
     * @return String to be autocompleted
     */
    public String autocompleteCommand(String userInput, String recommendedCommand) {
        // remaining values
        String suggestedCommand = recommendedCommand.substring(userInput.trim().length());

        // if command is complete, autocomplete the relevant arguments
        boolean isCompleteCommand = isCommandPrefixComplete(userInput, " ");
        int nextIdx = suggestedCommand.indexOf(isCompleteCommand ? "/" : " ");
        nextIdx = (nextIdx != -1) ? nextIdx + 1 : suggestedCommand.length();
        return userInput.trim() + suggestedCommand.substring(0, nextIdx);
    }

    private static boolean isCommandPrefixComplete(String userInput, String delimiter) {
        return userInput.contains(delimiter);
    }

    private CommandInfo findMatchingCommandInfo(String commandWord) {
        return commandInfoMap.keySet()
                .stream()
                .filter(command -> command.startsWith(commandWord))
                .map(commandInfoMap::get)
                .findFirst()
                .orElse(null);
    }

    private Prefix findMatchingPrefix(HashMap<Prefix, String> cmdPrompt, String prefix) {
        return cmdPrompt.keySet()
                .stream()
                .filter(command -> command.getPrefix().startsWith(prefix))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generates recommendations for parsed arguments. This function is invoked on each keystroke.
     *
     * @param commandInfo   The information for a particular command
     * @param userArgs      The current user input in the command textbox
     * @return The recommended arguments
     * @throws CommandException User typed an invalid prefix.
     */
    private String generateArgumentRecommendation(CommandInfo commandInfo, String userArgs)
            throws CommandException {

        HashMap<Prefix, String> cmdPrompt = commandInfo.getCmdPrompts();
        String command = commandInfo.getCmdWord();

        StringBuilder argumentRecommendation = new StringBuilder();
        if (userArgs == null) {
            getRemainingArguments(cmdPrompt,
                    key -> argumentRecommendation.append(" ").append(key).append(cmdPrompt.get(key)),
                    unused -> true);

            return argumentRecommendation.toString();
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userArgs, cmdPrompt.keySet()
                .toArray(new Prefix[]{}));

        boolean isValidArgs = isValidArgs(command, argumentMultimap);
        if (!isValidArgs) {
            throw new CommandException(INVALID_PREFIX_MESSAGE);
        }

        String[] userInputArray = userArgs.split(" ");

        String currPrefixString = userInputArray[userInputArray.length - 1];
        boolean isCompletePrefix = isCommandPrefixComplete(userInputArray[userInputArray.length - 1], "/");
        Prefix matchingPrefix = findMatchingPrefix(cmdPrompt, currPrefixString.split("/")[0]);

        if (isCompletePrefix && matchingPrefix == null) {
            throw new CommandException(INVALID_PREFIX_MESSAGE);
        }

        if (isCompletePrefix) {
            argumentMultimap.put(matchingPrefix, "");
        }

        if (!isCompletePrefix && matchingPrefix != null) {
            argumentRecommendation.append(matchingPrefix.getPrefix().substring(currPrefixString.length()));
            argumentRecommendation.append(cmdPrompt.get(matchingPrefix));
        }

        // current set of arguments is complete -> return remaining recommendation
        getRemainingArguments(cmdPrompt,
                key -> argumentRecommendation.append(" ").append(key).append(cmdPrompt.get(key)),
                key -> argumentMultimap.getValue(key).isEmpty());

        return argumentRecommendation.toString();
    }

    /**
     * Validates the current set of arguments according to the specified command validator.
     *
     * @param command          The command type.
     * @param argumentMultimap The map of arguments.
     * @return A boolean value indicating if the set of arguments specified is valid.
     */
    public static boolean isValidArgs(String command, ArgumentMultimap argumentMultimap) {
        CommandInfo commandInfo = commandInfoMap.get(command);
        Function<ArgumentMultimap, Boolean> argumentValidator = commandInfo.getCmdValidator();
        return argumentValidator.apply(argumentMultimap);
    }

    private static void getRemainingArguments(HashMap<Prefix, String> cmdPrompt, Consumer<? super Prefix> consumer,
                                              Predicate<Prefix> predicate) {
        cmdPrompt.keySet()
                .stream()
                .filter(predicate)
                .forEach(consumer);
    }
}
