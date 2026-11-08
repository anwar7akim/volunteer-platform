# VolunTier - Detailed Test Case Descriptions

## TC-001: Login - Valid Volunteer Credentials

| Field | Detail |
|---|---|
| Test Case ID | TC-001 |
| Related Requirement(s) | FR-001 |
| Title | Login - Valid Volunteer Credentials |
| Preconditions | A volunteer account exists. The user is on the Sign In screen. |
| Test Steps | 1. Enter the volunteer email and password<br>2. Click "Sign In" |
| Expected Result | The volunteer dashboard opens. Only volunteer features show. |
| Actual Result | As expected. Automated test TC001 passed. |
| Status | Pass |
| Priority | High |

## TC-002: Login - Valid Coordinator Credentials

| Field | Detail |
|---|---|
| Test Case ID | TC-002 |
| Related Requirement(s) | FR-001 |
| Title | Login - Valid Coordinator Credentials |
| Preconditions | A coordinator account exists. The user is on the Sign In screen. |
| Test Steps | 1. Enter the coordinator email and password<br>2. Click "Sign In" |
| Expected Result | The coordinator dashboard opens with opportunity management features. |
| Actual Result | As expected. Automated test TC002 passed. |
| Status | Pass |
| Priority | High |

## TC-003: Login - Valid Administrator Credentials

| Field | Detail |
|---|---|
| Test Case ID | TC-003 |
| Related Requirement(s) | FR-001 |
| Title | Login - Valid Administrator Credentials |
| Preconditions | An administrator account exists. The user is on the Sign In screen. |
| Test Steps | 1. Enter the administrator email and password<br>2. Click "Sign In" |
| Expected Result | The administrator dashboard opens with user management and program summaries. |
| Actual Result | As expected. Automated test TC003 passed. |
| Status | Pass |
| Priority | High |

## TC-004: Login - Invalid Password

| Field | Detail |
|---|---|
| Test Case ID | TC-004 |
| Related Requirement(s) | FR-001 |
| Title | Login - Invalid Password |
| Preconditions | A volunteer account exists. The user is on the Sign In screen. |
| Test Steps | 1. Enter a valid email and a wrong password<br>2. Click "Sign In" |
| Expected Result | Login fails. A clear error message shows. The user stays on the Sign In screen. |
| Actual Result | As expected. Automated test TC004 passed. |
| Status | Pass |
| Priority | High |

## TC-005: Login - Empty Required Fields

| Field | Detail |
|---|---|
| Test Case ID | TC-005 |
| Related Requirement(s) | FR-001, NFR-006 |
| Title | Login - Empty Required Fields |
| Preconditions | The user is on the Sign In screen. |
| Test Steps | 1. Leave email and password empty<br>2. Click "Sign In" |
| Expected Result | The system marks the required fields and shows an error. No login occurs. |
| Actual Result | As expected. Automated test TC005 passed. |
| Status | Pass |
| Priority | Medium |

## TC-006: Role Restriction - Volunteer Cannot Access Coordinator Features

| Field | Detail |
|---|---|
| Test Case ID | TC-006 |
| Related Requirement(s) | FR-001, NFR-004 |
| Title | Role Restriction - Volunteer Cannot Access Coordinator Features |
| Preconditions | The user is logged in as a Volunteer. |
| Test Steps | 1. Look for "Post an Opportunity" and "Attendance & Hours" in the navigation<br>2. Try to open the coordinator page directly (URL or menu) |
| Expected Result | The features are hidden or access is denied. No coordinator data shows. |
| Actual Result | As expected. Automated test TC006 passed. |
| Status | Pass |
| Priority | High |

## TC-007: Role Restriction - Coordinator Cannot Access Administrator Features

| Field | Detail |
|---|---|
| Test Case ID | TC-007 |
| Related Requirement(s) | FR-001, NFR-004 |
| Title | Role Restriction - Coordinator Cannot Access Administrator Features |
| Preconditions | The user is logged in as a Coordinator. |
| Test Steps | 1. Look for "Manage Coordinator Access" and "Program Overview" in the navigation<br>2. Try to open the administrator page directly |
| Expected Result | Access is denied. No administrator data shows. |
| Actual Result | As expected. Automated test TC007 passed. |
| Status | Pass |
| Priority | High |

