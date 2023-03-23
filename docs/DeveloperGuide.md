---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

The General Architecture of FriendlyLink follows that of [AddressBook3](https://se-education.org/addressbook-level3/DeveloperGuide.html).

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete_elderly S1234567A`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />
** TODO: Update image **

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)
** TODO: Update image **

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ElderlyListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>
** TODO: Update image **

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `FriendlyLinkParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddPairCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a pair).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete_elderly S1234567A` Command](images/DeleteSequenceDiagram.png)
** TODO: update image **

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>
** TODO: Update image **

How the parsing works:
* When called upon to parse a user command, the `FriendlyLinkParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddPairCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPairCommand`) which the `FriendlyLinkParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPairCommandParser`, `DeletePairCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />
** TODO: update image **

The `Model` component,

* stores the address book data i.e., all `Elderly`, `Volunteer` and `Pair` objects (which are contained in a `UniqueElderlyList`, `UniqueVolunteerList` and `UniquePairList` objects respectively).
* stores the currently 'selected' `Elderly`, `Volunteer` and `Pair` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Elderly>`,  `ObservableList<Volunteer>` and `ObservableList<Pair>` respectively  that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* depends on some classes in the `Storage` component (because the `Model` component requires `Storage` to save/retrieve objects that belong to the `Model`)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in `FriendlyLink`, which `Person` (`Elderly` or `Volunteer`) references. This allows `FriendlyLink` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>
** TODO: Update Image **

### Storage component

**API** : [`Storage.java`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/developerGuide/StorageClassDiagram.png"/>

The `Storage` component,
* can save `Elderly`, `Volunteer`, `Pair` and `UserPrefs` data in JSON format, and read them back into corresponding objects.
* inherits from `ElderlyStorage`, `VolunteerStorage`, `PairStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* does not depend on any of the other three components (as the `Storage` represents data entities on disk, they should make sense on their own without depending on other components)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Logic

#### Add and Delete Elderly and Volunteer

In FriendlyLink, `Elderly` and `Volunteer` are both implemented as subclasses of the abstract class `Person`.

The `add_elderly` and `add_volunteer` commands accept attributes of `Elderly` and `Volunteer` through prefixes.
Each prefix is followed by the information of one attribute.
Some prefixes, such as `availableDates`, `tags`, are optional.

* This grants greater flexibility of user input, as the user can key in the attributes in any order.
* The unspecified optional fields will return `null` value.
* The `NRIC` attribute for Elderly and Volunteer will be cross-checked to ensure no duplicates.
* When the `add` command format is invalid, or the user tries to add a duplicated person,
the add operation will be aborted.

The Elderly and Volunteers are stored in separate `UniquePersonList` lists.

* Allows for filtering to display a subset of Elderly or Volunteers in UI.
* Allows for easy retrieval of information for pairing.

The `delete_elderly` and `delete_volunteer` commands make use of `NRIC`
attribute of Elderly and Volunteer.
FriendlyLink retrieves the target person uniquely identified by its NRIC,
and removes it from the database.

* Allows more efficient deletion compare to index-based deletion.
* The user don't need to check the index of the target person before deletion.

If the deleted Elderly or Volunteer has existing pairing, the associated
pairs will be automatically removed as well.

<img src="images/developerGuide/PersonAndPair.png" width="500" />

#### Edit by index & NRIC

#### Find by keyword

The ```find``` command allows users to easily filter and locate the relevant elderly and volunteers, together with their related parings.
The results of the ```find``` command are displayed as the filtered version of the elderly, volunteers and pairs lists, 
together with the number of entities listed being shown in the command result box.

Volunteers and elderly who match all of the provided attributes are filtered out and displayed in their respective list.
For each filtered person, Any pairing that they are involve in would be filtered and displayed in the pair list.

Arguments for the ```find``` command involves at least one of the attributes belonging to an elderly or a volunteer.
Any number of attributes can be specified but if multiple of the same attribute is specified then only the last one will be
used in the search.

