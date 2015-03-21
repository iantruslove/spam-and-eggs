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

* Person info generator
  * <strike>API</strike>
  * <strike>Front end layout</strike>
  * <strike>AJAX and refresh button</strike>
* Email structure generator
  * API
  * <strike>Web app front end</strike>
  * AJAX and refresh button
* <strike>Token frequency analyzer to generate Markov chain transition frequencies</strike>
* <strike>Externalize Markov model data (into binary files?)</strike>
* More realistic paragraph and sentence distributions
* More funner models
* Expose model generation parameters in front end
* Only serve analytics in deployment environment, not in dev
* CORS
* Infinite scroll instead of refresh buttons - perhaps with http://www.infinite-scroll.com/
* Don't just have a huge bunch of functions for the Markov generator.
* Page-by-page analytics
  * Links:
    * https://developers.google.com/analytics/devguides/collection/analyticsjs/
    * https://hacks.mozilla.org/2015/03/an-analytics-primer-for-developers/
* Drop a release. Start and publish a CHANGELOG (perhaps with https://github.com/evilstreak/markdown-js)
* Show build and version info in footer
* Reply and Forward simulation
* Simulate different MUAs

## License

Copyright © 2015 Ian Truslove

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
