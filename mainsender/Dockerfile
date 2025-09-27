# Tomcat 10.x aur Java 17 (jo aapke pom.xml mein hai) ka base image use kar rahe hain.
# 'temurin' image lightweight aur secure hota hai.
FROM tomcat:10-jre17-temurin-focal

# Yeh step zaroori hai Render ko yeh batane ke liye ki WAR file kahan se milegi
# aur use kahan daalna hai.
#
# 1. 'target/mainsender.war' se file utha raha hai (jo Maven banayega).
# 2. Use Tomcat ke 'webapps' folder mein 'ROOT.war' naam se daal raha hai.
#    'ROOT.war' daalne se aapki application seedhe URL par chalegi, jaise:
#    https://[aapka-app-naam].onrender.com/ (bina /mainsender ke).
COPY target/mainsender.war /usr/local/tomcat/webapps/ROOT.war

# Tomcat image mein default ENTRYPOINT hota hai jo server ko start kar deta hai.
# Isliye aapko koi alag 'CMD' ki zaroorat nahi hai.