The Sequence Diagram below illustrates the execution of the ```find``` command.

<img src="images/developerGuide/FindSequenceDiagram.png" width="500" />

The command execution flow is as given below
1. The ```LogicManager``` will begin the execution of the command.
2. Input parsed by ```FriendlyLinkParser``` which creates and return a ```FindCommandParser```.
3. The ```FindCommandParser``` parses the arguments and returns a ```FindCommand``` with the relevant predicates.
4. The ```LogicManager``` executes the ```FindCommand```.
5. The ```FindCommand``` combines the relevant predicates for elderly and volunteers and calls ```updateFilteredElderlyList``` and ```updateFilteredVoolunteerList``` of ```Model```.
6. Based on the filtered elderly and volunteers a predicate to get the related pairs is created and ```updateFilteredPairList``` of ```Model``` is called.
7. ```CommandResult``` with the sizes of the 3 filtered lists is created and returned.

Design decisions:
- Name, address, email and phone attributes allows substring searching.
  - Easier to search with only partial information available.
- When multiple attributes and stated, the result must match all instead of any.
  - The search should narrow the field with each additional new attribute for a more targeted result.
- Related pairings are also shown during the search.
  - Provides a comprehensive search results where all information related to the people found are shown.

#### Pairing and unpairing of elderly and volunteers

Pairs are implemented as a class with 2 referenced attributes, `Elderly` and `Volunteer`.
* This allows the NRIC of a person in the pair to be automatically updated when the person is updated.

The pairs are stored in a list similar to persons.
* Allows for filtering to display a subset of pairs in the UI.
* Allows for identifying a pair by index.

<img src="images/developerGuide/Pair.png" width="350" />

Two pairs are identical if they have the same elderly and volunteer NRIC.

* Just like persons, we do not allow duplicate pairs (due to add or edit pair)
* Elderly and volunteer NRIC is used to identify a pair for deletion.

### Storage

#### Pairs

Pairs saved only contains the NRIC of the elderly and volunteer.

Reasons

* Reduce space needed to store pairs
* Reduce chance of inconsistent data between a person and the corresponding pair,
* Reduce number of files to amend manually when updating person information.

Implications

* A pair is reconstructed on startup by searching the model for the corresponding person.
* Elderly and volunteer files need to be read into the model before pair files.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* single administrator of Non-Profit Organisation (NPO) who needs to track volunteers and their assigned elderly
* works alone in managing volunteer and elderly information.
* tech-savvy
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is comfortable using CLI apps

