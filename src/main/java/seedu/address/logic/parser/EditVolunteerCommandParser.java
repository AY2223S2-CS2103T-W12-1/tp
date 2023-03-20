package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_VOLUNTEER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVolunteerCommand;
import seedu.address.logic.commands.util.EditVolunteerDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditVolunteerCommand object.
 */
public class EditVolunteerCommandParser implements Parser<EditVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditVolunteerCommand
     * and returns an EditVolunteerCommand object for execution.
     *
     * @param args Arguments.
     * @return {@code EditVolunteerCommand} for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public EditVolunteerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_NRIC_VOLUNTEER, PREFIX_AGE, PREFIX_REGION, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditVolunteerCommand.MESSAGE_USAGE), pe);
        }

        EditVolunteerDescriptor editVolunteerDescriptor = new EditVolunteerDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editVolunteerDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editVolunteerDescriptor.setPhone(
                    ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editVolunteerDescriptor.setEmail(
                    ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editVolunteerDescriptor.setAddress(
                    ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_NRIC_VOLUNTEER).isPresent()) {
            editVolunteerDescriptor.setNric(
                    ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC_VOLUNTEER).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editVolunteerDescriptor.setAge(
                    ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(PREFIX_REGION).isPresent()) {
            editVolunteerDescriptor.setRegion(
                    ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get()));
        }
        EditCommandParser.parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editVolunteerDescriptor::setTags);

        if (!editVolunteerDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditVolunteerCommand(index, editVolunteerDescriptor);
    }

}

