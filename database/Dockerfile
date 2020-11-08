FROM library/postgres

ENV POSTGRES_USER docker
ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB docker

COPY database-script/CreateFirst.sql /docker-entrypoint-initdb.d/

EXPOSE 5432