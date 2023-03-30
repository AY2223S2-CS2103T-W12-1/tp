package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FIELD_PROVIDED;
import static seedu.address.commons.core.Messages.MESSAGE_WARNING_AVAILABLE_DATES;
import static seedu.address.commons.core.Messages.MESSAGE_WARNING_REGION;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTH_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.EditDescriptor;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.information.Nric;

/**
 * Edits the details of an existing elderly in FriendlyLink.
 */
public class EditElderlyCommand extends Command {

    public static final String COMMAND_WORD = "edit_elderly";
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
        COMMAND_PROMPTS.put(PREFIX_AVAILABILITY, "[AVAILABLE_DATE_START, AVAILABLE_DATE_END]");
        COMMAND_PROMPTS.put(PREFIX_TAG, "[TAG]");
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the elderly identified "
            + "by the index number used in the displayed elderly list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_BIRTH_DATE + "BIRTHDATE] "
            + "[" + PREFIX_REGION + "REGION] "
            + "[" + PREFIX_RISK + "RISK] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_AVAILABILITY + "AVAILABLE_DATE_START,AVAILABLE_DATE_END]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com"
            + PREFIX_BIRTH_DATE + "1945-05-01 ";

    public static final String MESSAGE_EDIT_ELDERLY_SUCCESS = "Edited Elderly: %1$s";

    private final Index index;
    private final EditDescriptor editDescriptor;

    /**
     * Creates an {@code EditElderlyCommand} to edit an elderly.
     *
     * @param index Index of the elderly in the filtered elderly list to edit.
     * @param editDescriptor Details to edit the elderly with.
     */
    public EditElderlyCommand(Index index, EditDescriptor editDescriptor) {
        requireAllNonNull(index, editDescriptor);
        this.index = index;
        this.editDescriptor = new EditDescriptor(editDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!editDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NO_FIELD_PROVIDED);
        }

        requireNonNull(model);
        List<Elderly> lastShownList = model.getFilteredElderlyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
        }
        assert index.getZeroBased() >= 0 : "index should not be negative";

        Elderly elderlyToEdit = lastShownList.get(index.getZeroBased());
        Elderly editedElderly = EditDescriptor.createEditedElderly(elderlyToEdit, editDescriptor);

        Nric editedNric = editedElderly.getNric();
        if (!elderlyToEdit.isSamePerson(editedElderly) && model.hasElderly(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
        }
        if (model.hasVolunteer(editedNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
        }

        model.setElderly(elderlyToEdit, editedElderly);
        @SuppressWarnings("unchecked")
        Predicate<Elderly> predicate = (Predicate<Elderly>) PREDICATE_SHOW_ALL;
        model.updateFilteredElderlyList(predicate);

        String finalMessage = String.format(MESSAGE_EDIT_ELDERLY_SUCCESS, editedElderly);
        if (!model.checkIsSameRegion(editedElderly.getNric(), null)) {
            finalMessage += MESSAGE_WARNING_REGION;
        }
        if (!model.checkHasSuitableAvailableDates(editedElderly.getNric(), null)) {
            finalMessage += MESSAGE_WARNING_AVAILABLE_DATES;
        }

        model.refreshAllFilteredLists();
        return new CommandResult(finalMessage);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditElderlyCommand)) {
            return false;
        }

        // state check
        EditElderlyCommand e = (EditElderlyCommand) other;
        return index.equals(e.index)
                && editDescriptor.equals(e.editDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, editDescriptor);
    }
}
