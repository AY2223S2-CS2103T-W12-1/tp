package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.information.AvailableDate;

/**
 * An UI component that displays information of a {@code Elderly}.
 */
public class ElderlyCard extends UiPart<Region> {

    private static final String FXML = "ElderlyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Elderly elderly;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label availability;
    @FXML
    private Label age;
    @FXML
    private Label email;
    @FXML
    private HBox tagsBox;
    @FXML
    private FlowPane riskLevel;
    @FXML
    private FlowPane region;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ElderlyCard} with the given {@code Elderly} and index to display.
     *
     * @param elderly Elderly to be displayed.
     * @param displayedIndex Index shown on screen.
     */
    public ElderlyCard(Elderly elderly, int displayedIndex) {
        super(FXML);
        this.elderly = elderly;
        id.setText(displayedIndex + ". ");
        name.setText(elderly.getName().fullName);
        nric.setText(elderly.getNric().value);
        phone.setText(elderly.getPhone().value);
        address.setText(elderly.getAddress().value);
        age.setText(elderly.getAge().value);
        email.setText(elderly.getEmail().value);
        StringBuilder available = new StringBuilder();
        if (elderly.getAvailableDates().size() != 0) {
            for (AvailableDate dates : elderly.getAvailableDates()) {
                boolean b = !(available.toString().isEmpty());
                if (b) {
                    available.append(", ");
                }
                available.append(dates.toString());
            }
        }
        availability.setText(available.toString());
        region.getChildren().add(
                new Label(elderly.getRegion().region.name())
        );
        Label riskLabel = new Label(elderly.getRiskLevel().riskStatus.name());
        riskLabel.getStyleClass().add("risk-" + elderly.getRiskLevel().riskStatus.name());

        riskLevel.getChildren().add(riskLabel);
        elderly.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        if (elderly.getTags().isEmpty()) {
            tagsBox.getChildren().removeAll(tagsBox.getChildren());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ElderlyCard)) {
            return false;
        }

        // state check
        ElderlyCard card = (ElderlyCard) other;
        return id.getText().equals(card.id.getText())
                && elderly.equals(card.elderly);
    }
}
