#!/bin/sh
set -e

# Render (and other platforms) provide a single Postgres connection URI via
# DATABASE_URL, e.g. postgresql://user:password@host:port/database.
# Spring needs a jdbc:postgresql://host:port/database URL plus separate
# username/password, so translate it here before the app starts. Local
# docker-compose sets SPRING_DATASOURCE_* directly and has no DATABASE_URL,
# so it skips this and falls through unchanged.
if [ -n "$DATABASE_URL" ]; then
  rest="${DATABASE_URL#postgres*://}"
  creds="${rest%%@*}"
  hostpart="${rest#*@}"

  db_user="${creds%%:*}"
  db_pass="${creds#*:}"

  hostport="${hostpart%%/*}"
  db_name="${hostpart#*/}"

  db_host="${hostport%%:*}"
  db_port="${hostport#*:}"

  export SPRING_DATASOURCE_URL="jdbc:postgresql://${db_host}:${db_port}/${db_name}"
  export SPRING_DATASOURCE_USERNAME="${db_user}"
  export SPRING_DATASOURCE_PASSWORD="${db_pass}"
fi

exec java -jar app.jar
