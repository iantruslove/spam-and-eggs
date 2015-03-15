# spam-and-eggs

_Serving up tasty, tasty, fake emails._

Markov chains are a fun little toy you can use to generate text that
is complete gibberish, but kinda looks real. Using this, and some
other data generation based on somewhat real-world statistics of
emails, people, and words, you can generate emails.

The app will eventually be running at
[https://spam-and-eggs.herokuapp.com](https://spam-and-eggs.herokuapp.com).

## Usage

Start the server locally:

    lein run 8080

Deploy to Heroku:

    heroku login
    git push heroku master

## TODO

* Email structure generator
* Web app front end & REST API
* Reply and Forward simulation
* Token frequency analyzer to generate Markov chain transition frequencies
* Externalize Markov model data (into binary files?)
* Email client simulation

## License

Copyright © 2015 Ian Truslove

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
