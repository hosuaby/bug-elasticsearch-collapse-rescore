FROM elasticsearch:6.5.4
COPY build/distributions/post-rescore-plugin-*.zip /post-rescore-plugin.zip
RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch "file:///post-rescore-plugin.zip"
VOLUME /usr/share/elasticsearch/data