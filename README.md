Weather Java - Spring Boot backend + Static frontend + MySQL schema
============================================================

What this ZIP contains
- backend/: Spring Boot Java backend (Maven) that proxies OpenWeatherMap current weather API,
  caches responses using Caffeine, and logs searches into a MySQL table `search_logs`.
- frontend/: Static HTML/CSS/JS frontend that calls the backend via `/api/weather?city=...`.
- db/schema.sql: MySQL schema to create database and the search_logs table.
- run-instructions.txt: How to configure & run locally, including MySQL workbench instructions.

Important
- You MUST add your OpenWeatherMap API key in backend/src/main/resources/application.properties
  (or as environment variable OPENWEATHER_KEY).
- Configure MySQL connection in application.properties (URL, username, password).
- This is a skeleton intended to run locally; review & secure before production use.

Ready? Follow run-instructions.txt after extracting.
