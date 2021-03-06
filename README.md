# spam-and-eggs

_Serving up tasty, tasty, fake emails._

Markov chains are a fun little toy you can use to generate text that
is complete gibberish, but kinda looks real. Using this, and some
other data generation based on somewhat real-world statistics of
emails, people, and words, you can generate emails.

The app is running at
[https://spam-and-eggs.herokuapp.com](https://spam-and-eggs.herokuapp.com).

## Usage

Start the server locally:

    lein run 8080

Deploy to Heroku:

    heroku login
    git push heroku master

Running the text file analyzer / Markov model builder:

    lein analyze <filename>

For example, this will create a `license` model that you can
immediately use to generate license-esque email text:

    lein analyze LICENSE > resources/models/license.edn

## TODO

* Person generator
  * <strike>API</strike>
  * <strike>Front end layout</strike>
  * <strike>AJAX and refresh button</strike>
* Email generator
  * <strike>API</strike>
  * <strike>Web app front end</strike>
  * <strike>AJAX and refresh button</strike>
  * <strike>Token frequency analyzer to generate Markov chain transition frequencies</strike>
  * <strike>Externalize Markov model data (into binary files?)</strike>
  * More realistic paragraph and sentence distributions
  * More funner models
  * Don't just have a huge bunch of functions for the Markov generator.
  * Reply and Forward simulation
  * Simulate different MUAs
* Web app
  * <strike>Only serve analytics in deployment environment, not in dev</strike>
  * <strike>Page-by-page analytics</strike>
  * Back button navigation
  * Expose model generation parameters in front end
  * CORS
  * Infinite scroll instead of refresh buttons - perhaps with http://www.infinite-scroll.com/
  * Show build and version info in footer
* New Features
  * Front-page visualization of Markov chain generation of text
  * User-submittable text for analyzing to create the Markov model
* Misc
  * Start and publish a CHANGELOG
  * Drop a first release. 

## License

Copyright © 2015 Ian Truslove

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
