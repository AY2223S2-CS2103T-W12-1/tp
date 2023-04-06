---
layout: page
title: Developer Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

[//]: # (list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well)

* AddressBook Level-3 [documentation](https://se-education.org/addressbook-level3/)
* AddressBook Level-3 [code](https://github.com/nus-cs2103-AY2223S2/tp)
* Agolia [Documentation](https://www.algolia.com/doc/guides/solutions/ecommerce/search/autocomplete/predictive-search-suggestions)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip** The `.puml` files used to create diagrams in this document can be found in [here](https://github.com/AY2223S2-CS2103T-W12-1/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete_elderly S1234567G`.

<img src="images/ArchitectureSequenceDiagram.png" width="600" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<img src="images/developerGuide/UiClassDiagram.png" width="800" />

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ElderlyListPanel`
, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Elderly`, `Volunteer` and `Pair` objects residing in
  the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2223S2-CS2103T-W12-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `FriendlyLinkParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddPairCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a pair).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete_elderly S1234567I")` API call.

<img src="images/DeleteSequenceDiagram.png" width="1200" />

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteElderlyCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `FriendlyLinkParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddPairCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPairCommand`) which the `FriendlyLinkParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddPairCommandParser`, `DeletePairCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/developerGuide/ModelDiagram.png" width="600" />

The `Model` component,

* stores the address book data i.e., all `Elderly`, `Volunteer` and `Pair` objects (which are contained in a `UniqueElderlyList`, `UniqueVolunteerList` and `UniquePairList` objects respectively).
* stores the currently 'selected' `Elderly`, `Volunteer` and `Pair` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Elderly>`,  `ObservableList<Volunteer>` and `ObservableList<Pair>` respectively  that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* depends on some classes in the `Storage` component (because the `Model` component requires `Storage` to save/retrieve objects that belong to the `Model`)


This is the detailed implementation of `Person` in `Model`. 
* Both `Elderly` and `Volunteer` inherit from the abstract class `Person`.
* A `Pair` makes reference to one `Elderly` and one `Volunteer` each.

<br>

<img src="images/developerGuide/PersonNew.png" width="250" />


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

### Command Recommendation and Autocompletion

Autocompletion and command recommendation are crucial features that help to improve the user experience when interacting 
with our application. By predicting the set of words that the user intends to type based on a preset of words such as 
in-built commands and field prefixes, the `CommandRecommendationEngine` helps to save user's time and effort while 
ensuring accuracy.

#### Implementation

To provide autocompletion, the `CommandRecommendationEngine` uses an event handler attached to the `commandTextField` 
which listens for the `TAB` event. When triggered, it autocompletes the user's input by replacing it with the recommended values.

On the other hand, to provide command recommendations, the engine uses the longest prefix matching algorithm. This 
algorithm first **identifies** the related command based on the user's input and then **verifies** the fields 
associated with it. For instance, when the user types "add_e" in the command prompt, the engine will first recommend
"add_elderly", followed by the related fields. If there are ambiguities in the recommendations, the decision is to return 
the commands based on using lexicographical ordering. 

The CommandRecommendationEngine also uses the concept of **Full Attribute** commands and **Complete** commands. 
A _Full Attribute_ command means that all fields of the command, including optional and compulsory, have been specified. 
On the other hand, a _Complete_ command indicates that a command has been fully typed, but arguments may or may not have been typed. 
This distinction helps the engine to provide more accurate recommendations based on the user's input.

The following activity diagram describes the activity flow:

<img src="images/developerGuide/CommandRecommendationActivityDiagram.png"/>

To ensure the correctness of the attributes specified, the CommandRecommendationEngine also uses an event listener 
to provide immediate feedback to the user. This feedback mechanism helps to minimize errors and ensure that users input 
the correct command attributes.

Finally, the CommandRecommendationEngine provides a registerCommandInfo method that allows developers to register new commands
and turn on command recommendation for them. This flexibility ensures that the engine can adapt to changes in the application 
and provide accurate recommendations even as the application evolves.

#### Design considerations

Aspect: How recommendation executes:

- Alternative 1 (current choice): Using a `LinkedHashMap` for word search
  - Pros: Quick and Easy to implement.
  - Cons: May have performance issues in terms of memory usage.
- Alternative 2: Using a `Trie` for word search
  - Pros: Will use less memory (Only required to store unique prefixes).
  - Cons: Relatively harder to implement, and might introduce bugs.

### Add and Delete Elderly and Volunteer

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

### Edit by index & NRIC

In FriendlyLink, there are 2 methods to  choose which elderly/volunteer to edit:
- via **index** using the `edit_elderly` and `edit_volunteer` commands
- via **NRIC** using the `edit` command

Similar to adding elderly/volunteers, editing is done by specifying the desired field(s) to edit using their prefixes, 
and then providing the new value for the field(s). For consistency, the prefixes between the adding and 
editing commands are kept the same.

For the editing of fields that accept multiple arguments (i.e. `AvailableDate`, `Tag` and `MedicalQualificationTag`),
the specified behaviour is to leaves them unchanged if no new values are provided, else overwrite the existing values 
with the newly provided values.

Retrieving of the desired `Elderly`/`Volunteer` to edit is done differently 
depending on whether `edit_elderly`/`edit_volunteer`
or `edit` is called:
- `edit_elderly`/`edit_volunteer`: The `Elderly`/`Volunteer` is retrieved directly from
the `filteredElderly`/`filteredVolunteer` of `Model`
- `edit`: We first check if an `Elderly` with the specified NRIC exists in `Model`. 
If so, we retrieve it; Otherwise, we perform a similar check for `Volunteer`, and retrieve it if 
such a `Volunteer` exists.

These edits are performed primarily through the `EditDescriptor` class. This class
contains `Optional` types of the **union** of the fields between `Elderly` and `Volunteer`. In order to "transform"
a given volunteer/elderly with the edited fields, 2 important static methods are provided:
- `createEditedElderly(Elderly, EditDescriptor)`: returns a new `Elderly` representing the given `Elderly` modified 
with the values specified in the `EditDescriptor`
- `createEditedVolunteer(Volunteer, EditDescriptor)`: returns a `Volunteer` representing the given `Volunteer` modified 
with the values specified in the `EditDescriptor`

The `Elderly`/`Volunteer` and then edited in the model using the `setElderly`/`setVolunteer` methods
of `Model` respectively.

As an example, the following sequence diagram shows the sequence for the command `edit S1234567I n/Shaun ag/21`, where
the NRIC `S1234567I` belongs to an existing **volunteer**:
<img src="images/developerGuide/EditSequenceDiagram.png" width="1200"/>

:information_source: **Note:** The lifeline for `EditCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

Examples:
* `edit_pair 1 eic/T0245267I` Edits the 1st pair so that the volunteer is paired to the elderly with NRIC `T0245267I` instead.

### Find by keyword

The ```find``` command allows users to easily filter and locate the relevant elderly and volunteers, together with their related parings.
The results of the ```find``` command are displayed as the filtered version of the elderly, volunteers and pairs lists, 
together with the number of entities listed being shown in the command result box.

Volunteers and elderly who match all the provided attributes that they have are filtered out and displayed in their respective list.
For each filtered person, Any pairing that they are involve in would be filtered and displayed in the pair list.

Arguments for the ```find``` command involves at least one of the attributes belonging to an elderly or a volunteer.
Any number of attributes can be specified but if multiple of the same attribute is specified then only the last one will be
used in the search.

The Sequence Diagram below illustrates the execution of the ```find``` command.

<img src="images/developerGuide/FindSequenceDiagram.png" width="1200" />

The command execution flow is as given below
1. The ```LogicManager``` will begin the execution of the command.
2. Input parsed by ```FriendlyLinkParser``` which creates and return a ```FindCommandParser```.
3. The ```FindCommandParser``` parses the arguments and returns a ```FindCommand``` with the relevant predicates.
4. The ```LogicManager``` executes the ```FindCommand```.
5. The ```FindCommand``` combines the relevant predicates for elderly and volunteers and calls ```updateFilteredElderlyList``` and ```updateFilteredVoolunteerList``` of ```Model```.
6. Based on the filtered elderly and volunteers a predicate to get the related pairs is created and ```updateFilteredPairList``` of ```Model``` is called.
7. ```CommandResult``` with the sizes of the 3 filtered lists is created and returned.

Design decisions:
- Name, address, email, phone, tags and medical qualification attributes allow substring searching.
  - Easier to search with only partial information available.
- When multiple attributes and stated, the result must match all instead of any.
  - The search should narrow the field with each additional new attribute for a more targeted result.
- Related pairings are also shown during the search.
  - Provides a comprehensive search results where all information related to the people found are shown.
- People with Available dates that contain the specified dates or have no available dates will be found when searching with the specified dates.
  - They are found because they are available on the specified dates.

### Pairing and unpairing of elderly and volunteers

Pairs are implemented as a class with 2 referenced attributes, `Elderly` and `Volunteer`.
* This allows the NRIC of a person in the pair to be automatically updated when the person is updated.

The pairs are stored in a list similar to persons.

* Allows for filtering to display a subset of pairs in the UI.
* Allows for identifying a pair by index.

Two pairs are identical if they have the same elderly and volunteer NRIC.

* Just like persons, we do not allow duplicate pairs (due to add or edit pair)
* Elderly and volunteer NRIC is used to identify a pair for deletion.

### Summary Statistics

The `stats` command displays summary statistics about FriendlyLink, such as the total number of elderly, volunteers and unpaired persons.

It is implemented using the `Summary` and `AggregateFunction` class.

The `AggregateFunction`
* describes a particular statistic of FriendlyLink with a number.
* is an abstract class that requires concrete classes to override the `getDescription()` and `getResult()` method.

The `Summary` object
* formats the results to be displayed to the user.
* takes in 0 or more `AggregateFunction`s to show their description and results.

<img src="images/developerGuide/StatsCommandClassDiagram.png" width="500" />
 
### Storage
This section specifies how entities such as `Elderly`, `Volunteer` and `Pair` are stored on disk.

Elderly, volunteers and pairs are stored in separate files to reduces the impact of a corrupted file, since it will only affect either elderly or volunteers.

#### Persons

Persons saved contains all their attributes such as name, NRIC, in JSON format.
* Single value attributes are stored as key value pairs, such as name and NRIC.
* Multiple value attributes such as tag and available date sets are stored as JSON lists.

#### Pairs

Pairs saved only contains the NRIC of the elderly and volunteer in JSON format.

**Reasons**
* Reduce space needed to store pairs
* Reduce chance of inconsistent data between a person and the corresponding pair,
* Reduce number of files to amend manually when updating person information.

**Implications**
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

## **Appendix: Planned Enhancements**

### Editing a pair by index: `edit_pair`

Proposed usage: Edits an existing pair based on their index in the pairs list.

Currently, editing a pair by index has not been implemented. This feature can increase the efficiency of using FriendlyLink.
This feature was originally deemed low priority, since a pair only has 2 fields: elderly and volunteer, and thus users can reasonably still achieve this by deleting a pair and adding a new pair. Through user feedback, we acknowledge that this is still a useful feature to have to improve efficiency and user experience.

Proposed format: `edit_pair INDEX [eic/ELDERLY_NRIC] [vic/VOLUNTEER_NRIC]`

Proposed behaviour:
* Edits the pair at the specified `INDEX` in the displayed pair list.
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.

### Deleting a pair by index: `delete_pair`

Proposed usage: Deletes an existing pair based on their index in the pairs list.

Currently, pairs are deleted by specifying NRIC of both elderly and volunteer. This feature can increase the efficiency of using FriendlyLink, if we allow users to simply specify the index of the pair.
This feature is originally implemented as such to prevent accidental deletion of pairs, as it is easy to enter the wrong index but hard to accidentally enter a pair of incorrect NRICs and delete the wrong pair. Through user feedback, we acknowledge that we should implement it to delete by index and support this with an `undo` feature to minimise impact of accidental deletions.

Proposed format: `edit_pair INDEX [eic/ELDERLY_NRIC] [vic/VOLUNTEER_NRIC]`

Proposed behaviour:
* Edits the pair at the specified `INDEX` in the displayed pair list.
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* single administrator of a Voluntary Welfare Organisation (VWO) who needs to track volunteers and their assigned elderly.
* works alone in managing volunteer and elderly information.
* adequately tech-savvy.
* has a need to manage a significant number of volunteers and elderly.
* prefers desktop applications over other types.
* can type fast.
* prefers typing to mouse interactions.
* comfortable using CLI applications.

**Value proposition**: FriendlyLink streamlines volunteer and elderly management for single administrators of VWOs.
With its easy-to-use text-based interface and contact management features, say goodbye to manual record-keeping and hello
to a more efficient and organised way of managing volunteers’ and elderly’s contact details.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                                               | I want to …​                                                                                      | So that I can…​                                                                   |
|----------|---------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| `* * *`  | single administrator of a VWO                                                         | view the list of volunteers                                                                       | see all the volunteers and their information readily                              |
| `* * *`  | single administrator of a VWO                                                         | add a new volunteer to the system                                                                 | track and manage new volunteers                                                   |
| `* * *`  | single administrator of a VWO                                                         | remove an existing volunteer from the system                                                      | stop tracking volunteers that have left                                           |
| `* * *`  | single administrator of a VWO                                                         | edit the particulars of a volunteer, such as names or addresses                                   | keep their information up to date and rectify any error                           |
| `* * *`  | single administrator of a VWO                                                         | view the list of elderly                                                                          | see all the elderly and their information readily                                 |
| `* * *`  | single administrator of a VWO                                                         | add a new elderly member to the system                                                            | track and manage new elderly                                                      |
| `* * *`  | single administrator of a VWO                                                         | remove an existing elderly member from the system                                                 | stop tracking elderly that have left                                              |
| `* * *`  | single administrator of a VWO                                                         | edit the particulars of an elderly, such as names or addresses                                    | keep their information up to date and rectify any error                           |
| `* * *`  | single administrator of a VWO                                                         | find a particular volunteer or elderly by NRIC                                                    | uniquely identify and access their information                                    |
| `* * *`  | single administrator of a VWO                                                         | remove pairings that elderly and volunteers are involved in when they are removed from the system | maintain accurate and error-free records of pairings                              |
| `* * *`  | single administrator of a VWO                                                         | add a pair of a volunteer and an elderly                                                          | track and manage pairings of volunteer and elderly                                |
| `* * *`  | single administrator of a VWO                                                         | find and list unpaired volunteers and elderly                                                     | prioritise pairing volunteers and elderly who are unpaired                        |
| `* * *`  | single administrator of a VWO                                                         | remove pairings                                                                                   | to remove pairs that are no longer valid                                          |
| `* * `   | single administrator of a VWO                                                         | search for particular volunteers by keywords                                                      | quickly see the volunteer's details                                               |
| `* *`    | single administrator of a VWO                                                         | view nursing / medical courses that volunteers have taken in the past                             | pair an elderly with a more suitable volunteer                                    |
| `* *`    | single administrator of a VWO                                                         | filter and list elderly members by keyword search of name                                         | increasing efficiency of finding elderly with certain names                       |
| `* *`    | single administrator of a VWO                                                         | filter pairs by involved elderly members                                                          | to quickly find involved volunteers when elderly members are in need of attention |
| `* *`    | single administrator of a VWO                                                         | filter and list elderly members by age group                                                      | dedicate more attentions to older members                                         |
| `* *`    | single administrator of a VWO                                                         | filter and list elderly members by risk level                                                     | dedicate more attentions to members with higher risks                             |
| `* *`    | single administrator of a VWO                                                         | filter and list elderly members by region and community                                           | pair volunteers who can better reach out to elderly living close-by               |
| `* *`    | single administrator of a VWO                                                         | search elderly members by tags                                                                    | access the information of elderly members with specific tags                      |
| `* *`    | single administrator of a VWO                                                         | autocomplete commands                                                                             | know what are some possible commands and fields that I need to type               |
| `* *`    | single administrator of a VWO                                                         | rank elderly members in the order of their medical risk level                                     | better pair volunteers with more medical knowledge with higher-risk elderly       |
| `* *`    | single administrator of a VWO                                                         | keep track of the region and community of the elderly members                                     | reach out to the elderly members conveniently                                     |
| `* *`    | single administrator of a VWO                                                         | view the last visited time/date of the elderly                                                    | know when to plan the next visit                                                  |
| `* *`    | single administrator of a VWO                                                         | set up reminder system for elderly                                                                | plan volunteers to assist on those days                                           |
| `* *`    | single administrator of a VWO                                                         | find a pair by keyword                                                                            | to quickly look up important information when required                            |
| `* *`    | single administrator of a VWO                                                         | view overlapping pairs between the same volunteers or elderly members                             | to take note of overlapping work.                                                 |
| `* *`    | single administrator of a VWO                                                         | filter pairs by tags                                                                              | to quickly find certain groups of elderly members for events or routine checkups  |
| `* *`    | single administrator of a VWO                                                         | see summaries of number of elderly members assigned to each volunteer                             | to evenly distribute workload of volunteers                                       |
| `* *`    | single administrator of a VWO                                                         | see min, max and average number of elderly buddies per volunteer                                  | to evenly distribute workload of volunteers or to request for more resources      |
| `*`      | single administrator of a VWO                                                         | filter volunteers by tags                                                                         | access relevant groups of volunteers quickly                                      |
| `*`      | single administrator of a VWO                                                         | manage volunteers by region                                                                       | arrange the volunteers such that they can conveniently reach out to the elderly   |
| `*`      | single administrator of a VWO                                                         | record the community information of volunteers, but not their specific address                    | ensure that the volunteers' privacy is not compromised                            |
| `*`      | single administrator of a VWO                                                         | manage the volunteers' available dates and time                                                   | efficiently find volunteers available for activities                              |
| `*`      | single administrator of a VWO                                                         | see how long a volunteer has been with the program                                                | assess their experience                                                           |
| `*`      | single administrator of a VWO                                                         | track the befriending history of a volunteer                                                      | audit past involvements easily                                                    |
| `*`      | single administrator of a VWO                                                         | rank elderly members in the order of their loneliness situation                                   | arrange more frequent volunteer visits for more lonely elderly                    |
| `*`      | single administrator of a VWO                                                         | track the befriending history of an elderly                                                       | audit past involvements easily                                                    |
| `*`      | single administrator of a VWO                                                         | view past pairings                                                                                | to pair up members familiar with each other                                       |
| `*`      | single administrator of a VWO                                                         | making recurring pairings                                                                         | to handle recurrent changes in pairs.                                             |
| `*`      | single administrator of a VWO                                                         | adjust frequency and period limit of pairings                                                     | to facilitate regular swaps of volunteers and elderly members.                    |
| `*`      | single administrator of a VWO                                                         | track important dates                                                                             | to facilitate regular volunteer check ins on elderly members.                     |
| `*`      | single administrator of a VWO                                                         | set up reminders                                                                                  | to remind volunteers of their commitments                                         |
| `*`      | single administrator of a VWO                                                         | set up version control of the application                                                         | trace commands that are executed throughout the lifetime of the application       | 
| `*`      | lazy single administrator of a VWO                                                    | automatically pair up available volunteers to elderly                                             | quickly assign a volunteer to an elderly                                          | 
| `*`      | efficient single administrator of a VWO                                               | use natural language dates                                                                        | quickly assign add a volunteer availability into the database                     |  
| `*`      | organized single administrator of a VWO                                               | add tags to volunteer, elderly and pairs                                                          | filter the entities by tags                                                       |  
| `*`      | organized single administrator of a VWO                                               | assign a random integer ID to each entry                                                          | retrieve, modify and delete them directly without looking through the list        |  
| `*`      | organized single administrator of a VWO who have used the application for a long time | retrieve summary statistics of elderly, volunteers, and pairs in the database                     | have a better understanding of the organisation and it's clients                  |  

### Use cases

(For all use cases below, the **System** is the `FriendlyLink (FL)` and the **Actor** is the `Admin`, unless specified otherwise)

**Use case: UC01- Pair Volunteer and Elderly**

**MSS**

1.  User enters the details of elderly and volunteer to be paired into the application.
2.  FL adds the pair into the database, and feedbacks the successful addition of the pair.
3.  User see the pair details appear in the pair list.

    Use case ends.

**Extensions**

* 1a. FL detects that the elderly is not in the current database.
    * 1a1. FL informs User that the elderly has not been created.

    Use case ends.

* 1b. FL detects that volunteer is not in the current database.
    * 1b1. FL informs User that the volunteer has not been created.

    Use case ends.

* 1c. FL detects missing arguments or an error in the entered data.
    * 1c1. FL feedbacks that entered data is in a wrong format.

    Use case ends.

* 1d. FL detects duplicate pair records in the entered data.
    * 1d1. FL feedbacks that it is a duplicate record.

    Use case ends.

**Use case: UC02- Add Elderly**

**MSS**

1.  User enters the details of elderly to be added into the application.
2.  FL adds the elderly into the database, and feedbacks the successful addition of the elderly.
3.  User see the elderly details appear in the elderly list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL feedbacks that entered data is in a wrong format.
    
    Use case ends.

* 1b. FL detects duplicate elderly records in the entered data.
    * 1b1. FL informs it is a duplicate record.

    Use case ends.

**Use case: UC03- Add Volunteer**

**MSS**

1.  User enters the details of volunteer to be added into the application.
2.  FL adds the volunteer into the database, and feedbacks the successful addition of the volunteer.
3.  User see the volunteer details appear in the volunteer list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
  * 1a1. FL feedbacks that entered data is in a wrong format.

  Use case ends.

* 1b. FL detects duplicate volunteer records in the entered data.
  * 1b1. FL informs it is a duplicate record.

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
    * 1b1. FL feedbacks that entered data is in a wrong format.

  Use case ends.

**Use case: UC05- Delete Volunteer**

**MSS**

1.  User enters the NRIC of the volunteer to be deleted.
2.  FL deletes the volunteer from the database, and feedbacks the successful deletion of the volunteer.
3.  User see the volunteer details removed from the volunteer list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
    * 1a1. FL feedbacks that entered data is in a wrong format.

  Use case ends.

* 1b. FL detects that the volunteer is not inside the records.
    * 1b1. FL informs that the volunteer does not exist.

  Use case ends.

**Use case: UC06-  Delete Elderly**

**MSS**

1.  User enters the NRIC of the elderly to be deleted.
2.  FL deletes the elderly from the database, and feedbacks the successful deletion of the elderly.
3.  User see the elderly details removed from the elderly list.

    Use case ends.

**Extensions**

* 1a. FL detects missing arguments or an error in the entered data.
  * 1a1. FL feedbacks that entered data is in a wrong format.

  Use case ends.

* 1b. FL detects that the elderly is not inside the records.
  * 1b1. FL informs that the elderly does not exist.

  Use case ends.

### Non-Functional Requirements

1. FriendlyLink should work on Microsoft Windows, macOS, and Linux that has `Java 11` is installed.
1. FriendlyLink Should be able to hold up to 100 person (elderly and volunteer) without incurring a delay larger than 3 second for any command.
1. A user with above average typing speed (40wpm) for regular English text (i.e. not code, not system admin commands) should be able to perform at least 75% of use cases faster using commands instead of using the mouse.
1. FriendlyLink will perform minimal checks on correctness of details entered into FriendlyLink (as specified in the user guide advanced section).
1. FriendlyLink will not be responsible for the privacy and security of the data stored in FriendlyLink.
1. FriendlyLink will not recover from corrupted data files.
1. FriendlyLink will only available in English.
1. FriendlyLink does not require internet connection to work.
1. FriendlyLink is meant for VWOs in Singapore to contain information of and pair elderly and volunteers.

### Glossary

| Term            | Definition                                                                                  |
|-----------------|---------------------------------------------------------------------------------------------|
| FriendlyLink    | The name of the application                                                                 |
| Volunteer       | Volunteers who are willing to pair up with and accompany Elderly members                    |
| Elderly         | Elderly who need help from their buddies                                                    |
| Pair            | The pair of people that consists of an elderly and the volunteer assigned to the elderly    |
| Risk level      | The condition of the elderly                                                                |
| Available dates | Dates where the elderly are free to be visited and dates where volunteers are free to visit |
| Region          | Urban planning subdivisions demarcated by the Urban Redevelopment Authority of Singapore    |
| NRIC            | A unique identifier given to all Singaporeans.                                              |

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

  1. Download the jar file and copy into an empty folder

  1. Double-click the jar file Expected: Shows the GUI. The window size may not be optimum.

1. Saving window preferences

  1. Resize the window to an optimum size. Move the window to a different location. Close the window.

  1. Re-launch the app by double-clicking the jar file.<br>
     Expected: The most recent window size and location is retained.

1. Exiting the app
  
  1. Use the `exit` command or click the 'X' button in the top right corner.<br>
     Expected: The app closes.
