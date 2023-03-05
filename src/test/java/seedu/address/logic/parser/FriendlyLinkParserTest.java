package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC_VOLUNTEER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddElderlyCommand;
import seedu.address.logic.commands.AddPairCommand;
import seedu.address.logic.commands.AddVolunteerCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteElderlyCommand;
import seedu.address.logic.commands.DeleteVolunteerCommand;
import seedu.address.logic.commands.EditElderlyCommand;
import seedu.address.logic.commands.EditElderlyCommand.EditElderlyDescriptor;
import seedu.address.logic.commands.EditVolunteerCommand;
import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pair.Pair;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;
import seedu.address.testutil.EditElderlyDescriptorBuilder;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.ElderlyBuilder;
import seedu.address.testutil.ElderlyUtil;
import seedu.address.testutil.PairBuilder;
import seedu.address.testutil.PairUtil;
import seedu.address.testutil.VolunteerBuilder;
import seedu.address.testutil.VolunteerUtil;

public class FriendlyLinkParserTest {

    private final FriendlyLinkParser parser = new FriendlyLinkParser();

    @Test
    public void parseCommand_addElderly() throws Exception {
        Elderly elderly = new ElderlyBuilder().build();
        AddElderlyCommand command = (AddElderlyCommand) parser
                .parseCommand(ElderlyUtil.getAddElderlyCommand(elderly));
        assertEquals(new AddElderlyCommand(elderly), command);
    }

    @Test
    public void parseCommand_addVolunteer() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        AddVolunteerCommand command = (AddVolunteerCommand) parser
                .parseCommand(VolunteerUtil.getAddVolunteerCommand(volunteer));
        assertEquals(new AddVolunteerCommand(volunteer), command);
    }

    @Test
    public void parseCommand_editElderly() throws Exception {
        Elderly elderly = new ElderlyBuilder().build();
        EditElderlyDescriptor descriptor = new EditElderlyDescriptorBuilder(elderly).build();
        EditElderlyCommand command = (EditElderlyCommand) parser.parseCommand(ElderlyUtil
                .getEditElderlyCommand(INDEX_FIRST_PERSON.getOneBased(), descriptor));
        assertEquals(new EditElderlyCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editVolunteer() throws Exception {
        Volunteer volunteer = new VolunteerBuilder().build();
        EditVolunteerDescriptor descriptor = new EditVolunteerDescriptorBuilder(volunteer).build();
        EditVolunteerCommand command = (EditVolunteerCommand) parser.parseCommand(VolunteerUtil
                .getEditVolunteerCommand(INDEX_FIRST_PERSON.getOneBased(), descriptor));
        assertEquals(new EditVolunteerCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_deleteElderly() throws Exception {
        String nricStr = "T1234567I";
        DeleteElderlyCommand command = (DeleteElderlyCommand) parser.parseCommand(
                DeleteElderlyCommand.COMMAND_WORD + " " + nricStr);
        assertEquals(new DeleteElderlyCommand(nricStr), command);
    }

    @Test
    public void parseCommand_deleteVolunteer() throws Exception {
        String nricStr = "T1234567I";
        DeleteVolunteerCommand command = (DeleteVolunteerCommand) parser.parseCommand(
                DeleteVolunteerCommand.COMMAND_WORD + " " + PREFIX_NRIC_VOLUNTEER + nricStr);
        assertEquals(new DeleteVolunteerCommand(new Nric(nricStr)), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addPair() throws Exception {
        Pair pair = new PairBuilder().build();
        AddPairCommand command = (AddPairCommand) parser.parseCommand(PairUtil.getAddPairCommand(pair));
        assertEquals(new AddPairCommand(pair.getElderly().getNric(), pair.getVolunteer().getNric()), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
