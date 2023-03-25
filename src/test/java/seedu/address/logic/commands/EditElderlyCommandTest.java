package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RISK_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SINGLE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showElderlyAtIndex;
import static seedu.address.testutil.TestUtil.getTypicalModelManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.util.EditDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.testutil.EditDescriptorBuilder;
import seedu.address.testutil.ElderlyBuilder;
import seedu.address.testutil.ModelManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditElderlyCommand.
 */
public class EditElderlyCommandTest {

    private final Model model = getTypicalModelManager();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredElderlyList_success() {
        Elderly editedElderly = new ElderlyBuilder().build();
        EditDescriptor descriptor = new EditDescriptorBuilder(editedElderly).build();
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS,
                editedElderly);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setElderly(model.getFilteredElderlyList().get(0), editedElderly);

        assertCommandSuccess(editElderlyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredElderlyList_success() {
        Index indexLastElderly = Index.fromOneBased(model.getFilteredElderlyList().size());
        Elderly lastElderly = model.getFilteredElderlyList().get(indexLastElderly.getZeroBased());

        ElderlyBuilder elderlyInList = new ElderlyBuilder(lastElderly);
        Elderly editedElderly = elderlyInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withRiskLevel(VALID_RISK_LEVEL_BOB).withTags(VALID_TAG_SINGLE).build();

        EditDescriptor descriptor = new EditDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withRiskLevel(VALID_RISK_LEVEL_BOB)
                .withTags(VALID_TAG_SINGLE).build();
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(indexLastElderly, descriptor);

        String expectedMessage = String.format(EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS,
                editedElderly);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setElderly(lastElderly, editedElderly);

        assertCommandSuccess(editElderlyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredElderlyList_failure() {
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(INDEX_FIRST_PERSON,
                new EditDescriptor());

        String expectedMessage = MESSAGE_NOT_EDITED;

        assertCommandFailure(editElderlyCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredElderlyList_success() {
        showElderlyAtIndex(model, INDEX_FIRST_PERSON);

        Elderly elderlyInFilteredList = model.getFilteredElderlyList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Elderly editedElderly = new ElderlyBuilder(elderlyInFilteredList).withName(VALID_NAME_BOB).build();
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(INDEX_FIRST_PERSON,
                new EditDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS,
                editedElderly);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setElderly(model.getFilteredElderlyList().get(0), editedElderly);

        assertCommandSuccess(editElderlyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateElderlyUnfilteredElderlyList_failure() {
        Elderly firstElderly = model.getFilteredElderlyList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDescriptor descriptor = new EditDescriptorBuilder(firstElderly).build();
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editElderlyCommand, model, MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
    }

    @Test
    public void execute_duplicateElderlyFilteredElderlyList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST_PERSON);

        // edit elderly in filtered list into a duplicate in FriendlyLink
        Elderly elderlyInList = model.getFriendlyLink().getElderlyList()
                .get(INDEX_SECOND_PERSON.getZeroBased());
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(INDEX_FIRST_PERSON,
                new EditDescriptorBuilder(elderlyInList).build());

        assertCommandFailure(editElderlyCommand, model, MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
    }

    @Test
    public void execute_invalidElderlyIndexUnfilteredElderlyList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredElderlyList().size() + 1);
        EditDescriptor descriptor = new EditDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(outOfBoundIndex,
                descriptor);

        assertCommandFailure(editElderlyCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address PERSON
     */
    @Test
    public void execute_invalidElderlyIndexFilteredList_failure() {
        showElderlyAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFriendlyLink().getElderlyList().size());

        EditElderlyCommand editElderlyCommand = new EditElderlyCommand(outOfBoundIndex,
                new EditDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editElderlyCommand, model, MESSAGE_INVALID_ELDERLY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditElderlyCommand standardCommand = new EditElderlyCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_AMY);
        EditElderlyCommand commandWithSameValues = new EditElderlyCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditElderlyCommand(INDEX_SECOND_PERSON, DESC_AMY));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditElderlyCommand(INDEX_FIRST_PERSON, DESC_BOB));
    }

}
