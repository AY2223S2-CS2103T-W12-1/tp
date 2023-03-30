---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

----------------------------------------------------

## Introduction
FriendlyLink **streamlines volunteer and elderly management** for single administrators of small VWOs. With FriendlyLink, administrators such as yourself can easily manage your database and pair volunteers with the elderly as you wish, all through an intuitive, user-friendly interface. Our goal is to make volunteer and elderly management simple, efficient, and effective, so that you can focus on making a difference in your communities.

With its easy-to-use **text-based interface** and contact management features, say goodbye to manual record-keeping and hello to a more efficient and organised way of managing the volunteers’ and elderly’s contact details.

## Before you Begin

Please read this section before skipping to specific parts in the `Features` section.

<div markdown="block" class="alert alert-danger">:exclamation: **Notes on display resolution**

The application has been tested extensively on a display resolution of 1920 X 1200 and a display zoom of 125%. 
It is recommended that you switch to this resolution before proceeding with the rest of the user guide. UI bugs stemming 
from other resolutions or different zoom levels is out of scope (impracticability due to lack of time).

</div>

### Purpose of Guide

This guide aims to teach you how to navigate and use FriendlyLink

### How to use this User Guide

This guide quickly gets you started to use FriendlyLink, describing our features and providing their instruction format and examples. Some terms used have specific meanings and are explained below and summarised in the `Glossary`.

We suggest reading this guide in sequential order (or at least the whole of this section) to familiarise yourself with the keywords used in this guide in the glossary and differently styled text which have special meanings.

### How to Use FriendlyLink

#### Information Boxes

You may see the following icons throughout our user guide, which are styled differently according to their purpose.

<div markdown="span" class="alert alert-info">:information_source: **Info:**
This provides some additional information that you are recommended to know.
</div>

#### Tip Box

<div markdown="block" class="alert alert-primary">:bulb: **Tip:**
This provides some quick and convenient hacks that you can use to optimize your experience with FriendlyLink.
</div>

#### Danger Box

<div markdown="block" class="alert alert-danger">:exclamation: **Warning:**
Danger zone! Do pay attention to the information here carefully.
</div>

#### FriendlyLink
FriendlyLink is a command line based tool. This means that it is mainly designed to receive your text commands, and show you the output after each command is carried out.

FriendlyLink stores paired elderly and volunteers, which you can add, modify or remove.

#### Commands
A command is an instruction given by you to FriendlyLink to perform a specific task. For example, `add_elderly n/John Doe ...` tells FriendlyLink to record the information of a new person in FriendlyLink. More details about each command is given in the `Features` section.

#### Prefixes
Prefixes are the characters appearing before a slash in a command. Prefixes label the information that they represent. For example, the add elderly command `add_elderly ic/S1234567A ...` contains the prefix `ic` to indicate that the text that follows is the NRIC of the elderly.
* Prefixes should be entered in all lower case (E.g. n/Abdul instead of N/Abdul)
* Fields after prefixes have leading and trailing whitespaces removed (e.g. `n/ Mary` is truncated to `n/Mary`)

#### Entities
An entity is a generic term for an object stored in FriendlyLink. It includes elderly, volunteers and pairs.

#### Indexes
Indexes are natural numbers (numbers used for counting) that are used for numbering entities in a list.
* An index must be a positive integer (E.g. 1, 2, 3, …​)

#### Fields
Fields are the information following the slash in a command, to provide appropriate information to FriendlyLink, such as indicating a volunteer's name, phone number, email and other information.
* For example, `add_elderly n/John Doe ic/...` has the prefix `n` followed by a slash, followed by the field `John Doe`. This tells FriendlyLink to record the newly added elderly with the name `John Doe`.
* Fields can be entered in any order.
    * E.g. If a command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
* If a field is expected only once in the command, but you specify it multiple times, only the last occurrence of the field will be taken.<br>
    * E.g. If you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.
