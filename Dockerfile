FROM adoptopenjdk/openjdk11:alpine-slim 

RUN addgroup worker && adduser -G worker -S worker
USER worker
WORKDIR /home/worker

RUN mkdir -p .ssh && touch .ssh/known_hosts
COPY --chown=worker ./target/uberjar/cloj-first-project-0.1.0-SNAPSHOT-standalone.jar cloj-first-project-standalone.jar

CMD [ "java", \
      "-jar", "cloj-first-project-standalone.jar" ]