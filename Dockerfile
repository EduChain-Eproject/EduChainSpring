# Sử dụng OpenJDK 17
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy JAR file vào container
COPY target/educhain-0.0.1-SNAPSHOT.war app.war

# Tạo thư mục upload
RUN mkdir -p /app/static/uploads && mkdir -p /app/static/video

# Expose cổng 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.war"]