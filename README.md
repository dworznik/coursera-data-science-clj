# coursera-data-science-clj


"Twitter Sentiment Analysis" assignment implemented in Clojure.

## Usage

Save Twitter credentials in the .oauth.yml file

    consumer-key: xxxx
    consumer-secret: xxxx
    access-token: xxxx
    access-token-secret: xxx

To dump Twitter live stream, run:

    lein run -m coursera_data_science_clj.twitter <num of seconds to run> <output filename>

To generate tweets sentiment scores, run:

    lein run -m coursera_data_science_clj.core tweet-sentiments <sentiment dictionary filename> <stream dump filename> <output filename>

To calculate sentiment scores for new terms, run:

    lein run -m coursera_data_science_clj.core term-sentiments <sentiment dictionary filename> <stream dump filename> <output filename>




## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
