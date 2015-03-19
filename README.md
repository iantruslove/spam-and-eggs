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
  * AJAX init and refresh - perhaps with http://www.infinite-scroll.com/
* Email structure generator
  * API
  * <strike>Web app front end</strike>
  * AJAX
* CORS
* Page-by-page analytics (https://developers.google.com/analytics/devguides/collection/analyticsjs/)
* Drop a release. Start and publish a CHANGELOG (perhaps with https://github.com/evilstreak/markdown-js)
* HTML5 templating:
  * Only serve analytics in prod
  * Show build and version info in footer
* Reply and Forward simulation
* Token frequency analyzer to generate Markov chain transition frequencies
* Externalize Markov model data (into binary files?)
* Email client simulation

## License

Copyright © 2015 Ian Truslove

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
