# lattice
Login Activity with Room db and retrofit
Made using Retrofit API for making GET and POST request and Room Persistence Library for storing and retrieving data locally

For signing up of the user we check all the constrains for every data field and then only let the user signup. 
By click on signup button the details of the user are saved into local database (Room Persistence Library) and also sent to the server using Retrofit.
The new activity that opens up after the signup contains the list of all the signed-up users which if fetched from the server if there is network else it is retrieved from Room Persistence Library.

Postman link: getpostman.com/collections/b7de84e5177d34181b47

![screen](https://user-images.githubusercontent.com/20511163/41386702-6d9efd74-6fa0-11e8-829f-8a96fb9acb07.png)
