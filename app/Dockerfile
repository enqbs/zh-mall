# 建议在 Linux 环境运行
# 由于 openjdk 不再提供成品镜像，选择 eclipse-jdk（基于openjdk）镜像
FROM eclipse-temurin:8-jre
LABEL authors="ENQBS"
COPY target/app-0.0.1.jar /app.jar

# 环境变量
# docker 中每个容器都是隔离的，端口直接设置8080
ENV TZ=Asia/Shanghai PORT=8080 JAVA_OPTS="-Xms320m -Xmx320m -Xmn160m"
CMD java -jar -Dserver.port=${PORT} ${JAVA_OPTS} /app.jar
