# W2023 T1 Project: Monthly Spending Application

## Project Description

*A system to record monthly spending for person(s).*
The application will allow users to enter information about monthly revenues
and expenditures and their sources. Possible other features would include
viewing statistics and/or comparison of the monthly spendings.

This application can be used by various people, especially by individuals who would like to 
keep track of their spendings whilst having multiple bank accounts, different sources of income, 
are using different currencies, or are mostly using cash. This is a project of interest to me as 
I personally would like to have a system which would allow me to have records of my monthly spendings.

## User Stories:
- As a user, I want to be able to add an income/spending to a month and specify the category
- As a user, I want to be able to remove an income/spending to a month and specify the category
- As a user, I want to be able to view the list of monthly income and spending 
- As a user, I want to be able to select a month and view the income/spending summary
- As a user, I want to be able to compare my monthly spendings
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the "Add a new log" button in the homepage.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Remove existing log" button in the homepage.
- You can locate my visual component by running the application, in the welcome/start page.
- You can save the state of my application by pressing the "Save data" button at the bottom of the homepage before quitting the application.
- You can reload the state of my application by clicking the "Yes, load data" button at the welcome/start page.

## Phase 4: Task 2
Representative sample of event logs when the program runs:

Tue Nov 28 22:28:59 PST 2023
Loaded previous logs.

Tue Nov 28 22:28:59 PST 2023
Log: [ year = 2000, month = 12, type = income, category = salary, amount = $1000.00 ] was added.

Tue Nov 28 22:28:59 PST 2023
Log: [ year = 2000, month = 12, type = spending, category = grocery, amount = $200.00 ] was added.

Tue Nov 28 22:28:59 PST 2023
Log: [ year = 2000, month = 1, type = income, category = bonus, amount = $200.00 ] was added.

Tue Nov 28 22:28:59 PST 2023
Log: [ year = 2000, month = 12, type = income, category = salary, amount = $2000.00 ] was added.

Tue Nov 28 22:28:59 PST 2023
Displayed logs in (YYYY/MM): 2000 / 12

Tue Nov 28 22:28:59 PST 2023
Displayed logs in (YYYY/MM): 2000 / 1

Tue Nov 28 22:29:22 PST 2023
Log: [ year = 2000, month = 1, type = income, category = salary, amount = $6000.00 ] was added.

Tue Nov 28 22:29:22 PST 2023
Displayed logs in (YYYY/MM): 2000 / 12

Tue Nov 28 22:29:22 PST 2023
Displayed logs in (YYYY/MM): 2000 / 1

Tue Nov 28 22:29:33 PST 2023
Log: [ year = 2000, month = 1, type = income, category = bonus, amount = $200.00 ] was removed.

Tue Nov 28 22:29:33 PST 2023
Displayed logs in (YYYY/MM): 2000 / 12

Tue Nov 28 22:29:33 PST 2023
Displayed logs in (YYYY/MM): 2000 / 1

Tue Nov 28 22:29:36 PST 2023
Saved logs to file.


## Phase 4: Task 3
If I had more time to work on the project, I would try to improve my design by refactoring the AddOrRemoveLog in the 
ui.gui package and separating it into two separate classes: AddLog and RemoveLog as one class is currently doing the work of two. 
Another possible refactoring is to decrease the amount of duplicate codes (extracting the method and replace the clones 
with a call to the newly made method and passing in the right parameters).