* Extraneous fields for commands that do not take in fields (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
    * E.g. If you specify `help 123`, it will be interpreted as `help`.

#### NRIC
NRIC is a unique identifier given to all Singaporeans.
* NRIC is case-insensitive
* The structure of the NRIC should be `@XXXXXXX#`, where:
  * `@` is a letter that can be "S", "T", "F", "G", "M"
  * `XXXXXXX` is a 7-digit serial number
  * `#` is a letter from A to Z
* There is no cross validation of birthdate against NRIC (There are no checks for the birth year in first 2 digits of NRIC)

#### Phone number
The phone number of a person.
* Phone number is strictly numeric (digits from 0 to 9) and have more than or equal to 3 digits

#### Email
The email of a person.

Emails should be of the format local-part@domain and adhere to the following constraints:

* The local-part should only contain alphanumeric characters and these special characters, excluding the parentheses, (+_.-). The local-part may not start or end with any special characters.
* This is followed by a '@' and then a domain name. The domain name is made up of domain labels separated by periods.

The domain name must:
* end with a domain label at least 2 characters long
* have each domain label start and end with alphanumeric characters
* have each domain label consist of alphanumeric characters, separated only by hyphens, if any.

#### Date
A date represents a point in time, such as aa birthdate or starting or ending days where someone is available for meet ups.
* Date must be in the format `YYYY-MM-DD`
* Entering dates that have already passed is allowed.
* When a person's available dates have passed, it will not be removed.
* Where relevant, start date must occur before end date

<div markdown="block" class="alert alert-primary">:bulb: **Tip:**
You can enter a person's available date for record keeping purposes, even if the date has already passed
</div>

#### Region
The general area in **Singapore**.
* Region must be one of the following values: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` and `EAST`.

#### Risk Level
The susceptibility level of an elderly to injury or sickness.
* Risk level can only be one of the following values: `LOW`, `MEDIUM` or `HIGH`.
* Specified for elderly only.

#### Medical Qualification
* Medical qualification must be in the format `SKILL_NAME, LEVEL`.
  * The `LEVEL` must be one of the following value: `BASIC`, `INTERMEDIATE` or `HIGH`.
  * Example: `CPR, BASIC`, `AED, INTERMEDIATE`.
  * The `SKILL_NAME` should not include any spaces.
* Specified for volunteers only.

#### Available Dates

The availability of a person.
* The start of the available dates must be before the end of the available dates.

<div markdown="block" class="alert alert-danger">:exclamation: **Warning**

Out of concerns of time, we do not implement the scheme to check and merge overlapping available date ranges, 
such as `2022-01-03,2022-01-20` and `2022-01-10,2022-01-23`. Therefore, to ensure maximum efficiency for the software,
please ensure your input available date ranges are non-overlapping.

</div>

#### Tags
A tag is a generic description for a group of people.
* Only alphanumeric, non-space characters are allowed.

#### Duplicate Entries
* Person (Elderly and Volunteers)
  * Two persons having the same `NRIC` are considered the same person and therefore a duplicate entry in FriendlyLink, and is not allowed.
  * The same person in FriendlyLink cannot be both an elderly and a volunteer at the same time.
* Pair
  * Two pairs having the same elderly and volunteer are considered a duplicate entry in FriendlyLink, and is not allowed.

#### Code highlights

`Highlights` are used to denote specific terms, commands or output from the application. These are case-sensitive and may not give the correct results if you use different capitalisation or if you include additional space in them.

* Highlighted commands consists special characters or capitalisation, which have special meaning.
    * Words that are `CAPITALISED` are placeholders that should be replaced by you.<br>
      e.g. in `add n/NAME`, `NAME` should be replaced with actual information `add n/John Doe`.

    * Items in `[square brackets]` are optional.<br>
      e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or just `n/John Doe`.

    * Items with `[ellipsis]…` after them can be used 0 or more times.<br>
      e.g. `add_elderly n/NAME [t/TAG]…` can be used as `add_elderly n/John Doe` (no tags included), `add_elderly n/John Doe t/friend` (1 tag), `add_elderly n/John Doe t/friend t/family` (2 tags) and more.

---

## Glossary

These terms have specific meanings in the context of FriendlyLink. For a more detailed description, refer to the `How to Use FriendlyLink` section.

| Term                  | Meaning                                                                                                                                                     |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Command               | An instruction given by you to FriendlyLink to perform a specific task.                                                                                     |
| Date                  | A date representing the timestamp of an event, in the format `YYYY-MM-DD`                                                                                   |
| Duplicate Pairs       | Two pairs having the same elderly and volunteer are considered a duplicate entry in FriendlyLink                                                            |
| Duplicate Persons     | Two persons having the same NRIC are considered a duplicate entry in FriendlyLink                                                                           |
| Elderly               | Elderly are people under the care of your VWO                                                                                                               |
| Email                 | The email of a person, in the `localPart@domain` format, containing the `@`                                                                                 |
| Entity                | A generic term to describe an object stored in FriendlyLink, such as elderly, volunteer and pairs                                                           |
| FriendlyLink          | The name of our application                                                                                                                                 |
| Field                 | A field is the information following the slash in a command.                                                                                                |
| Index                 | An index represents the position of the referred item in a displayed list of entities. It must be a positive integer.                                       |
| Medical Qualification | The level of care taking or first aid of a volunteer. It consists of the type of skill (E.g. CP, AED) and a skill level (`BASIC`, `INTERMEDIATE` or `HIGH`) |
| NRIC                  | A unique identifier given to all Singaporeans. It is case-insensitive.                                                                                      |
| Pair                  | A pair consists of an elderly and a volunteer assigned to accompany and take care of the elderly                                                            | 
| Phone number          | The phone number of a person. Must be numeric and has more than 3 digits                                                                                    |
| Prefixes              | Prefixes are the characters appearing before a slash in a command. Prefixes describe the field that they represent.                                         |
| Region                | The general portion of area in Singapore. Must be one of the following values: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` or `EAST`                            |
| Risk level            | The susceptibility level of an elderly to injury or sickness. Must be one of the following values: `LOW`, `MEDIUM` or `HIGH`                                |
| Tag                   | A generic description for a group of people. Must contain only alphanumeric characters                                                                      |
| Volunteer             | Volunteers that signed up to pair up with and accompany elderly members                                                                                     |
| VWO                   | Voluntary Welfare Organisations such as yourself                                                                                                            |

## Quick Start

1. Ensure you have [Java 11](https://www.oracle.com/sg/java/technologies/javase/jdk11-archive-downloads.html) installed in your Computer.

1. Download the latest `friendlylink.jar` from [here](https://github.com/AY2223S2-CS2103T-W12-1/tp/releases).

1. Move the `friendlylink.jar` to an empty folder where you want FriendlyLink to store information.

1. Double-click on the `friendlylink.jar` file. If the app does not open, follow the following steps instead.
    * Open a terminal
      * On **Windows**: Click Start and search for `Command Prompt`
      * On **macOS**: Open Launchpad and search for `terminal`
    * Move into the folder you are keeping FriendlyLink by entering `cd FILE/PATH/TO/FRIENDLYLINK` into the terminal
    * Open the app by entering `java -jar friendlylink.jar` into the terminal.
   
   The GUI should appear in a few seconds.
   ![Ui](images/emptyFriendlyLink.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `add_elderly n/John Doe ic/S1234567A bd/1959-09-09 re/WEST r/LOW` : Adds an elderly named `John Doe`with NRIC 
`S1234567A` to FriendlyLink, whose birthday is `1959-09-09`, lives in `WEST` region and has `LOW` health risk.

    * `delete_elderly S1234567A` : Deletes the elderly with NRIC `S1234567A`.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

---------------------------------------------------
## Features

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.PNG)

Format: `help`

--------------------------------------------------

### Adding records

Adds an elderly, a volunteer, or a pairing between one elderly and one volunteer to FriendlyLink.

#### Adding an elderly: `add_elderly`

Adds an elderly to FriendlyLink.

Format: `add_elderly n/NAME ic/NRIC bd/BIRTH_DATE [re/REGION] [r/RISK_LEVEL] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Every elderly must have a unique `NRIC`. Refer to the [NRIC section](#nric) for the required format for NRIC.
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

* `add_elderly n/John Wick ic/S1234567C bd/1950-02-03 re/NORTH r/HIGH`
* `add_elderly n/Betsy Crowe p/98765432 e/johnd@example.com a/John street ic/S1234567C bd/1950-02-03 re/NORTH r/HIGH t/lonely dr/2023-06-03,2023-06-17`

#### Adding a volunteer: `add_volunteer`

Adds a volunteer to FriendlyLink.

Format: `add_volunteer ic/NRIC n/NAME bd/BIRTH_DATE [re/REGION] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [mt/MEDICAL_QUALIFICATIONS]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…​`

* Every volunteer must have a unique `NRIC`. Refer to the [NRIC section](#nric) for the required format for NRIC.
* Alphabets in `NRIC` are case-insensitive.
* The `REGION` can only take 5 values: `NORTH`, `NORTHEAST`, `CENTRAL`, `WEST` or `EAST`.
* Dates specified should follow the format `YYYY-MM-DD`. 
* For available dates, the start date should be before the end date.
* Phone number specified can only be numeric characters, and must be at least 3 digits long.
* The `MEDICAL_QUALIFICATION` takes the form `SKILL_NAME, LEVEL`. The `LEVEL` can only take 3 values: `BASIC`, `INTERMEDIATE` or `HIGH`. Example: `CPR, BASIC`, `AED, INTERMEDIATE`.
* `AVAILABLE_DATE_START, AVAILABLE_DATE_END` represents the start and end of the dates that the volunteer is available.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A volunteer can have any number of tags, medical qualifications and available dates. 
</div>

Examples:

* `add_volunteer n/John Wick bd/1990-11-05 e/johnwick@example.com a/New yourk p/1234561 ic/T1254567D dr/2023-04-01,2023-04-15`
* `add_volunteer n/Sally White bd/1990-11-05 ic/S8457677H re/EAST`

#### Pair volunteer and elderly: `pair`

Add a pairing between an existing elderly and volunteer.
This allows you to track which elderly members are assigned to which volunteers.

Format: `pair eic/ELDERLY_NRIC vic/VOLUNTEER_NRIC`

* After pairing, the newly added pairs appear in the pair list in the window.
* Only elderly members and volunteers existing in FriendlyLink's data can be paired.
* Elderly member and volunteers with no common available dates can be paired but a warning message is issued.
* Elderly member and volunteers in different regions can be paired but a warning message is issued.
* Duplicate pairs will fail to be added to FriendlyLink.
* Alphabets in NRIC are case-insensitive.

<div markdown="block" class="alert alert-info">
   
**:information_source: Info**<br>
* A warning will be given when:
   * there are clashes in availability between the volunteer and elderly; or
   * the volunteer and elderly lives in different regions
   
</div>

Examples:
* `pair eic/S2235243I vic/t0123423a` pairs up the elderly with NRIC S2235243I with the volunteer with NRIC T0123423A.
* `pair eic/s1135243A vic/S0773423a` pairs up the elderly with NRIC S1135243A with the volunteer with NRIC S0773423A.

#### Auto pairing of volunteers and elderly: `auto_pair`

Automatically pairs all unpaired elderly and volunteers, if possible. This command is intended to give
you a starting point for how the pairs can be formed, and aims to make the manual process of pairing
slightly easier. 

Format: `auto_pair`

* The volunteer and elderly in each pair generated will 
always have **matching regions** and **compatible availabilities**.
* In the event that no pairs can be formed satisfying the above constraints 
(either because there are no unpaired volunteers or elderly, or all the unpaired volunteers 
and elderly have incompatible regions or availabilities)
then a simple "_No pairs were formed._" message will be displayed.

<div markdown="block" class="alert alert-info">

**:information_source: How are the volunteers and elderly paired?**<br>

* The volunteers and elderly are paired in a _greedy_ manner: We essentially match each unpaired volunteer
with the first unpaired elderly that is compatible with it. 
* Furthermore, the pairs will be **one-to-one** i.e. every unpaired volunteer will be paired with at most
one unpaired elderly, and vice versa.
* This approach will not necessarily maximise the number of possible pairs, but it should serve as a good
starting point.

</div>

---------------------------------------------

### Editing records

Edit the information of an existing elderly or volunteer in FriendlyLink, based on their index or NRIC.

#### Editing an elderly by index : `edit_elderly`

Edits an existing elderly based on their index in the elderly list.

Format: `edit_elderly INDEX [n/NAME] [ic/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the elderly at the specified `INDEX` in the displayed elderly list.
* Any combination of the optional fields is possible but **at least one** optional field must be specified. 
* Existing values will be updated to the input values.
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags or available dates will overwrite previous ones and are not cumulative.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can remove all the elderly’s tags by typing `t/` without specifying any tags after it. Same for available dates by typing `dr/`.
</div>

Examples:

* `edit_elderly 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st elderly to be `91234567` and `johndoe@example.com` respectively.
* `edit_elderly 2 n/Betsy Crower t/` Edits the name of the 2nd elderly to be `Betsy Crower` and clears all existing tags.

#### Editing a volunteer by index: `edit_volunteer`

Edits an existing volunteer based on their index in the volunteers list.

Format: `edit_volunteer INDEX [n/NAME] [ic/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the volunteer at the specified `INDEX` in the displayed volunteer list. 
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags, medical qualifications or available dates will overwrite previous ones and are not cumulative.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can remove all the volunteer’s tags by typing `t/` without specifying any tags after it. Same behavior as tags for available dates and medical qualifications by typing `dr/` and `mt/` respectively.
</div>

Examples:

* `edit_volunteer 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st volunteer to be `91234567` and `johndoe@example.com` respectively.
* `edit_volunteer 2 n/Betsy Crower mt/` Edits the name of the 2nd volunteer to be `Betsy Crower` and clears all existing medical qualifications.

#### Editing a person by NRIC: `edit`

Edits an existing elderly or volunteer identified by their NRIC.

Format: `edit NRIC [n/NAME] [ic/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…`

* Edits the person identified by `NRIC`. As no people records of duplicate `NRIC` are allowed in FriendlyLink, one `NRIC` uniquely identifies one elderly or volunteer.
* Any combination of the optional fields is possible but **at least one** optional field must be specified.
* Existing values will be updated to the input values.
* When editing elderly or volunteer-specific fields, if such fields do not match the identity of the target person, the change will be ignored. Example: `edit S1234567A r/LOW` will ignore the change of `RISK_LEVEL` to be `LOW` if `S1234567A` identifies a volunteer 
(as volunteers do not have a `RISK_LEVEL` field).
* When editing fields allowing multiple inputs, the existing contents of the field will be removed. i.e. editing of tags, medical qualifications or available dates will overwrite previous ones and are not cumulative. 

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can remove all the person's tags by typing `t/` without specifying any tags after it. Same behavior as tags for available dates and medical qualifications by typing `dr/` and `mt/` respectively.
</div>
Examples:

* `edit S2233556T p/91642345 re/NORTH` Edits the phone number of the person identified by `S2233556T` to be `91642345` and region to be `NORTH`.
* `edit S8833657U re/CENTRAL r/HIGH` Edits the region of the person identified by `S8833657U` to be `CENTRAL` and risk level to be `HIGH`. However, if `S8833657U` identifies a volunteer, the risk level edit will be ignored.

------------------------------------------------

### Deleting records

Delete the specific existing elderly or volunteer in FriendlyLink with the given NRIC.

#### Deleting an elderly: `delete_elderly`

Deletes the specified elderly from FriendlyLink.

Format: `delete_elderly NRIC`

* Deletes the elderly with the specified NRIC `NRIC`.
* If no existing elderly matches the specified `NRIC`, FriendlyLink will inform the user that no such elderly exists.
* If the deleted elderly has existing pairings, the corresponding volunteer will be unpaired.

Examples:
* `delete_elderly S8238657A` deletes an existing elderly with NRIC `S8238657A`, as well as all the pairings containing this elderly.

#### Deleting a volunteer: `delete_volunteer`

Deletes the specified volunteer from FriendlyLink.

Format: `delete_volunteer NRIC`

* Deletes the volunteer with the specified NRIC `NRIC`.
* If no existing volunteer matches the specified `NRIC`, FriendlyLink will inform the user that no such volunteer exists.
* If the deleted volunteer has existing pairings, the corresponding elderly will be unpaired.

Examples:
* `delete_volunteer S8238658J` deletes an existing volunteer with NRIC `S8238658J`, as well as all the pairings containing this volunteer.

#### Unpair volunteer and elderly: `unpair`

Unpairs an elderly from its assigned volunteer.

This deletes the pair while still keeping the elderly and volunteer.

Format `unpair eic/ELDERLY_NRIC vic/VOLUNTEER_NRIC`

* After deletion, the pair is removed from the list of pairs in the window.

Examples
* `unpair eic/S2235243I vic/t0123423a` unpairs the elderly with NRIC S2235243I with the volunteer with NRIC T0123423A.
* `unpair eic/s1135243A vic/S0773423a` unpairs the elderly with NRIC S1135243A with the volunteer with NRIC S0773423A.

-----------------------------------------

### Listing persons: `list`

Shows a list of all persons in FriendlyLink or paired and unpaired persons if specified.

Format: `list [paired/unpaired]`

* All persons will be listed if "paired" or "unpaired" is not specified after the list word
* `[paired/unpaired]` is case-insensitive e.g. `pAIReD` will match `paired`.
* Pair list will always list all pairs when the command executes.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
`list` is useful if you need to refresh all the lists after they have been filtered.
</div>
Examples:

* `list`
* `list paired`
* `list unpaired`

--------------------------------------

### Finding people and their related pairs: `find`

Finds any elderly or volunteers matching **all** the relevant specified fields, and pairings that they are involved in.

Format: `find [n/NAME] [ic/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS] [t/TAG] [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]`

* Fields can be in any order.
* The fields are optional so any combination of them is possible but **at least one** field must be specified.
* The search is case-insensitive for all fields. e.g. `jANe` will match `Jane`.
* Elderly specific fields will not be searched for in the volunteer list and vice versa.
    * `find r/HIGH` will show all volunteers since volunteers do not contain risk level field.
    * `find mt/cpr basic` will show all elderly since elderly do not contain medical qualifications field.
* `[n/NAME]` `[ic/NRIC]` `[p/PHONE_NUMBER]` `[e/EMAIL]` `[a/ADDRESS]` `[t/TAG]` need not be specified in full e.g. `Joh` for the `n/NAME` field will match `John` and `John Doe`.
    * Such fields can contain any value but cannot be empty.
* `[r/RISK_LEVEL]`, `[bd/BIRTH_DATE]`, `[re/REGION]` and `[dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]` are required to be fully specified.
    * Such fields have to be valid.
* For `[dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]` date ranges that starts before or equal to `AVAILABLE_DATE_START` and ends after or equal to `AVAILABLE_DATE_END` will match.
* For `[mt/MEDICAL_QUALIFICATIONS]` you can either specify just the type e.g. `mt/cpr` or the type and its level separated by comma e.g. `mt/cpr, basic`.
    * The type need not be specified in full so it can contain any non-empty value.
    * Qualification level needs to be fully specified if present.

Examples:

* `find t/experienced p/1234567 e/betsycrowe@example.com a/Newgate Estate`
* `find n/John Wick e/johnwick@example.com a/New York p/1234561 ic/T1254567D re/north r/low t/funny dr/2023-04-01,2023-04-15`

---------------------------------------

### Show Summary Statistics: `stats`

Shows the statistics of FriendlyLink.

This shows the total number of elderly, volunteers and pairs. It also shows the maximum number of elderly paired to each volunteer and vice versa.
This command can be entered after the `find` command to show statistics on a subset of data (e.g. Find statistics of people in a particular region)

Format `stats`

* The summary is shown in the feedback box below your input.

Examples
* `stats` Display summary statistics on every person and pair.

* ```
  find re/NORTH
  stats
  ```
  Display summary statistics for all persons (and associated pairs) living in the north.

-------------------------------------------

### Exiting the program : `exit`

Exits the program.

Format: `exit`

---------------------------------------------

### Saving the data

FriendlyLink data are saved in the hard disk automatically after any command that changes the data. There is no need to
save manually.

--------------------------------------------

### Editing the data file

FriendlyLink data are saved in the JSON files `JAR_FILE_LOCATION/data/elderly.json`, `JAR_FILE_LOCATION/data/volunteer.json` and `JAR_FILE_LOCATION/data/pair.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="block" class="alert alert-danger">:exclamation: **Warning**
If your changes to the data file makes its format invalid, FriendlyLink will discard all data and start with an empty data file at the next run.
</div>

-------------------------------------------------

### Auto-complete

FriendlyLink provides auto-complete suggestions for the available commands, or a field prefixes when adding new records of elderly or volunteers into the database.

Example:

* Typing `add_e` will suggest `add_elderly`.
* Typing `fi` will suggest `find`.

For adding records, if the user has not input all the available fields for the new input elderly or volunteer, the
auto-complete feature will automatically recommend all remaining prefixes. No new suggestions will be given once all
possible prefixes has at least one value provided. If the user continues to specify more attributes like `t/`, the recommendation
will be done on a case-by-case basis.

Example:

* Typing `add_volunteer n/Harry p/12345686`, FriendlyLink will
  suggest `bd/BIRTH_DATE ic/NRIC e/[EMAIL] a/[ADDRESS] t/[TAG] re/[REGION] mt/[MEDICAL_QUALIFICATION] dr/[AVAILABLE_DATE_START, AVAILABLE_DATE_END]` as
  these fields have not been filled. Note that the order of fields specified here may not be what is reflected in the application.
* Typing `add_volunteer n/Betsy p/1234567 e/test@test.com a/Linken Drive bd/1990-01-01 vnr/S8959886I re/NORTH t/experienced mt/CPR, ADVANCED dr/2023-06-03,2023-06-17`
, FriendlyLink will not suggest anything by default as all possible fields have at least one value.

<div markdown="block" class="alert alert-info">

**:information_source: Notes on Command Recommendation**<br>

* If the user specifies a field that is not included in the list of accepted fields, a warning will given. The user is free to continue typing, but an
  error will be thrown when the user confirms the command.
* There is a known UI bug when the text in `command box` overflows. To improve user experience, command recommendation is disabled once overflow is detected.

</div>

--------------------------------------------------

## Shortcuts

* `CTRL+A`: Select all the text
* `CTRL+C`: Copy the text
* `CTRL+V`: Paste the text into command box
* `CTRL+Z`: Undo the previous command
* `CTRL+X`: Cut the text
* `ARROW_UP`: Navigate to the previous item on the list
* `ARROW_DOWN`: Navigate to the next item on the list

For more shortcuts, please refer [here](https://wiki.openjdk.org/display/OpenJFX/Keyboard+Navigation).

---------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous FriendlyLink home folder.

---------------------------------------------------

## Command summary

| Action               | Format, Examples                                                                                                                                                                                                                                                                                                            |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Elderly**      | `add_elderly n/NAME ic/NRIC bd/BIRTH_DATE [p/PHONE] [e/EMAIL] [a/ADDRESS] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> <br> e.g.,`add_elderly n/John ic/S1234567C bd/1950-02-03 p/98765432 e/johnd@example.com a/John street re/NORTH r/HIGH t/lonely dr/2023-06-03,2023-06-17` |
| **Add Volunteer**    | `add_volunteer ic/NRIC n/NAME bd/BIRTH_DATE [p/PHONE] [e/EMAIL] [a/ADDRESS] [re/REGION] [t/TAG]… [mt/MEDICAL_QUALIFICATIONS]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> <br> e.g.,`add_volunteer n/Doe bd/1998-02-01 ic/S8457677H p/98765432 e/johnd@example.com a/block 123 re/WEST t/graduate mt/CPR BASIC`    |
| **Pair Up**          | `pair eic/ELDERLY_NRIC vic/VOLUNTEER_NRIC`<br> <br> e.g., `pair eic/S2235243I vic/t0123423a`                                                                                                                                                                                                                                |
| **Auto Pair**        | `auto_pair`                                                                                                                                                                                                                                                                                                                 |
| **Edit Elderly**     | `edit_elderly INDEX [n/NAME] [ic/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> <br> e.g., `edit_elderly 1 p/91234567 e/johndoe@example.com`                                                                                |
| **Edit Volunteer**   | `edit_volunteer INDEX [n/NAME] [ic/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> <br> e.g., `edit_volunteer 2 n/Betsy Crower mt/`                                                                     |
| **Edit Person**      | `edit NRIC [n/NAME] [ic/NRIC] [p/PHONE] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS]… [t/TAG]… [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]…` <br> <br> e.g., `edit S1234567A p/12334455`                                                                                  |
| **Delete Elderly**   | `delete_elderly NRIC`<br> <br> e.g., `delete_elderly S8238655C`                                                                                                                                                                                                                                                             |
| **Delete Volunteer** | `delete_volunteer NRIC`<br> <br> e.g., `delete_volunteer S8238658J`                                                                                                                                                                                                                                                         |
| **Unpair**           | `unpair eic/ELDERLY_NRIC vic/VOLUNTEER_NRIC`<br> <br> e.g., `unpair vic/t0123423a eic/S2235243I`                                                                                                                                                                                                                            |
| **Find People**      | `find [n/NAME] [ic/NRIC] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [bd/BIRTH_DATE] [re/REGION] [r/RISK_LEVEL] [mt/MEDICAL_QUALIFICATIONS] [t/TAG] [dr/AVAILABLE_DATE_START, AVAILABLE_DATE_END]` <br> <br> e.g., `find n/John Doe`                                                                                             |
| **Summarise Data**   | `stats`                                                                                                                                                                                                                                                                                                                     |
| **Help**             | `help`                                                                                                                                                                                                                                                                                                                      |
| **Exit Program**     | `exit`                                                                                                                                                                                                                                                                                                                      |
