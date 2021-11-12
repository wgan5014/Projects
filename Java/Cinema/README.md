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
