FROM mysql:latest

MAINTAINER total-hub-site-mysql

COPY init.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD=root

VOLUME /total-hub-site-mysql-data

EXPOSE 3306