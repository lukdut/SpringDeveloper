FROM maven:3.5.3-jdk-8

ENV PROJ_DIR=/opt/proj
RUN mkdir -p $PROJ_DIR
WORKDIR $PROJ_DIR


COPY . $PROJ_DIR

RUN mvn clean install -DskipTests



FROM openjdk:8-jdk

ENV PROJ_DIR=/opt/proj
RUN mkdir -p $PROJ_DIR
WORKDIR $PROJ_DIR

COPY --from=0 $PROJ_DIR/NoSqlLibrary/target/nosqlLib.jar $PROJ_DIR/

EXPOSE 8080

CMD ["java", "-jar", "/opt/proj/nosqlLib.jar"]