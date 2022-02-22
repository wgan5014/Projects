# Cinema Booking System
## About
<p>This program simulates the functionality of a Cinema booking system. This is including but not limited to purchases<br>at varying cinemas with screen sizes, and viewing the movies available.</p>

## Installing the program
- Install Java 11.0.0 or later
- Download the cinema.jar [here](https://github.sydney.edu.au/SOFT2412-2021S2/w14_c3_g4_asm2/raw/master/cinema.jar)
- Place in suitable folder
- Open to run!

## Using the Program (user)
Users have the option to
- Create an account
- Sign in with the account they created, or as a guest user into the home page
- View movies available in system
- Select movies for purchase, in addition to the number of tickets and the screen size (Gold, Silver, Bronze sizes)
- Sign in with the account they created to purchase a movie
- Purchase tickets for a movie
- Login as a staff member and/or manager, who is able to add and edit movies, showings, gift cards, staff accounts, and gain access to transaction and movie summaries.

## Testing the Program
<p>Regression testing was done with JUnit, using a CI/CD pipeline via Jenkins and Gradle.<br>This automatically tested the software with every change made to it (software)</p>

The program can be tested using this set of gradle commands. These commands show the status of which tests have been passed
- ```Gradle clean build```
- ```Gradle test```

For a further summary, the jacoco report can be accessed through *build/reports/jacoco/test/html/index.html*

## Contribute/collaborate to the program
<p>The workflow consisted of each developer pushing their changes to git into the remote repository https://github.sydney.edu.au/SOFT2412-2021S2/w14_c3_g4_asm2/. The CI/CD pipeline was then run via Jenkins to determine if the new changes could result in the software being successfully run or not.
  <br>
  Developers were able to review each other's changes from pull requests,<br>and then merge said changes from the request.</p>

## Contributors
<p>This project could not have been possible without the help of some excellent programmers. Below lists the developers and their contributions</p>

 - William Gan (developer)
    - Manager transaction screen, and backend processing logic
    - Customer login screen
    - CSV file maintaining information on tickets booked by customers
    - Set up CI/CD pipeline tools on AWS virtual machine
 
 - Nafi Robayat (developer)
    - Lower-level I/O parsing and file-handling (JSON, CSV, text file types). 
      - This formed the basis of how our system fetched and retrieved information
    - Purchase, and transaction screen templates
    - Idle timer in guest and customer transaction process
 
 - Ian Chen(developer)
    - Home screen display and processing for customer/guest login (displaying tables, hovering over icons etc.)
    - Movie screen
 
 - Ishan Zuaim (scrum master)
    - Ensure agile development practices were followed
    - Staff transaction screen
    - Gift card screen - display and features
    - Movie purchase screen for guests and existing customers
 
 - Melissa Nguyen (developer)
    - Create GUI template files for all classes used in the entire project
    - Pop up screen
    - Movie screen actions

<p>The above duties do not represent the entire contributions of any of the members involved, they are an approximation. In addition to the responsibilities listed, each member wrote tests at the unit and integration level for the software, and performed copious amounts of bug-fixing.</p>
