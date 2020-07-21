FROM elasticsearch:6.5.4
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch \
"https://github.com/adelean/elasticsearch-learning-to-rank/raw/es_6_5_4/ltr-1.1.1-es6.5.4.zip"

COPY build/distributions/post-rescore-plugin-*.zip /post-rescore-plugin.zip
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch "file:///post-rescore-plugin.zip"

VOLUME /usr/share/elasticsearch/data