## TC-008: Browse Opportunities - List Displays Details

| Field | Detail |
|---|---|
| Test Case ID | TC-008 |
| Related Requirement(s) | FR-002 |
| Title | Browse Opportunities - List Displays Details |
| Preconditions | The user is logged in as a Volunteer. At least three opportunities exist. |
| Test Steps | 1. Open the Browse Opportunities page |
| Expected Result | Each opportunity shows date, location, description, and remaining spaces. |
| Actual Result | As expected. Automated test TC008 passed. |
| Status | Pass |
| Priority | High |

## TC-009: Search Opportunities - Matching Term

| Field | Detail |
|---|---|
| Test Case ID | TC-009 |
| Related Requirement(s) | FR-002 |
| Title | Search Opportunities - Matching Term |
| Preconditions | The user is logged in as a Volunteer. An opportunity titled "Food Bank Sorting" exists. |
| Test Steps | 1. Open Browse Opportunities<br>2. Enter "Food Bank" in the search field |
| Expected Result | Only matching opportunities show, including "Food Bank Sorting". |
| Actual Result | As expected. Automated test TC009 passed. |
| Status | Pass |
| Priority | High |

## TC-010: Search Opportunities - No Matches

| Field | Detail |
|---|---|
| Test Case ID | TC-010 |
| Related Requirement(s) | FR-002 |
| Title | Search Opportunities - No Matches |
| Preconditions | The user is logged in as a Volunteer. |
| Test Steps | 1. Open Browse Opportunities<br>2. Enter a term that matches nothing (e.g., "zzzzqq") |
| Expected Result | A "no matches" message shows. The user can change the search or filters. |
| Actual Result | As expected. Automated test TC010 passed. |
| Status | Pass |
| Priority | Medium |

## TC-011: Filter Opportunities - By Category or Date

| Field | Detail |
|---|---|
| Test Case ID | TC-011 |
| Related Requirement(s) | FR-002 |
| Title | Filter Opportunities - By Category or Date |
| Preconditions | The user is logged in as a Volunteer. Opportunities exist in more than one category or date. |
| Test Steps | 1. Open Browse Opportunities<br>2. Select a filter value |
| Expected Result | The list shows only opportunities that match the filter. |
| Actual Result | As expected. Automated test TC011 passed. |
| Status | Pass |
| Priority | Medium |

## TC-012: View Opportunity Details

| Field | Detail |
|---|---|
| Test Case ID | TC-012 |
| Related Requirement(s) | FR-002 |
| Title | View Opportunity Details |
| Preconditions | The user is logged in as a Volunteer. Opportunities exist. |
| Test Steps | 1. Open Browse Opportunities<br>2. Select one opportunity |
| Expected Result | The Opportunity Details page shows title, description, date, time, location, capacity, and a Register button. |
| Actual Result | As expected. Automated test TC012 passed. |
| Status | Pass |
| Priority | Medium |

## TC-013: Register for Opportunity - Space Available

| Field | Detail |
|---|---|
| Test Case ID | TC-013 |
| Related Requirement(s) | FR-003 |
| Title | Register for Opportunity - Space Available |
| Preconditions | Logged in as a Volunteer. The opportunity has open spaces. The volunteer is not registered. |
| Test Steps | 1. Open the opportunity details<br>2. Click "Register" |
| Expected Result | A confirmation shows. The opportunity appears in Upcoming Registrations. Remaining spaces fall by one. |
| Actual Result | As expected. Automated test TC013 passed. |
| Status | Pass |
| Priority | High |

## TC-014: Register for Opportunity - Duplicate Registration

| Field | Detail |
|---|---|
| Test Case ID | TC-014 |
| Related Requirement(s) | FR-003 |
| Title | Register for Opportunity - Duplicate Registration |
| Preconditions | Logged in as a Volunteer. The volunteer is already registered for the opportunity. |
| Test Steps | 1. Open the same opportunity details<br>2. Click "Register" |
| Expected Result | The system refuses and explains that the volunteer is already registered. No second record is created. |
| Actual Result | As expected. Automated test TC014 passed. |
| Status | Pass |
| Priority | High |

## TC-015: Register for Opportunity - Capacity Reached

