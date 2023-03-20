package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_VOLUNTEER;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.commons.core.Messages.MESSAGE_NRIC_NOT_EXIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditElderlyDescriptor;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.logic.commands.util.EditVolunteerDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;

/**
 * Edits the details of an existing elderly or volunteer in FriendlyLink.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the NRIC of the person. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_NRIC + "NEW_NRIC] "
            + "[" + PREFIX_REGION + "NEW_REGION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " S4263131J "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com"
            + PREFIX_AGE + "73 ";

    private final Nric nric;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an {@code EditCommand} to edit a person.
     *
     * @param nric Of the person in volunteer or elderly list to edit.
     * @param editPersonDescriptor Details to edit the person with.
     */
    public EditCommand(Nric nric, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(nric);
        requireNonNull(editPersonDescriptor);

        this.nric = nric;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
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
        Elderly editedElderly = EditElderlyDescriptor.createEditedElderly(
                elderlyToEdit, editPersonDescriptor);
        if (!elderlyToEdit.isSamePerson(editedElderly) && model.hasElderly(editedElderly)) {
            throw new CommandException(MESSAGE_DUPLICATE_ELDERLY);
        }

        model.setElderly(elderlyToEdit, editedElderly);

        @SuppressWarnings("unchecked")
        Predicate<Elderly> predicate = (Predicate<Elderly>) PREDICATE_SHOW_ALL;
        model.updateFilteredElderlyList(predicate);
        return new CommandResult(String.format(
                EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly));
    }

    private CommandResult editVolunteer(Model model) throws CommandException {
        Volunteer volunteerToEdit = model.getVolunteer(nric);
        Volunteer editedVolunteer = EditVolunteerDescriptor.createEditedVolunteer(
                volunteerToEdit, editPersonDescriptor);
        if (!volunteerToEdit.isSamePerson(editedVolunteer) && model.hasVolunteer(editedVolunteer)) {
            throw new CommandException(MESSAGE_DUPLICATE_VOLUNTEER);
        }

        model.setVolunteer(volunteerToEdit, editedVolunteer);
        @SuppressWarnings("unchecked")
        Predicate<Volunteer> predicate = (Predicate<Volunteer>) PREDICATE_SHOW_ALL;
        model.updateFilteredVolunteerList(predicate);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nric, editPersonDescriptor);
    }
}
