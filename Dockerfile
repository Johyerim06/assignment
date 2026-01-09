# ✅ 공식 권장 Java 17 이미지
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Maven 빌드 결과물 복사
COPY target/*.jar app.jar

# Spring Boot 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