| Field | Detail |
|---|---|
| Test Case ID | TC-015 |
| Related Requirement(s) | FR-003 |
| Title | Register for Opportunity - Capacity Reached |
| Preconditions | Logged in as a Volunteer. The opportunity has 0 remaining spaces. |
| Test Steps | 1. Open the opportunity details<br>2. Click "Register" |
| Expected Result | The system refuses and explains that the opportunity is full. No registration is saved. |
| Actual Result | As expected. Automated test TC015 passed. |
| Status | Pass |
| Priority | High |

## TC-016: Register for Opportunity - Last Remaining Space

| Field | Detail |
|---|---|
| Test Case ID | TC-016 |
| Related Requirement(s) | FR-003 |
| Title | Register for Opportunity - Last Remaining Space |
| Preconditions | Logged in as a Volunteer. The opportunity has exactly 1 remaining space. |
| Test Steps | 1. Register for the opportunity<br>2. Log in as a second volunteer and try to register |
| Expected Result | The first volunteer registers. Remaining spaces show 0. The second volunteer is refused. |
| Actual Result | As expected. Automated test TC016 passed. |
| Status | Pass |
| Priority | Medium |

## TC-017: Withdraw - More Than Three Days Before Event

| Field | Detail |
|---|---|
| Test Case ID | TC-017 |
| Related Requirement(s) | FR-004 |
| Title | Withdraw - More Than Three Days Before Event |
| Preconditions | Logged in as a Volunteer. The volunteer is registered for an event 7 days away. |
| Test Steps | 1. Open My Volunteering<br>2. Select the opportunity and click "Withdraw"<br>3. Confirm |
| Expected Result | The registration is removed. A confirmation shows. The space is available again. |
| Actual Result | As expected. Automated test TC017 passed. |
| Status | Pass |
| Priority | Medium |

## TC-018: Withdraw - Within Three Days of Event

| Field | Detail |
|---|---|
| Test Case ID | TC-018 |
| Related Requirement(s) | FR-004 |
| Title | Withdraw - Within Three Days of Event |
| Preconditions | Logged in as a Volunteer. The volunteer is registered for an event 2 days away. |
| Test Steps | 1. Open My Volunteering<br>2. Select the opportunity and click "Withdraw" |
| Expected Result | Withdrawal is blocked. The message explains the three-day cutoff. The registration stays. |
| Actual Result | As expected. Automated test TC018 passed. |
| Status | Pass |
| Priority | Medium |

## TC-019: Withdraw - Exactly Three Days Boundary

| Field | Detail |
|---|---|
| Test Case ID | TC-019 |
| Related Requirement(s) | FR-004 |
| Title | Withdraw - Exactly Three Days Boundary |
| Preconditions | Logged in as a Volunteer. The volunteer is registered for an event exactly 72 hours away. |
| Test Steps | 1. Open My Volunteering<br>2. Select the opportunity and click "Withdraw" |
| Expected Result | Withdrawal is blocked because the event is not more than three days away. |
| Actual Result | As expected. Automated test TC019 passed. |
| Status | Pass |
| Priority | Low |

## TC-020: My Volunteering - Upcoming and History

| Field | Detail |
|---|---|
| Test Case ID | TC-020 |
| Related Requirement(s) | FR-005 |
| Title | My Volunteering - Upcoming and History |
| Preconditions | Logged in as a Volunteer. The volunteer has upcoming and past registrations. |
| Test Steps | 1. Open My Volunteering |
| Expected Result | Upcoming registrations and past participation show in separate, clear sections. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-021: My Volunteering - Hours Show After Coordinator Records Them

| Field | Detail |
|---|---|
| Test Case ID | TC-021 |
| Related Requirement(s) | FR-005, FR-007 |
| Title | My Volunteering - Hours Show After Coordinator Records Them |
| Preconditions | A coordinator recorded 3 hours for the volunteer. Logged in as that Volunteer. |
| Test Steps | 1. Open My Volunteering<br>2. View participation history |
| Expected Result | The history shows the opportunity as attended with 3 completed hours. The total hours include 3. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-022: My Volunteering - No Records

| Field | Detail |
|---|---|
| Test Case ID | TC-022 |
| Related Requirement(s) | FR-005 |
| Title | My Volunteering - No Records |
| Preconditions | Logged in as a new Volunteer with no registrations. |
| Test Steps | 1. Open My Volunteering |
| Expected Result | A helpful message shows that there are no records. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Low |

