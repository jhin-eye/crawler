FROM ubuntu:20.04

# 비대화형 모드 설정
ENV DEBIAN_FRONTEND=noninteractive

# 업데이트 후 필수 패키지 설치
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      openjdk-17-jdk \
      firefox \
      firefox-geckodriver \
      xvfb \
      libgtk-3-0 \
      libdbus-glib-1-2 \
      dbus-x11 \
      ca-certificates \
      wget \
      unzip \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# 어플리케이션 JAR 복사
COPY build/libs/crawlerCore.jar /app/crawlerCore.jar

# Xvfb를 백그라운드에서 띄운 후 Java 애플리케이션 실행
CMD ["sh", "-c", "Xvfb :99 -screen 0 1024x768x24 & java -jar /app/crawlerCore.jar"]