**Value proposition**: FriendlyLink streamlines volunteer and elderly management for single administrators of small NPOs.
With its easy-to-use text-based interface and contact management features, say goodbye to manual record-keeping and hello
to a more efficient and organised way of managing the volunteers’ and elderly’s contact details.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority   | As a …​                                                                                    | I want to …​                                                                           | So that I can…​                                                                   |
|------------|--------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| `* * *`    | single administrator of small NPOs                                                         | list volunteers                                                                        | see all the volunteers and their information                                      |
| `* * *`    | single administrator of small NPOs                                                         | add volunteer to the list of volunteers                                                | include new volunteers in the application                                         |
| `* * *`    | single administrator of small NPOs                                                         | delete volunteers                                                                      | remove volunteers that have left                                                  |
| `* * *`    | single administrator of small NPOs                                                         | remove all the pairs a volunteer is in                                                 | accurately keep track of which elderly are affected when a volunteer leaves       |
| `* * *`    | single administrator of small NPOs                                                         | read the list of elderly members                                                       | have a clear view of existing elderly members in system                           |
| `* * *`    | single administrator of small NPOs                                                         | add a new elderly member to the system                                                 |                                                                                   |
| `* * *`    | single administrator of small NPOs                                                         | remove an existing elderly member from the system                                      |                                                                                   |
| `* * *`    | single administrator of small NPOs                                                         | remove all the pairings an elderly member has when he / she is removed from the system | maintain accurate and error-free records of pairings                              |
| `* * *`    | single administrator of small NPOs                                                         | find the particular elderly member by search of nric                                   | access the information of each elderly member conveniently                        |
| `* * *`    | single administrator of small NPOs                                                         | edit the particulars of elderly members, such as names or addresses                    | manage elderly information in a more flexible manner                              |
| `* * *`    | single administrator of small NPOs                                                         | add a pair of a volunteer to an elderly                                                | to make sure every elderly member is taken care of                                |
| `* * *`    | single administrator of small NPOs                                                         | filter pairs by involved elderly members                                               | to quikcly find involved volunteers when elderly members are in need of attention |
| `* * *`    | single administrator of small NPOs                                                         | find and list unpaired elderlies                                                       | pair new incoming volunteers easily                                               |
| `* * *`    | single administrator of small NPOs                                                         | delete a pairing of a volunteer form an elderly                                        | to remove pairs that are no longer used                                           |
| `* *`      | single administrator of small NPOs                                                         | update volunteers & volunteer information                                              | keep the volunteer information up-to-date                                         |
| `* * `     | single administrator of small NPOs                                                         | search for particular volunteers by keywords                                           | quickly see the volunteer's details                                               |
| `* *`      | single administrator of small NPOs                                                         | view nursing / medical courses that volunteers have taken in the past                  | pair an elderly witha more suitable volunteer                                     |
| `* *`      | single administrator of small NPOs                                                         | filter and list elderly members by keyword search of name                              | increasing efficiency of finding elderly with certain names                       |
| `* *`      | single administrator of small NPOs                                                         | filter and list elderly members by age group                                           | dedicate more attentions to older members                                         |
| `* *`      | single administrator of small NPOs                                                         | filter and list elderly members by risk level                                          | dedicate more attentions to members with higher risks                             |
| `* *`      | single administrator of small NPOs                                                         | filter and list elderly members by region and community                                | pair volunteers who can better reach out to elderly living close-by               |
| `* *`      | single administrator of small NPOs                                                         | search elderly members by tags                                                         | access the information of elderly members with specific tags                      |
| `* *`      | single administrator of small NPOs                                                         | rank elderly members in the order of their medical risk level                          | better pair volunteers with more medical knowledge with higher-risk elderly       |
| `* *`      | single administrator of small NPOs                                                         | keep track of the region and community of the elderly members                          | reach out to the elderly members conveniently                                     |
| `* *`      | single administrator of small NPOs                                                         | view the last visited time/date of the elderly                                         | know when to plan the next visit                                                  |
| `* *`      | single administrator of small NPOs                                                         | set up reminder system for elderlies                                                   | plan volunteers to assist on those days                                           |
| `* *`      | single administrator of small NPOs                                                         | find a pair by keyword                                                                 | to quickly look up important information when required                            |
| `* *`      | single administrator of small NPOs                                                         | view overlapping pairs between the same volunteers or elderly members                  | to take note of overlapping work.                                                 |
| `* *`      | single administrator of small NPOs                                                         | filter pairs by tags                                                                   | to quickly find certain groups of elderly members for events or routine checkups  |
| `* *`      | single administrator of small NPOs                                                         | see summaries of number of elderly members assigned to each volunteer                  | to evenly distribute workload of volunteers                                       |
| `* *`      | single administrator of small NPOs                                                         | see min, max and average number of elderly buddies per volunteer                       | to evenly distribute workload of volunteers or to request for more resources      |
| `*`        | single administrator of small NPOs                                                         | filter volunteers by tags                                                              | access relevant groups of volunteers quickly                                      |
| `*`        | single administrator of small NPOs                                                         | manage volunteers by region                                                            | arrange the volunteers such that they can conveniently reach out to the elderly   |
| `*`        | single administrator of small NPOs                                                         | record the community information of volunteers, but not their specific address         | ensure that the volunteers' privacy is not compromised                            |
| `*`        | single administrator of small NPOs                                                         | manage the volunteers' available dates and time                                        | efficiently find volunteers available for activities                              |
| `*`        | single administrator of small NPOs                                                         | see how long a volunteer has been with the program                                     | assess their experience                                                           |
| `*`        | single administrator of small NPOs                                                         | track the befriending history of a volunteer                                           | audit past involvements easily                                                    |
| `*`        | single administrator of small NPOs                                                         | rank elderly members in the order of their loneliness situation                        | arrange more frequent volunteer visits for more lonely elderly                    |
| `*`        | single administrator of small NPOs                                                         | track the befriending history of an elderly                                            | audit past involvements easily                                                    |
| `*`        | single administrator of small NPOs                                                         | view past pairings                                                                     | to pair up members familiar with each other                                       |
| `*`        | single administrator of small NPOs                                                         | making recurring pairings                                                              | to handle recurrent changes in pairs.                                             |
| `*`        | single administrator of small NPOs                                                         | adjust frequency and period limit of pairings                                          | to facilitate regular swaps of volunteers and elderly members.                    |
| `*`        | single administrator of small NPOs                                                         | track important dates                                                                  | to facilitate regular volunteer check ins on elderly members.                     |
| `*`        | single administrator of small NPOs                                                         | set up reminders                                                                       | to remind volunteers of their commitments                                         |
| `*`        | single administrator of small NPOs                                                         | set up version control of the application                                              | trace commands that are executed throughout the lifetime of the application       | 
| `*`        | lazy single administrator of small NPOs                                                    | automatically pair up available volunteers to elderlies                                | quickly assign a volunteer to an elderly                                          | 
| `*`        | efficient single administrator of small NPOs                                               | use natural language dates                                                             | quickly assign add a volunteer availability into the database                     | 
| `*`        | forgetful single administrator of small NPOs                                               | autocomplete commands                                                                  | quickly complete the commands                                                     |  
| `*`        | organized single administrator of small NPOs                                               | add tags to volunteer, elderly and pairs                                               | filter the entities by tags                                                       |  
| `*`        | organized single administrator of small NPOs                                               | assign a random integer ID to each entry                                               | retrieve, modify and delete them directly without looking through the list        |  
| `*`        | organized single administrator of small NPOs who have used the application for a long time | retrieve summary statistics of elderlies, volunteers, and pairs in the database        | have a better understanding of the organisation and it's clients                  |  

