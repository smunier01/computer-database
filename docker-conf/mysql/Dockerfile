FROM mysql:5.6

# ENV MYSQL_DATABASE computer-database-db
ENV MYSQL_ROOT_PASSWORD root

COPY ./sqlscripts/* /docker-entrypoint-initdb.d/
