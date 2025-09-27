# --- STAGE 1: BUILD PHASE ---
FROM maven:3.8.7-jdk-17 AS builder

# Working directory set karein.
WORKDIR /app/mainsender

# Dependencies download karein.
# Yeh step humesha run nahi hoga agar pom.xml same rahe.
COPY mainsender/pom.xml .
RUN mvn dependency:go-offline

# Source code copy karein aur WAR file banayein.
COPY mainsender/src src
RUN mvn clean install -DskipTests

# --- STAGE 2: RUNTIME PHASE ---
# Tomcat 10.x aur Java 17 ka final image use karein.
FROM tomcat:10-jre17-temurin-focal

# Build stage se final WAR file ko copy karein.
# Kyunki humne 'mainsender' ko working directory set kiya tha, war file yahan milegi.
COPY --from=builder /app/mainsender/target/mainsender.war /usr/local/tomcat/webapps/ROOT.war

# Port expose karna zaroori hai (Render ise khud hi detect kar leta hai, but good practice)
EXPOSE 8080