## TC-023: Create Opportunity - Valid Inputs

| Field | Detail |
|---|---|
| Test Case ID | TC-023 |
| Related Requirement(s) | FR-006 |
| Title | Create Opportunity - Valid Inputs |
| Preconditions | Logged in as a Coordinator. |
| Test Steps | 1. Open Post an Opportunity<br>2. Enter title, description, future date, time, location, and capacity 10<br>3. Click "Post" |
| Expected Result | The opportunity saves. It appears in the list that volunteers see. |
| Actual Result | As expected. Automated test TC023 passed. |
| Status | Pass |
| Priority | High |

## TC-024: Create Opportunity - Missing Required Fields

| Field | Detail |
|---|---|
| Test Case ID | TC-024 |
| Related Requirement(s) | FR-006, NFR-006 |
| Title | Create Opportunity - Missing Required Fields |
| Preconditions | Logged in as a Coordinator. |
| Test Steps | 1. Open Post an Opportunity<br>2. Leave the title empty and fill other fields<br>3. Click "Post" |
| Expected Result | The system explains the missing field. Nothing is saved. |
| Actual Result | As expected. Automated test TC024 passed. |
| Status | Pass |
| Priority | High |

## TC-025: Create Opportunity - Invalid Date or Capacity

| Field | Detail |
|---|---|
| Test Case ID | TC-025 |
| Related Requirement(s) | FR-006, NFR-006 |
| Title | Create Opportunity - Invalid Date or Capacity |
| Preconditions | Logged in as a Coordinator. |
| Test Steps | 1. Open Post an Opportunity<br>2. Enter a past date or a capacity of 0 or a negative number<br>3. Click "Post" |
| Expected Result | The system rejects the input with a clear message. Nothing is saved. |
| Actual Result | As expected. Automated test TC025 passed. |
| Status | Pass |
| Priority | Medium |

## TC-026: Edit Opportunity - Valid Change

| Field | Detail |
|---|---|
| Test Case ID | TC-026 |
| Related Requirement(s) | FR-006 |
| Title | Edit Opportunity - Valid Change |
| Preconditions | Logged in as a Coordinator. An opportunity exists. |
| Test Steps | 1. Open the opportunity for editing<br>2. Change the description<br>3. Click "Save" |
| Expected Result | The change saves and shows in the opportunity list. |
| Actual Result | As expected. Automated test TC026 passed. |
| Status | Pass |
| Priority | Medium |

## TC-027: Cancel Opportunity - Registered Volunteers Notified

| Field | Detail |
|---|---|
| Test Case ID | TC-027 |
| Related Requirement(s) | FR-006, FR-009 |
| Title | Cancel Opportunity - Registered Volunteers Notified |
| Preconditions | Logged in as a Coordinator. An opportunity has two registered volunteers. |
| Test Steps | 1. Open the opportunity<br>2. Click "Cancel Opportunity" and confirm |
| Expected Result | The status changes to cancelled. Both volunteers receive a notification that names the opportunity. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | High |

## TC-028: View Registrations for an Opportunity

| Field | Detail |
|---|---|
| Test Case ID | TC-028 |
| Related Requirement(s) | FR-007 |
| Title | View Registrations for an Opportunity |
| Preconditions | Logged in as a Coordinator. An opportunity has registrations. |
| Test Steps | 1. Select the opportunity<br>2. Open its registration list |
| Expected Result | The registered volunteers and the number of available places show. No data changes. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-029: Record Attendance and Hours - Valid

| Field | Detail |
|---|---|
| Test Case ID | TC-029 |
| Related Requirement(s) | FR-007 |
| Title | Record Attendance and Hours - Valid |
| Preconditions | Logged in as a Coordinator. The event date has passed. Volunteers are registered. |
| Test Steps | 1. Open the registration list<br>2. Mark one volunteer as attended<br>3. Enter 3 hours<br>4. Click "Save" |
| Expected Result | Attendance and hours save. They appear in the volunteer's participation history. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-030: Record Hours - Invalid Hours

