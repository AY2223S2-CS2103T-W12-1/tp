package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import seedu.address.model.pair.Pair;

/**
 * An UI component that displays information of a {@code Pair}.
 */
public class PairCard extends UiPart<Region> {

    private static final String FXML = "PairListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Pair pair;

    @FXML
    private HBox cardPane;
    @FXML
    private Label elderlyName;
    @FXML
    private Label elderlyNric;
    @FXML
    private Label volunteerName;
    @FXML
    private Label volunteerNric;
    @FXML
    private Label id;

    // Create a popover and set its content
    private final Popup popover = new Popup();

    /**
     * Creates a {@code PairCode} with the given {@code Pair} and index to display.
     *
     * @param pair           Pair to be displayed.
     * @param displayedIndex Index shown on screen.
     */
    public PairCard(Pair pair, int displayedIndex) {
        super(FXML);
        this.pair = pair;
        elderlyName.setText(pair.getElderly().getName().fullName);
        elderlyNric.setText(pair.getElderly().getNric().toString());
        volunteerName.setText(pair.getVolunteer().getName().fullName);
        volunteerNric.setText(pair.getVolunteer().getNric().toString());

        // Create a VBox to hold the content of the popover
        VBox vbox = new VBox();
        Label elderlyLabel = new Label("Elderly");
        Label volunteerLabel = new Label("Volunteer");

        ElderlyCard elderlyCard = new ElderlyCard(pair.getElderly(), 0);
        VolunteerCard volunteerCard = new VolunteerCard(pair.getVolunteer(), 0);
        vbox.getChildren().add(elderlyLabel);
        vbox.getChildren().add(elderlyCard.getRoot());
        vbox.getChildren().add(volunteerLabel);
        vbox.getChildren().add(volunteerCard.getRoot());

        elderlyCard.getRoot().setMaxWidth(400);
        volunteerCard.getRoot().setMaxWidth(400);
        vbox.getStyleClass().add("popup");
        elderlyLabel.setStyle(
                "-fx-font-size: 20; -fx-text-fill: black; -fx-font-weight: 700"
        );

        volunteerLabel.setStyle(
                "-fx-font-size: 20; -fx-text-fill: black; -fx-font-weight: 700"
        );

        popover.getContent().add(vbox);
        // Set the popover to hide when the user clicks outside of it
        popover.setAutoHide(true);

        cardPane.setOnMouseEntered(event -> {
            // Show the popover anchored to the position of the HBox
            Bounds bounds = cardPane.localToScreen(cardPane.getBoundsInLocal());

            double popupWidth = popover.getWidth();
            double popupHeight = popover.getHeight();
            double x = event.getSceneX();
            double y = event.getSceneY();

            if (x + popupWidth >= bounds.getMaxX()) {
                x = bounds.getMaxX() - popupWidth - 10;
            }

            if (y + popupHeight >= bounds.getMaxY()) {
                y = bounds.getMaxY() - popupHeight - 10;
            }
            popover.show(cardPane, x, y - 50);
        });

        cardPane.setOnMouseExited(event -> popover.hide());

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PairCard)) {
            return false;
        }

        // state check
        PairCard card = (PairCard) other;
        return id.getText().equals(card.id.getText())
                && pair.equals(card.pair);
    }
}
