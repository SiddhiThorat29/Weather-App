-- Create database and table for logging searches
CREATE DATABASE IF NOT EXISTS weather_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE weather_app;

CREATE TABLE IF NOT EXISTS search_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  city VARCHAR(255) NOT NULL,
  searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
