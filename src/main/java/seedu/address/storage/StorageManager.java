package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.ReadOnlyElderly;
import seedu.address.model.ReadOnlyFriendlyLink;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyVolunteer;
import seedu.address.model.UserPrefs;
import seedu.address.storage.elderly.ElderlyStorage;
import seedu.address.storage.volunteer.VolunteerStorage;

/**
 * Manages storage of FriendlyLink data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final FriendlyLinkStorage friendlyLinkStorage;
    private final ElderlyStorage elderlyStorage;
    private final VolunteerStorage volunteerStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code FriendlyLinkStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(FriendlyLinkStorage friendlyLinkStorage, ElderlyStorage elderlyStorage,
                          VolunteerStorage volunteerStorage, UserPrefsStorage userPrefsStorage) {

        this.friendlyLinkStorage = friendlyLinkStorage;
        this.elderlyStorage = elderlyStorage;
        this.volunteerStorage = volunteerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Elderly methods ==============================

    @Override
    public Path getElderlyFilePath() {
        return elderlyStorage.getElderlyFilePath();
    }

    /**
     * Returns FriendlyLink data as a {@link ReadOnlyElderly}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<ReadOnlyElderly> readElderly(FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        return readElderly(elderlyStorage.getElderlyFilePath(), friendlyLink);
    }

    @Override
    public Optional<ReadOnlyElderly> readElderly(Path filePath, FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return elderlyStorage.readElderly(filePath, friendlyLink);
    }

    /**
     * Saves the given {@link ReadOnlyElderly} to the storage.
     *
     * @param elderly cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveElderly(ReadOnlyElderly elderly) throws IOException {
        saveElderly(elderly, elderlyStorage.getElderlyFilePath());
    }

    @Override
    public void saveElderly(ReadOnlyElderly elderly, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        elderlyStorage.saveElderly(elderly, filePath);
    }

    // ================ Volunteer methods ==============================

    @Override
    public Path getVolunteerFilePath() {
        return volunteerStorage.getVolunteerFilePath();
    }

    /**
     * Returns FriendlyLink data as a {@link ReadOnlyVolunteer}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<ReadOnlyVolunteer> readVolunteer(FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        return readVolunteer(volunteerStorage.getVolunteerFilePath(), friendlyLink);

    }

    @Override
    public Optional<ReadOnlyVolunteer> readVolunteer(Path filePath, FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return volunteerStorage.readVolunteer(filePath, friendlyLink);
    }

    @Override
    public void saveVolunteer(ReadOnlyVolunteer volunteer) throws IOException {
        saveVolunteer(volunteer, volunteerStorage.getVolunteerFilePath());
    }

    @Override
    public void saveVolunteer(ReadOnlyVolunteer volunteer, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        volunteerStorage.saveVolunteer(volunteer, filePath);
    }

    // ================ FriendlyLink methods ==============================

    @Override
    public Path getFriendlyLinkFilePath() {
        return friendlyLinkStorage.getFriendlyLinkFilePath();
    }

    @Override
    public Optional<ReadOnlyFriendlyLink> readFriendlyLink(FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        return readFriendlyLink(friendlyLinkStorage.getFriendlyLinkFilePath(), friendlyLink);
    }

    @Override
    public Optional<ReadOnlyFriendlyLink> readFriendlyLink(Path filePath, FriendlyLink friendlyLink)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return friendlyLinkStorage.readFriendlyLink(filePath, friendlyLink);
    }

    @Override
    public void saveFriendlyLink(ReadOnlyFriendlyLink friendlyLink) throws IOException {
        saveFriendlyLink(friendlyLink, friendlyLinkStorage.getFriendlyLinkFilePath());
    }

    @Override
    public void saveFriendlyLink(ReadOnlyFriendlyLink friendlyLink, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        friendlyLinkStorage.saveFriendlyLink(friendlyLink, filePath);
    }

}
