FROM centos

RUN yum install -y java-1.8.0-openjdk-devel

ENV JAVA_HOME=/usr/lib/jvm/java

ADD build/payara-micro-4.1.1.162.jar /opt/sealion/payara-micro.jar
ADD build/libs/sealion.war /opt/sealion/sealion.war

WORKDIR /opt/sealion

ENTRYPOINT ["java", "-Ddb.url=jdbc:h2:file:/var/sealion", "-jar", "/opt/sealion/payara-micro.jar", "--noCluster", "--deploy", "sealion.war"]
