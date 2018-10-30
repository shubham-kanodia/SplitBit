# SplitBit
## Under Development
## About

SplitBit is an android app for splitting money between friends, stores data on a free online server. 
It allows user to add other users, among who the money is split. As soon as the transaction is added, other users get request to accept themseleves
as part of the split. On acceptance it is added to their records.

## Implementation

It uses HTTP Requests to send data, which is handled by PHP files on server. Then the data is processed and stored in an SQL database. These
requests happen in background and hence do not make the app unresponsive.
The user is first asked to register, or sign in. In case of registration username and password are added to online database. Once signed in
the user can click on SPLIT button to add transaction consisting of a group of users.
