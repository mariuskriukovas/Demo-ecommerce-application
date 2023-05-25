FROM amazoncorretto:17

RUN yum install -y shadow-utils \
  && /usr/sbin/groupadd -r spring  \
  && /usr/sbin/adduser -r spring -g spring \
  && mkdir -p /data/files  \
  && chown spring:spring /data/files \
  && yum clean all

USER spring:spring

COPY target/dependency/BOOT-INF/classes /app
COPY target/dependency/BOOT-INF/lib /app/lib
COPY target/dependency/META-INF /app/META-INF

ENTRYPOINT ["java","-cp","app:app/lib/*","io.marius.demo.ecommerce.inventory.InventoryApplication"]