# FriendlyLink - User Guide

By: `Team CS2103T-W12-1` Since `Jan 2023` Licence: `MIT`

1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [Features](#features)
    1. [Viewing help: `help`](#viewing-help--help)
    2. [Adding records](#adding-records)
        1. [Adding an elderly: `add_elderly`](#adding-an-elderly-add_elderly)
        2. [Adding a volunteer: `add_volunteer`](#adding-a-volunteer-add_volunteer)
        3. [Pair volunteer and elderly: `add_pair`](#pair-volunteer-and-elderly-add_pair)
    3. [Editing records](#editing-records)
        1. [Editing a elderly by index : `edit_elderly`](#editing-a-elderly-by-index--edit_elderly)
        2. [Editing a volunteer by index : `edit_volunteer`](#editing-a-volunteer-by-index--edit_volunteer)
        3. [Editing a person by NRIC: `edit`](#editing-a-person-by-nric-edit)
    4. [Deleting records](#deleting-records)
        1. [Deleting an elderly : `delete_elderly`](#deleting-an-elderly--delete_elderly)
        2. [Deleting a volunteer : `delete_volunteer`](#deleting-a-volunteer--delete_volunteer)
        3. [Unpair volunteer and elderly: `delete_pair`](#unpair-volunteer-and-elderly-delete_pair)
    5. [Finding people and their related pairs: `find`](#finding-people-and-their-related-pairs-find)
    6. [Show Summary Statistics: `stats`](#show-summary-statistics-stats)
    7. [Exiting the program : `exit`](#exiting-the-program--exit)
    8. [Saving the data](#saving-the-data)
    9. [Editing the data file](#editing-the-data-file)
    10. [Auto-complete](#auto-complete)
4. [Advanced Details](#advanced-details)
5. [Shortcuts](#shortcuts)
6. [FAQ](#faq)
7. [Command summary](#command-summary)

----------------------------------------------------

## Introduction

FriendlyLink **streamlines volunteer and elderly management** for single administrators of small NPOs.
With its easy-to-use **text-based interface** and contact management features, say goodbye to manual
record-keeping and hello to a more efficient and organised way of managing the volunteers’ and elderly’s contact details.

----------------------------------------------------

## Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `friendlyLink.jar` from [here](https://github.com/AY2223S2-CS2103T-W12-1/tp/releases).

3. Copy the file to an empty folder you want to use as the _home folder_ for your FriendlyLink.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar friendlylink.jar` command to run the application.
   The GUI should appear in a few seconds. <br>

![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `add_elderly n/John Doe a/Linden Drive enr/S0312312A ag/76 r/LOW re/NORTH` : Adds an elderly named `John Doe`, NRIC `S0312312A` to FriendlyLink.

    * `delete_elderly S0312312A` : Deletes the elderly with NRIC `S0312312A`.

    * `clear` : Deletes all data.

    * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

---------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Command Format**<br>

* Words in `UPPER_CASE` are required attributes to be supplied by the users. <br> Example: `add_volunteer n/NAME vnr/NRIC`, `NAME` and `NRIC` are parameters that need to be supplied.
* Attributes in `[]` are optional. <br> Example: `add_volunteer n/NAME vnr/NRIC [e/EMAIL] [re/REGION]`, then `EMAIL` and `REGION` are optional attributes.
* Attributes with trailing `...` can be provided 0 or multiple times. <br> Example: `add_elderly n/NAME enr/NRIC [t/TAG]...`, then the elderly can have 0, 1, or many tags.
* Attributes can be provided in any order. <br> Example: if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
* If a parameter is expected only once in the command and is specified multiple times, only the last occurrence of the parameter will be taken.<br>
  Example: if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.
* Extraneous parameters for commands that do not take in parameters (such as `help` and `exit`) will be ignored.<br>
  Example: if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

[//]: # (Need to update help message pic)
![help message](images/helpMessage.png)

Format: `help`

--------------------------------------------------

### Adding records

Adds an elderly, a volunteer, or a pairing between one elderly and one volunteer to FriendlyLink.

#### Adding an elderly: `add_elderly`

Adds an elderly to FriendlyLink.

Format: `add_elderly n/NAME enr/NRIC [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Every elderly must have a unique `NRIC`, which is 9-alphabets long.
* Alphabets in `NRIC` are case-insensitive.
* The `REGION` can only take 5 values: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` or `EAST`.
* The `RISK_LEVEL` can only takes 3 values: `LOW`, `MEDIUM` or `HIGH`.
* Dates specified should follow the format `YYYY-MM-DD`. 
* For available dates, the start date should be before the end date.
* Phone number specified can only be numeric characters, and must be at least 3 digits long.
* `AVAILABLE_DATE_START, AVAILABLE_DATE_END` represents the start and end of the dates that the elderly is available.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An elderly can have any number of tags and available dates.
</div>

Examples:

* `add_elderly n/John Doe p/98765432 e/johnd@example.com a/John street, block 123 enr/S1234567C bd/1953-01-08 re/NORTH r/HIGH t/lonely t/diabetes`
* `add_elderly n/Betsy Crowe a/Newgate Prison p/1234567 enr/T1234567D bd/1948-02-08 re/WEST r/LOW t/lonely dr/2023-02-14,2023-05-01 dr/2023-06-03,2023-06-17`
* `add_elderly n/John Wick a/New yourk p/1234561 enr/T1254567D r/HIGH`
* `add_elderly n/Sally Black ag/70 enr/S8457677M`

#### Adding a volunteer: `add_volunteer`

Adds a volunteer to FriendlyLink.

Format: `add_volunteer vnr/NRIC n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [t/TAG]…​ [mt/MEDICAL_QUALIFICATIONS]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…​`

* Every volunteer must have a unique `NRIC`.
* Alphabets in `NRIC` are case-insensitive.
* The `REGION` can only take 5 values: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` or `EAST`.
* Dates specified should follow the format `YYYY-MM-DD`. 
* For available dates, the start date should be before the end date.
* Phone number specified can only be numeric characters, and must be at least 3 digits long.
* The `MEDICAL_QUALIFICATION` takes the form `SKILL_NAME LEVEL`. The `LEVEL` can only take 3 values: `BASIC`, `INTERMEDIATE` or `HIGH`. Exmaple: `CPR BASIC`, `AED INTERMEDIATE`.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A volunteer can have any number of tags, medical qualifications and available dates. 
</div>

Examples:

* `add_volunteer n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 bd/1999-08-05 vnr/S8457677H re/WEST t/graduate t/new`
* `add_volunteer n/Betsy Crowe p/1234567 bd/1993-08-09 vnr/S8959886I re/NORTH t/experienced mt/CPR ADVANCED mt/BLS BASIC dr/2023-06-03,2023-06-17`
* `add_volunteer n/John Wick e/johnwick@example.com a/New yourk p/1234561 vnr/T1254567D dr/2023-04-01,2023-04-15 dr/2023-03-01,2023-07-17`
* `add_volunteer n/Sally White bd/1990-11-05 vnr/S8457677H`


#### Pair volunteer and elderly: `add_pair`

Add a pairing between an existing elderly and volunteer.
This allows you to track which elderly members are assigned to which volunteers.

Format: `add_pair enr/ELDERLY_NRIC vnr/VOLUNTEER_NRIC`

* After pairing, the newly added pairs appear in the pair list in the window.
* Only elderly members and volunteers existing in FriendlyLink's data can be paired.
* Only elderly members and volunteers with intersecting available dates can be paired.
* Elderly member and volunteers in different regions can be paired but a warning message is issued.
* Duplicate pairs will fail to be added to FriendlyLink.
* Alphabets in NRIC are case-insensitive.

<div markdown="block" class="alert alert-info">

**:information_source: Notes pairing**<br>

* A warning will be given if there are clashes in availability between the volunteer and elderly when pairing.

</div>

Examples:
* `add_pair enr/S2235243I vnr/t0123423a` pairs up the elderly with NRIC S2235243I with the volunteer with NRIC T0123423A.
* `add_pair enr/s1135243A vnr/S0773423a` pairs up the elderly with NRIC S1135243A with the volunteer with NRIC S0773423A.

---------------------------------------------

### Editing records

Edit the information of an existing elderly or volunteer in FriendlyLink, based on their index or NRIC.

#### Editing a elderly by index : `edit_elderly`

Edits an existing elderly based on their index in the elderly list.

Format: `edit_elderly INDEX [n/NAME] [nr/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the elderly at the specified `INDEX`. The index refers to the index number shown in the displayed elderly list. The index **must be a positive integer** 1, 2, 3, …​
* Any combination of the optional fields is possible but **at least one** optional field must be specified. 
* Existing values will be updated to the input values.
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags or available dates will overwrite previous ones and are not cumulative.
* You can remove all the elderly’s tags by typing `t/` without specifying any tags after it. Same for available dates by typing `dr/`.

Examples:

* `edit_elderly 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st elderly to be `91234567` and `johndoe@example.com` respectively.
* `edit_elderly 2 n/Betsy Crower t/` Edits the name of the 2nd elderly to be `Betsy Crower` and clears all existing tags.

#### Editing a volunteer by index: `edit_volunteer`

Edits an existing volunteer based on their index in the volunteers list.

Format: `edit_volunteer INDEX [n/NAME] [nr/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the volunteer at the specified `INDEX`. The index refers to the index number shown in the displayed volunteers list. The index **must be a positive integer** 1, 2, 3, …​
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags, medical qualifications or available dates will overwrite previous ones and are not cumulative.
* You can remove all the volunteer’s tags by typing `t/` without specifying any tags after it. Same behavior as tags for available dates and medical qualifications by typing `dr/` and `mt/` respectively.

Examples:

* `edit_volunteer 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st volunteer to be `91234567` and `johndoe@example.com` respectively.
* `edit_volunteer 2 n/Betsy Crower mt/` Edits the name of the 2nd volunteer to be `Betsy Crower` and clears all existing medical qualifications.

#### Editing a person by NRIC: `edit`

Edits an existing elderly or volunteer identified by their NRIC.

Format: `edit NRIC [n/NAME] [nr/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the person identified by `NRIC`. As no people records of duplicate `NRIC` are allowed in FriendlyLink, one `NRIC` uniquely identifies one elderly or volunteer.
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.
* When editing elderly or volunteer-specific fields, if such fields do not match the identity of the target person, the change will be ignored. Example: `edit S1234567A r/LOW` will ignore the change of `RISK_LEVEL` to be `LOW` if `S1234567A` identifies a volunteer 
(as volunteers do not have a `RISK_LEVEL` attribute).
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags, medical qualifications or available dates will overwrite previous ones and are not cumulative.
* You can remove all the person's tags by typing `t/` without specifying any tags after it. Same behavior as tags for available dates and medical qualifications by typing `dr/` and `mt/` respectively.

Examples:

* `edit S2233556T p/91642345 re/NORTH` Edits the phone number of the person identified by `S2233556T` to be `91642345` and region to be `NORTH`.
* `edit S8833657U ag/68 r/HIGH` Edits the age of the person identified by `S8833657U` to be `68` and risk level to be `HIGH`. However, if `S8833657U` identifies a volunteer, the risk level edit will be ignored.

------------------------------------------------

### Deleting records

Delete the specific information of an existing elderly or volunteer in FriendlyLink, based on their NRIC.

#### Deleting an elderly: `delete_elderly`

Deletes the specified elderly from FriendlyLink.

Format: `delete_elderly NRIC`

* Deletes the elderly with the specified NRIC `NRIC`.
* If no existing elderly matches the specified `NRIC`, FriendlyLink will inform the user that no such elderly exists.
* If the deleted elderly has existing pairings, the corresponding pairing information will also be deleted.

Examples:
* `delete_elderly S8238657A` deletes an existing elderly with NRIC `S8238657A`, as well as all the pairings containing this elderly.

#### Deleting a volunteer: `delete_volunteer`

Deletes the specified volunteer from FriendlyLink.

Format: `delete_volunteer NRIC`

* Deletes the volunteer with the specified NRIC `NRIC`.
* If no existing volunteer matches the specified `NRIC`, FriendlyLink will inform the user that no such volunteer exists.
* If the deleted volunteer has existing pairings, the corresponding pairing information will also be deleted.

Examples:
* `delete_volunteer S8238658J` deletes an existing volunteer with NRIC `S8238658J`, as well as all the pairings containing thsi volunteer.

#### Unpair volunteer and elderly: `delete_pair`

Unpairs an elderly from its assigned volunteer.

This deletes the pair while still keeping the information of the elderly member and volunteer.

Format `delete_pair enr/ELDERLY_NRIC vnr/VOLUNTEER_NRIC`

* After deletion, the pair is removed in the list of pairs in the window.
* Alphabets in NRIC are case-insensitive.

Examples
* `delete_pair enr/S2235243I vnr/t0123423a` unpairs the elderly with NRIC S2235243I with the volunteer with NRIC T0123423A.
* `delete_pair enr/s1135243A vnr/S0773423a` unpairs the elderly with NRIC S1135243A with the volunteer with NRIC S0773423A.

-----------------------------------------

### Listing persons: `list`

Shows a list of all persons in the address book or paired and unpaired persons if specified.

Format: `list [paired/unpaired]`

* All persons will be listed if "paired" or "unpaired" is not specified after the list word
* `[paired/unpaired]` is case-insensitive e.g. `pAIReD` will match `paired`.
* Pair list will always list all pairs when the command executes.

Examples:

* `list`
* `list paired`
* `list unpaired`

-----------------------------------------

### Finding people and their related pairs: `find`

Finds any elderly or volunteers matching **all** of the specified fields, and pairings that they are involved in.

Format: `find [n/NAME] [nr/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS] [t/TAG] [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]`

* Fields can be in any order.
* The fields are optional so any combination of them is possible but **at least one** field must be specified.
* When multiple of the same field are specified, only the last one will be taken. e.g. `find n/jane n/john` will search for names that contain `john` only.
* The search is case-insensitive for all fields. e.g. `jANe` will match `Jane`.
* Specifying a certain portion of a field is possible except for `[r/RISK_LEVEL]`, `[mt/MEDICAL_QUALIFICATIONS]`, `[re/REGION]`, `[t/TAG]…` and `[dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` e.g. `Joh` for the `n/NAME` field will match `John` and `John Doe`.
* For `[dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]` date ranges that starts before or equal to `AVAILABLE_DATE_START` and ends after or equal to `AVAILABLE_DATE_END` will match.

Examples:

* `find n/John Doe`
* `find re/CENTRAL`
* `find t/experienced p/1234567 e/betsycrowe@example.com a/Newgate Estate`
* `find n/John Wick e/johnwick@example.com a/New York p/1234561 nr/T1254567D re/north r/low, t/funny dr/2023-04-01,2023-04-15`

---------------------------------------

### Show Summary Statistics: `stats`

Shows the statistics of FriendlyLink.

This shows the total number of elderly, volunteers and pairs. It also shows the average number of elderly paired to volunteers and vice versa.
This command can be entered after the `find` command to show statistics on a subset of data (e.g. Find statistics of people in a particular region)

Format `stats`

* The summary is shown in the feedback box below your input.

Examples
* `stats` Display summary statistics on every person and pair.

* ```
  find n/alice
  stats
  ```
  Display summary statistics for all persons (and associated pairs) with `alice` in their name.

-------------------------------------------

### Exiting the program : `exit`

Exits the program.

Format: `exit`

---------------------------------------------

### Saving the data

FriendlyLink data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------

### Editing the data file

FriendlyLink data are saved as a JSON file `[JAR file location]/data/friendlylink.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, FriendlyLink will discard all data and start with an empty data file at the next run.
</div>

-------------------------------------------------

### Auto-complete

FriendlyLink provides auto-complete suggestions for the available commands, or attribute prefixes when adding new records of elderly or volunteers into the database.

Example:
* Typing `add` will suggest `add_elderly` or `add_volunteer`.
* Typing `fi` will suggest `find`.

For adding records, if the user has not input all the available attributes for the new input elderly or volunteer, the auto-complete feature will automatically suggests all the remaining attributes. No new suggestions will be given if all possible attributes has at least one value provided.

Example:
* Typing `add_volunteer n/Harry p/12345686`, FriendlyLink will suggest `e/<email> a/<address> t/<tag> re/<region> mt/<medical_tags> bd/<birth_date> dr/<start_date,end_date>` as these attributes have not been filled.
* Typing `add_volunteer n/Betsy p/1234567 e/test@test.com a/Linken Drive bd/1990-01-01 vnr/S8959886I re/NORTH t/experienced mt/CPR ADVANCED dr/2023-06-03,2023-06-17`, FriendlyLink will not suggest anything as all possible attributes have at least one value.

------------------------------------------

## Advanced Details

### Prefixes
* Prefixes should be entered in all lower case (e.g. n/Abdul instead of N/Abdul)
* Fields after prefixes have leading and trailing whitespaces removed (e.g. `n/  Mary` is truncated to `n/Mary`)

### NRIC
* NRIC is case-insensitive
* There is no cross validation of age against NRIC (i.e. There are no checks for the birth year in first 2 digits of NRIC)

### Phone number
* Phone number must be strictly numeric (i.e digits from 0 to 9) and have more than 3 digits

### Email
* Email must be in the `local-part@domain.com` format, containing the `@`

### Date
* Date must be in the format `YYYY-MM-DD`
* Entering of dates before the current date is allowed
* Past dates will not be removed
* Where relevant, start date must occur before end date

### Region
* Region must be one of the following value: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` and `EAST`.

### RiskLevel
* For elderly, risk level can only be one of the following value: `LOW`, `MEDIUM` or `HIGH`.

### Medical Qualification
* For volunteer, medical qualificaiton must be in the format `SKILL_NAME LEVEL`.
* The `LEVEL` must be one of the following value: `BASIC`, `INTERMEDIATE` or `HIGH`.
* Exmaple: `CPR BASIC`, `AED INTERMEDIATE`.

### Duplicate Entries
* Person (Elderly and Volunteers)
    * Persons with identical `NRIC` are considered the same person.
    * A person cannot be both an elderly and a volunteer.
* Pair
    * Pairs with the same elderly and same volunteer are the same pair.

--------------------------------------------------

## ShortCuts

* `CTRL+C`: Copy the text
* `CTRL+V`: Paste the text into command box
* `CTRL+Z`: Undo the previous command

---------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous FriendlyLink home folder.

---------------------------------------------------

## Command summary

| Action               | Format, Examples                                                                                                                                                                                                                                                                                                           |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Elderly**      | `add_elderly n/NAME enr/NRIC [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> e.g.,`add_elderly n/John p/98765432 e/johnd@example.com a/John street enr/S1234567C bd/1950-02-03 re/NORTH r/HIGH t/lonely dr/2023-06-03,2023-06-17` |
| **Add Volunteer**    | `add_volunteer vnr/NRIC n/NAME [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [t/TAG]…​ [mt/MEDICAL_QUALIFICATIONS]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…​` <br> e.g.,`add_volunteer n/Doe p/98765432 e/johnd@example.com a/block 123 bd/1998-02-01 vnr/S8457677H re/WEST t/graduate mt/CPR BASIC`  |
| **Add Pair**         | `add_pair enr/ELDERLY_NRIC vnr/VOLUNTEER_NRIC`<br> e.g., `add_pair enr/S2235243I vnr/t0123423a`                                                                                                                                                                                                                            |
| **Edit Elderly**     | `edit_elderly INDEX [n/NAME] [nr/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> e.g., `edit_elderly 1 p/91234567 e/johndoe@example.com`                                                                                    |
| **Edit Volunteer**   | `edit_volunteer INDEX [n/NAME] [nr/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> e.g., `edit_volunteer 2 n/Betsy Crower mt/`                                                                         |
| **Edit Person**      | `edit NRIC [n/NAME] [nr/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> e.g., `edit S1234567A p/12334455`                                                                                      |
| **Delete Elderly**   | `delete_elderly NRIC`<br> e.g., `delete_ elderly S8238655C`                                                                                                                                                                                                                                                                |
| **Delete Volunteer** | `delete_volunteer NRIC`<br> e.g., `delete_volunteer S8238658J`                                                                                                                                                                                                                                                             |
| **Delete Pair**      | `delete_pair enr/ELDERLY_NRIC vnr/VOLUNTEER_NRIC`<br> e.g., `delete_pair vnr/t0123423a enr/S2235243I`                                                                                                                                                                                                                      |
| **Find People**      | `find [n/NAME] [nr/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS] [t/TAG] [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]` <br> e.g., `find n/John Doe`                                                                                                 |
| **Summarise Data**   | `stats`                                                                                                                                                                                                                                                                                                                    |
| **Help**             | `help`                                                                                                                                                                                                                                                                                                                     |
| **Exit Program**     | `exit`                                                                                                                                                                                                                                                                                                                     |

[//]: # (| **Edit**             | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                                    |)

