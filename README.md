CCMFinalProject
===============
A final project for my CCM class CMP233- Data Structures

The goal of this program is to allow a user to create/read/update a private calendar with a list of events. These events have certain priority levels and due dates which can be used to sort the list events.  Since this is a web application, there is no main class. The classes are created/instantiated based on their scope. The first class that will be access by the user is the LoginController. This class� responsibility is to create users and to track the current user. Its boolean, isLoggedIn, is user to render certain page aspects. The next two classes which will be created are the Login and Registration validators. These RequestScoped classes intercept the the call to the login controller�s methods in order to first verify that the user has enter the proper values. The login controller checks to see if a user exists in the database while the registration controller makes sure the account isn�t already taken.  The next two classes, ListController and CalendarController, control the data (retrieving, updating, sorting) that is backing the two page UIComponets: the datatable and the calendar. Finally, the User and Event classes are used to store user and event data in a datebase. A user object contains a List of  all the Event objects that belong to them.
All of the classes so far have utilized the DatabaseService. They obtain an instance through class dependency injection. The DatabaseService interacts with the database through the Java Persistence Architecture. It uses pre-created parameterized queries to retrieve data from tables and map them to java entity classes.
