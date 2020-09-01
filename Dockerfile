# 目录路径都是相对于 Dockerfile 文件的

# 基于 jdk8
FROM adoptopenjdk/openjdk8-openj9:alpine-slim

# 文件创建维护者
MAINTAINER tinychest@foxmail.com

# ？
#RUN mkdir -p /mine
CMD ["--spring.profiles.active=dev", "--server.port=8090"]

# ？
EXPOSE 8090

# ？
#WORKDIR /mine

# ？
#ADD ./target/mine-0.0.1-SNAPSHOT.jar ./app.jar
COPY *.jar /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
