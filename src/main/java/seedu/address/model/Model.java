package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.pair.Pair;
import seedu.address.model.person.Elderly;
import seedu.address.model.person.Person;
import seedu.address.model.person.Volunteer;
import seedu.address.model.person.information.Nric;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' FriendlyLink database file path.
     */
    Path getFriendlyLinkFilePath();

    /**
     * Sets the user prefs' FriendlyLink database file path.
     */
    void setFriendlyLinkFilePath(Path friendlyLinkFilePath);

    /**
     * Replaces FriendlyLink database data with the data in {@code friendlyLink}.
     */
    void setFriendlyLink(ReadOnlyFriendlyLink friendlyLink);

    /** Returns the FriendlyLink */
    ReadOnlyFriendlyLink getFriendlyLink();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the FriendlyLink database.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the FriendlyLink database.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the FriendlyLink database.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the FriendlyLink database.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the
     * FriendlyLink database.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Retrieves the elderly with the given Nric.
     * Elderly of that Nric must exist in the FriendlyLink database.
     *
     * @param nric Nric of the elderly.
     * @return Elderly with that Nric.
     */
    Elderly getElderly(Nric nric);

    /**
     * Retrieves the volunteer with the given Nric.
     * Volunteer of that Nric must exist in the FriendlyLink database.
     *
     * @param nric Nric of the volunteer.
     * @return Volunteer with that Nric.
     */
    Volunteer getVolunteer(Nric nric);

    /**
     * Returns true if an elderly with the same identity as {@code elderly} exists in the friendly link database.
     */
    boolean hasElderly(Elderly elderly);

    /**
     * Deletes the given elderly.
     * The elderly must exist in the friendly link database.
     */
    void deleteElderly(Elderly target);

    /**
     * Adds the given elderly.
     * {@code elderly} must not already exist in the friendly link database.
     */
    void addElderly(Elderly elderly);

    /**
     * Replaces the given elderly {@code target} with {@code editedPerson}.
     * {@code target} must exist in the friendly link database.
     * The elderly identity of {@code editedPerson} must not be the same as another existing elderly in the
     * friendly link database.
     */
    void setElderly(Elderly target, Elderly editedPerson);

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the friendly link database.
     */
    boolean hasVolunteer(Volunteer volunteer);

    /**
     * Deletes the given volunteer.
     * The volunteer must exist in the friendly link database.
     */
    void deleteVolunteer(Volunteer target);

    /**
     * Adds the given volunteer.
     * {@code volunteer} must not already exist in the friendly link database.
     */
    void addVolunteer(Volunteer volunteer);

    /**
     * Replaces the given volunteer {@code target} with {@code editedVolunteer}.
     * {@code target} must exist in the friendly link database.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another existing volunteer in the
     * friendly link database.
     */
    void setVolunteer(Volunteer target, Volunteer editedPerson);

    /**
     * Returns the volunteer with the given NRIC, or null otherwise.
     * @param nric The given NRIC.
     * @return A Volunteer with the matching NRIC< or null if no volunteer has that NRIC.
     */
    Volunteer getVolunteerByNric(Nric nric);

    /**
     * Returns true if a pair with the same identity as {@code pair} exists in the address book.
     */
    boolean hasPair(Pair pair);

    /**
     * Deletes the given pair.
     * The pair must exist in the address book.
     */
    void deletePair(Pair target);

    /**
     * Adds the given pair.
     * {@code pair} must not already exist in the address book.
     */
    void addPair(Pair pair);

    /**
     * Replaces the given pair {@code target} with {@code editedPair}.
     * {@code target} must exist in the address book.
     * The pair identity of {@code editedPair} must not be the same as another existing pair in the address book.
     */
    void setPair(Pair target, Pair editedPair);


    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered elderly list */
    ObservableList<Elderly> getFilteredElderlyList();

    /**
     * Updates the filter of the filtered elderly list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredElderlyList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered volunteers list */
    ObservableList<Volunteer> getFilteredVolunteerList();

    /**
     * Updates the filter of the filtered volunteers list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVolunteerList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered pair list */
    ObservableList<Pair> getFilteredPairList();

    /**
     * Updates the filter of the filtered pair list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPairList(Predicate<Pair> predicate);

}