| Field | Detail |
|---|---|
| Test Case ID | TC-030 |
| Related Requirement(s) | FR-007, NFR-006 |
| Title | Record Hours - Invalid Hours |
| Preconditions | Logged in as a Coordinator. The event date has passed. |
| Test Steps | 1. Open the registration list<br>2. Mark a volunteer as attended<br>3. Enter -2, or a letter, or leave empty<br>4. Click "Save" |
| Expected Result | The system asks for a correction. Nothing is saved. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-031: Grant Coordinator Access

| Field | Detail |
|---|---|
| Test Case ID | TC-031 |
| Related Requirement(s) | FR-008 |
| Title | Grant Coordinator Access |
| Preconditions | Logged in as an Administrator. A volunteer account exists. |
| Test Steps | 1. Open the user management page<br>2. Select the volunteer<br>3. Grant coordinator access and save |
| Expected Result | The change saves. After the next login, the user sees coordinator features. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-032: Remove Coordinator Access

| Field | Detail |
|---|---|
| Test Case ID | TC-032 |
| Related Requirement(s) | FR-008 |
| Title | Remove Coordinator Access |
| Preconditions | Logged in as an Administrator. A coordinator account exists. |
| Test Steps | 1. Open the user management page<br>2. Select the coordinator<br>3. Remove coordinator access and save |
| Expected Result | The change saves. The user no longer sees coordinator features. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-033: Review Program Summaries

| Field | Detail |
|---|---|
| Test Case ID | TC-033 |
| Related Requirement(s) | FR-008 |
| Title | Review Program Summaries |
| Preconditions | Logged in as an Administrator. Opportunities, registrations, and hours exist. |
| Test Steps | 1. Open the Program Overview page |
| Expected Result | The summary shows counts of opportunities, registrations, attendance, and total hours. No data changes. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-034: Program Summaries - No Data For Period

| Field | Detail |
|---|---|
| Test Case ID | TC-034 |
| Related Requirement(s) | FR-008 |
| Title | Program Summaries - No Data For Period |
| Preconditions | Logged in as an Administrator. No data exists for the selected period. |
| Test Steps | 1. Open Program Overview<br>2. Select a period with no activity |
| Expected Result | A clear "no information" message shows. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Low |

## TC-035: Notification - Opportunity Time or Location Changed

| Field | Detail |
|---|---|
| Test Case ID | TC-035 |
| Related Requirement(s) | FR-009 |
| Title | Notification - Opportunity Time or Location Changed |
| Preconditions | A volunteer is registered. Logged in as the Coordinator. |
| Test Steps | 1. Edit the opportunity time or location<br>2. Save<br>3. Log in as the registered volunteer and open notifications |
| Expected Result | The volunteer has a notification that names the opportunity and states what changed. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-036: Notification - Unregistered Volunteer Not Notified

| Field | Detail |
|---|---|
| Test Case ID | TC-036 |
| Related Requirement(s) | FR-009 |
| Title | Notification - Unregistered Volunteer Not Notified |
| Preconditions | A second volunteer is not registered for the opportunity. |
| Test Steps | 1. Edit the opportunity date and save<br>2. Log in as the unregistered volunteer |
| Expected Result | The unregistered volunteer receives no notification. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Low |

## TC-037: Performance - Lists Load Within Two Seconds

| Field | Detail |
|---|---|
| Test Case ID | TC-037 |
| Related Requirement(s) | NFR-001 |
| Title | Performance - Lists Load Within Two Seconds |
| Preconditions | Database holds hundreds of opportunities and thousands of records. |
| Test Steps | 1. Open Browse Opportunities<br>2. Open My Volunteering<br>3. Measure the time to display each page |
| Expected Result | Each page displays within two seconds. |
| Actual Result | (To be filled after test execution) |
| Status | (Pass/Fail/Blocked) |
| Priority | Medium |

## TC-038: Reliability - No Duplicate Records On Rapid Double Click

| Field | Detail |
|---|---|
| Test Case ID | TC-038 |
| Related Requirement(s) | NFR-003 |
| Title | Reliability - No Duplicate Records On Rapid Double Click |
| Preconditions | Logged in as a Volunteer. The opportunity has open spaces. |
| Test Steps | 1. Double-click "Register" quickly<br>2. Open My Volunteering |
| Expected Result | Only one registration exists. Remaining spaces fall by one only. |
| Actual Result | As expected. Automated test TC038 passed. |
| Status | Pass |
| Priority | High |
