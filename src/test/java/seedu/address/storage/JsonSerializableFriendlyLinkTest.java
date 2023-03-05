package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonSerializableFriendlyLinkTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFriendlyLinkTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsFriendlyLink.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonFriendlyLink.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonFriendlyLink.json");

    // TODO: change
    /* @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableFriendlyLink dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableFriendlyLink.class).get();
        FriendlyLink friendlyLinkFromFile = dataFromFile.toModelType();
        FriendlyLink typicalFriendlyLink = getTypicalFriendlyLink();
        assertEquals(friendlyLinkFromFile, typicalFriendlyLink);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFriendlyLink dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableFriendlyLink.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableFriendlyLink dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableFriendlyLink.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFriendlyLink.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    } */

}
