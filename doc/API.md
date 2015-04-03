# API

Spam and Eggs generated content - both email and persona data - is
available through this API.

## `/api/emails`

### `GET /api/emails`

Generates and returns randomly-generated emails
(currently only one email returned per call).

#### Supported Content Types

* `application/json`

#### Request Headers

None

#### Query Parameters

None

#### Response

* Object
  * `num-emails` (integer)
    * Number of emails in the `emails` array
  * `emails` (array of objects)
    * `from` (string)
    * `to` (string)
    * `subject` (string)
    * `date` (string, ISO date)
    * `message-id` (string)
    * `body` (string)

#### Curl Sample

    curl -v http://localhost:8080/api/emails
    
    > GET /api/emails HTTP/1.1
    > User-Agent: curl/7.35.0
    > Host: localhost:8080
    > Accept: */*
    > 
    < HTTP/1.1 200 OK
    < Date: Fri, 03 Apr 2015 00:37:33 GMT
    < Content-Type: application/json; charset=utf-8
    < Content-Length: 2658
    < Server: Jetty(7.6.13.v20130916)
    < 
    {
      "num-emails" : 1,
      "emails" : [ {
        "from" : "Deborah Adams <deborah.adams@comcast.net>",
        "to" : "Tammy Thomas <tammy.thomas@hotmail.co.uk>",
        "subject" : "Consumers only concerned with tier without loss",
        "date" : "2015-04-03T00:37:33Z",
        "message-id" : "9f2639cd-0c3d-4d9d-9ebd-11cda7378147",
        "body" : "This type that the most recent message <snip...> has a top-level project."
      } ]
    }

## `/api/email-addresses`

### `GET /api/email-addresses`

Retrieves a number of freshly-generated user personas

#### Supported Content Types

* `application/json`

#### Request Headers

None

#### Query Parameters

* `n` (integer, > 0)
  * Number of email addresses to return.
    * Server compliance with the requested value is not guaranteed.

#### Response

* Object
  * `num-addresses` (integer)
    * Number of email addresses in the `addresses` array
  * `addresses` (array of objects)
    * `first-name` (string)
    * `last-name` (string)
    * `email-address` (string)
      * Display-ready representation of the email address. May or may
        not be formatted with a display name, with quotes, angle
        brackets around the email address, or just a raw email
        address. Have fun!

#### Curl Sample

    curl -v http://localhost:8080/api/email-addresses?n=2
    
    > GET /api/email-addresses?n=2 HTTP/1.1
    > User-Agent: curl/7.35.0
    > Host: localhost:8080
    > Accept: */*
    > 
    < HTTP/1.1 200 OK
    < Date: Fri, 03 Apr 2015 00:49:04 GMT
    < Content-Type: application/json; charset=utf-8
    < Content-Length: 301
    < Server: Jetty(7.6.13.v20130916)
    < 
    {
      "num-addresses" : 2,
      "addresses" : [ {
        "first-name" : "Dorothy",
        "last-name" : "Nelson",
        "email-address" : "Dorothy Nelson <dorothy.nelson@gmx.com>"
      }, {
        "first-name" : "Daniel",
        "last-name" : "Hill",
        "email-address" : "Daniel Hill <daniel.hill@hotmail.co.uk>"
      } ]
    }

## `/api/_status`

### `GET /api/_status`

For checking whether the smoke's still inside.

#### Supported Content Types

* `application/json`

#### Response

* Object
  * `status` (string)
    * Can be `ok`

#### Curl Sample

    curl -v localhost:8080/api/_status
    
    > GET /api/_status HTTP/1.1
    > User-Agent: curl/7.35.0
    > Host: localhost:8080
    > Accept: */*
    > 
    < HTTP/1.1 200 OK
    < Date: Thu, 02 Apr 2015 21:52:44 GMT
    < Content-Type: application/json; charset=utf-8
    < Content-Length: 22
    < Server: Jetty(7.6.13.v20130916)
    < 
    {
      "status" : "ok"
    }
