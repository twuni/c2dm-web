Summary
=======

This project is a barebones C2DM registration and messaging server built on the
[common-net-http](https://github.com/twuni/common-net-http) framework. It acts as
the intermediary server between your Android client application and the Google C2DM
API.


API Documentation
=================

Device Registration
-------------------

**POST** http://localhost:8080/register

Parameters:

 * `registration_id`: The C2DM registration ID received by the client from the Google C2DM API.
 * `device_id`: A unique ID for the client device being registered.

This is the API call that should be made from the device whenever a C2DM registration is received.
See [this step](http://code.google.com/android/c2dm/index.html#handling_reg) in the C2DM documentation.


User Registration
-----------------

**POST** http://localhost:8080/register

Parameters:

 * `user_id`: A unique ID for the user who has logged into your application.
 * `device_id`: A unique ID for the client device.

This is the API call that should be made from the device whenever the user has logged in or the
application is ready to send and receive C2DM messages. This basically "links" your application's
`user_id` to the C2DM `registration_id` so that you can send messages with just the `user_id`.
If you don't have user authentication, simply using the `device_id` will suffice.
If you have user authentication, consider setting this parameter to a session token, instead.


Sending a Message
-----------------

**POST** http://localhost:8080/message

Parameters:

 * `user_id`: The user ID of the client to which you want to send the message.
 * `content`: The content of the message you want to send.
 * `auth_token`: The Google ClientLogin authorization token for sending C2DM messages.

This is the API call you would make to send a message to a specific client.
An `auth_token` can be obtained via Google's ClientLogin API:

  curl https://www.google.com/accounts/ClientLogin -d Email=you@yourdomain.com -d Passwd=yourPassword -d accountType=HOSTED_OR_GOOGLE -d source=your.package.name -d service=ac2dm

The email you use for this request should be identical to the email you have authorized in your Android manifest.
See [Registering for C2DM](http://code.google.com/android/c2dm/index.html#registering) in the Android C2DM documentation.

**Note:** You must first [sign up](http://code.google.com/android/c2dm/signup.html) for C2DM access before being able to actually send messages this way.


Building
========

You can build the server by simply typing the following from the project's root directory:

  mvn clean package

Running
=======

To run the C2DM server, run the following:

  mvn exec:java -Dexec.mainClass=org.twuni.web.c2dm.C2DMServer