### Use cases

(For all use cases below, the **System** is the `FriendlyLink (FL)` and the **Actor** is the `Admin`, unless specified otherwise)

**Use case: UC01- Pairs Volunteer and Elderly**

**MSS**

1.  User enters the details of elderly and volunteer to be paired into the application.
2.  FL adds the pair into the database, and feedbacks the successful addition of the pair.
3.  User see the pair details appear in the joint list.

    Use case ends.

**Extensions**

* 1a. FL detects that the elderly is not in the current database.
    * 1a1. FL informs User that the elderly has not been created.

    Use case ends.

* 1b. FL detects that volunteer is not in the current database.
    * 1b1. FL informs User that the volunteer has not been created.

    Use case ends.

* 1c. FL detects missing arguments or an error in the entered data.
    * 1c1. FL feedbacks that entered data is in a wrong format

    Use case ends.

* 1d. FL detects duplicate pair records in the entered data.

    * 1d1. FL feedbacks that it is a duplicate record, and rejects the data entry

    Use case ends.

**Use case: UC02- Add Elderly**

**MSS**

1.  User enters the details of elderly to be added into the application.
2.  FL adds the elderly into the database, and feedbacks the successful addition of the elderly.
3.  User see the elderly details appear in the elderly list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL requests for the correct data.
    * 1a2. User enters new data.
    
    Steps 1a1-1a2 are repeated until the data entered are correct.
    
    Use case ends.

* 1b. FL detects duplicate elderly records in the entered data.

    * 1b1. FL informs it is a duplicate record, requests for the new data.
    * 1b2. User enters new data.

    Steps 1b1-1b2 are repeated until the data entered are correct.

    Use case ends.

**Use case: UC03- Add Volunteer**

**MSS**

