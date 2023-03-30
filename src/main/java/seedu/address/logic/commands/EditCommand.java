package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FIELD_PROVIDED;
import static seedu.address.commons.core.Messages.MESSAGE_NRIC_NOT_EXIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTH_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditDescriptor;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;

/**
 * Edits the details of an existing elderly or volunteer in FriendlyLink.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final HashMap<Prefix, String> COMMAND_PROMPTS = new LinkedHashMap<>();

    static {
        COMMAND_PROMPTS.put(PREFIX_NAME, "[NAME]");
        COMMAND_PROMPTS.put(PREFIX_NRIC, "[NRIC]");
        COMMAND_PROMPTS.put(PREFIX_BIRTH_DATE, "[BIRTH_DATE]");
        COMMAND_PROMPTS.put(PREFIX_REGION, "[REGION]");
        COMMAND_PROMPTS.put(PREFIX_RISK, "[RISK]");
        COMMAND_PROMPTS.put(PREFIX_ADDRESS, "[ADDRESS]");
        COMMAND_PROMPTS.put(PREFIX_PHONE, "[PHONE]");
        COMMAND_PROMPTS.put(PREFIX_EMAIL, "[EMAIL]");
        COMMAND_PROMPTS.put(PREFIX_MEDICAL_TAG, "[MEDICAL_QUALIFICATION]");
        COMMAND_PROMPTS.put(PREFIX_AVAILABILITY, "[AVAILABLE_DATE_START, AVAILABLE_DATE_END]");
        COMMAND_PROMPTS.put(PREFIX_TAG, "[TAG]");
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the NRIC of the person. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC "
            + "" + PREFIX_NAME + "NAME "
            + "" + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BIRTH_DATE + "BIRTHDATE] "
            + "[" + PREFIX_REGION + "REGION] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_AVAILABILITY + "AVAILABLE_DATE_START,AVAILABLE_DATE_END]...\n"
            + "Example: " + COMMAND_WORD + " S4263131J "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com"
            + PREFIX_BIRTH_DATE + "1945-05-03 ";

    private final Nric nric;
    private final EditDescriptor editDescriptor;

    /**
     * Creates an {@code EditCommand} to edit a person.
     *
     * @param nric Of the person in volunteer or elderly list to edit.
     * @param editDescriptor Details to edit the person with.
     */
    public EditCommand(Nric nric, EditDescriptor editDescriptor) {
        requireNonNull(nric);
        requireNonNull(editDescriptor);

        this.nric = nric;
        this.editDescriptor = new EditDescriptor(editDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!editDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NO_FIELD_PROVIDED);
        }
        requireNonNull(model);

        if (model.hasElderly(nric)) {
            return editElderly(model);
        } else if (model.hasVolunteer(nric)) {
            return editVolunteer(model);
        } else {
            throw new CommandException(MESSAGE_NRIC_NOT_EXIST);
        }
    }

    private CommandResult editElderly(Model model) throws CommandException {
        Elderly elderlyToEdit = model.getElderly(nric);
        Elderly editedElderly = EditDescriptor.createEditedElderly(
                elderlyToEdit, editDescriptor);
        Nric editedNric = editedElderly.getNric();
        if (!elderlyToEdit.isSamePerson(editedElderly) && model.hasElderly(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
        }
        if (model.hasVolunteer(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
        }

        model.setElderly(elderlyToEdit, editedElderly);
        model.refreshAllFilteredLists();
        return new CommandResult(String.format(
                EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly));
    }

    private CommandResult editVolunteer(Model model) throws CommandException {
        Volunteer volunteerToEdit = model.getVolunteer(nric);
        Volunteer editedVolunteer = EditDescriptor.createEditedVolunteer(
                volunteerToEdit, editDescriptor);
        Nric editedNric = editedVolunteer.getNric();
        if (!volunteerToEdit.isSamePerson(editedVolunteer) && model.hasVolunteer(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
        }
        if (model.hasElderly(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
        }

        model.setVolunteer(volunteerToEdit, editedVolunteer);
        model.refreshAllFilteredLists();
        return new CommandResult(String.format(
                EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return nric.equals(e.nric)
                && editDescriptor.equals(e.editDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nric, editDescriptor);
    }
}
