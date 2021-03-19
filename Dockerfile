FROM hivemq/hivemq3:3.4.7

COPY target/hivemq-sub-plugin-demo-*.jar /opt/hivemq/plugins/
COPY src/main/resources/myPlugin.properties /conf

EXPOSE 8080
EXPOSE 1883
# Plugin port
EXPOSE 9362
# docker build -t hivemq .
# docker run -p 8080:8080 -p 1883:1883 -p 9362:9362 hivemq