1.  User enters the details of volunteer to be added into the application.
2.  FL adds the volunteer into the database, and feedbacks the successful addition of the volunteer.
3.  User see the volunteer details appear in the volunteer list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL requests for the correct data.
    * 1a2. User enters new data.

  Steps 1a1-1a2 are repeated until the data entered are correct.

  Use case ends.

* 1b. FL detects duplicate volunteer records in the entered data.

    * 1b1. FL informs it is a duplicate record, requests for the new data.
    * 1b2. User enters new data.

  Steps 1b1-1b2 are repeated until the data entered are correct.

  Use case ends.

**Use case: UC04- Unpair Volunteer and Elderly**

**MSS**

1.  User enters the pair details (elderly & volunteer) to be deleted into FL.
2.  FL deletes the pair from the database, and feedbacks the successful unpairing.
3.  User see the pair details removed from the joint list.

    Use case ends.

**Extensions**

* 1a. FL detects that the pair is not in the current database.
    * 1a1. FL informs User that the pair has not been created.

  Use case ends.

* 1b. FL detects missing arguments or an error in the entered data.
    * 1b1. FL feedbacks that entered data is in a wrong format

  Use case ends.

**Use case: UC05- Delete Volunteer**

**MSS**

1.  User enters the NRIC of the volunteer to be deleted.
2.  FL deletes the volunteer from the database, and feedbacks the successful deletion of the volunteer.
3.  User see the volunteer details removed from the volunteer list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL requests for the correct data.
    * 1a2. User enters new data.

  Steps 1a1-1a2 are repeated until the data entered are correct.

  Use case ends.

* 1b. FL detects that the volunteer is not inside the records.

    * 1b1. FL informs that the volunteer does not exist, and requests for the new data.
    * 1b2. User enters new data.

  Steps 1b1-1b2 are repeated until the data entered are correct.

  Use case ends.

**Use case: UC06-  Delete Elderly**

**MSS**

1.  User enters the NRIC of the elderly to be deleted.
2.  FL deletes the elderly from the database, and feedbacks the successful deletion of the elderly.
3.  User see the elderly details removed from the elderly list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL requests for the correct data.
    * 1a2. User enters new data.

  Steps 1a1-1a2 are repeated until the data entered are correct.

  Use case ends.

* 1b. FL detects that the elderly is not inside the records.

    * 1b1. FL informs that the elderly does not exist, and requests for the new data.
    * 1b2. User enters new data.

  Steps 1b1-1b2 are repeated until the data entered are correct.

  Use case ends.

**Use case: UC07-  Exit application**

**MSS**

1.  User press the “X” button to exit the application.
2.  FL closes the application.

    Use case ends.


### Non-Functional Requirements

1. FriendlyLink should work on Microsoft Windows, macOS, and Linux that has `Java 11` is installed.
1. FriendlyLink Should be able to hold up to 100 persons (elderly and volunteer) without incurring a delay larger than 3 second for any command.
1. A user with above average typing speed (40wpm) for regular English text (i.e. not code, not system admin commands) should be able to perform at least 75% of use cases faster using commands instead of using the mouse.
1. FriendlyLink will perform minimal checks on correctness of details entered into FriendlyLink (as specified in the user guide advanced section).
1. FriendlyLink will not be responsible for the privacy and security of the data stored in FriendlyLink.
1. FriendlyLink will not recover from corrupted data files.
1. FriendlyLink will only available in English.

### Glossary

| Term         | Definition                                                                               |
|--------------|------------------------------------------------------------------------------------------|
| FriendlyLink | The name of the application                                                              |
| Volunteer    | Volunteers who are willing to pair up with and accompany Elderly members                 |
| Elderly      | Elderlies who need help from their buddies                                               |
| Pair         | The pair of people that consists of an elderly and the volunteer assigned to the elderly |
| NRIC         | A unique identifier given to all Singaporeans.                                           |

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

  1. Download the jar file and copy into an empty folder

  1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

  1. Resize the window to an optimum size. Move the window to a different location. Close the window.

  1. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained.
