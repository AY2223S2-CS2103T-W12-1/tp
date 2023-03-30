package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_ELDERLY;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FIELD_PROVIDED;
import static seedu.address.commons.core.Messages.MESSAGE_NRIC_NOT_EXIST;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TestUtil.getTypicalModelManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.util.EditDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;
import seedu.address.testutil.EditDescriptorBuilder;
import seedu.address.testutil.ElderlyBuilder;
import seedu.address.testutil.ModelManagerBuilder;
import seedu.address.testutil.TypicalElderly;
import seedu.address.testutil.TypicalVolunteers;
import seedu.address.testutil.VolunteerBuilder;

public class EditCommandTest {
    private final Model model = getTypicalModelManager();

    @Test
    public void execute_validElderlyNric_success() {
        Elderly selectedElderly = TypicalElderly.getTypicalElderly().get(0);
        Nric selectedNric = selectedElderly.getNric();
        Elderly editedElderly = new ElderlyBuilder().build();

        EditDescriptor descriptor = new EditDescriptorBuilder(editedElderly).build();
        Elderly resultantElderly = EditDescriptor.createEditedElderly(selectedElderly, descriptor);
        EditCommand editCommand = new EditCommand(selectedNric, descriptor);

        String expectedMessage = String.format(EditElderlyCommand.MESSAGE_EDIT_ELDERLY_SUCCESS,
                resultantElderly);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setElderly(model.getElderly(selectedNric), resultantElderly);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validVolunteerNric_success() {
        Volunteer selectedVolunteer = TypicalVolunteers.getTypicalVolunteers().get(0);
        Nric selectedNric = selectedVolunteer.getNric();
        Volunteer editedVolunteer = new VolunteerBuilder().build();

        EditDescriptor descriptor = new EditDescriptorBuilder(editedVolunteer).build();
        Volunteer resultantVolunteer = EditDescriptor.createEditedVolunteer(selectedVolunteer, descriptor);
        EditCommand editCommand = new EditCommand(selectedNric, descriptor);

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                resultantVolunteer);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();

        expectedModel.setVolunteer(model.getVolunteer(selectedNric), resultantVolunteer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editElderlyNricInAnotherElderly_throwsCommandException() {
        Elderly selectedElderly = TypicalElderly.getTypicalElderly().get(1);
        Nric selectedNric = TypicalElderly.getTypicalElderly().get(0).getNric();
        EditDescriptor descriptor = new EditDescriptorBuilder(selectedElderly).build();
        EditCommand editCommand = new EditCommand(selectedNric, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
    }

    @Test
    public void execute_editElderlyNricInAnotherVolunteer_throwsCommandException() {
        Elderly selectedElderly = TypicalElderly.getTypicalElderly().get(0);
        Nric volunteerNric = TypicalVolunteers.getTypicalVolunteers().get(0).getNric();
        EditDescriptor descriptor = new EditDescriptorBuilder(selectedElderly)
                .withNric(volunteerNric.value).build();
        EditCommand editCommand = new EditCommand(selectedElderly.getNric(), descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
    }

    @Test
    public void execute_editVolunteerNricInAnotherVolunteer_throwsCommandException() {
        Volunteer selectedVolunteer = TypicalVolunteers.getTypicalVolunteers().get(1);
        Nric selectedNric = TypicalVolunteers.getTypicalVolunteers().get(0).getNric();
        EditDescriptor descriptor = new EditDescriptorBuilder(selectedVolunteer).build();
        EditCommand editCommand = new EditCommand(selectedNric, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_PERSON_IN_VOLUNTEERS);
    }

    @Test
    public void execute_editVolunteerNricInAnotherElderly_throwsCommandException() {
        Volunteer selectedVolunteer = TypicalVolunteers.getTypicalVolunteers().get(0);
        Nric elderlyNric = TypicalElderly.getTypicalElderly().get(0).getNric();
        EditDescriptor descriptor = new EditDescriptorBuilder(selectedVolunteer)
                .withNric(elderlyNric.value).build();
        EditCommand editCommand = new EditCommand(selectedVolunteer.getNric(), descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_PERSON_IN_ELDERLY);
    }

    @Test
    public void execute_emptyDescriptor_throwsCommandException() {
        Nric validNric = TypicalVolunteers.getTypicalVolunteers().get(0).getNric();
        EditCommand editCommand = new EditCommand(validNric, new EditDescriptor());

        assertCommandFailure(editCommand, model, MESSAGE_NO_FIELD_PROVIDED);
    }
    @Test
    public void execute_invalidNric_throwsCommandException() {
        Nric invalidNric = new Nric("T9999999I");
        EditCommand editCommand = new EditCommand(
                invalidNric,
                new EditDescriptorBuilder(new VolunteerBuilder().build()).build()
        );

        assertCommandFailure(editCommand, model, MESSAGE_NRIC_NOT_EXIST);
    }
    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(new Nric(VALID_NRIC_AMY), DESC_AMY);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(new Nric(VALID_NRIC_AMY), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new AutoPairCommand());

        // different nric -> returns false
        assertNotEquals(standardCommand, new EditCommand(new Nric(VALID_NRIC_BOB), CommandTestUtil.DESC_AMY));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditCommand(new Nric(VALID_NRIC_AMY), DESC_BOB));
    }
}
