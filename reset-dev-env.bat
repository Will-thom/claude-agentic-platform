@echo off

echo Stopping running Spring Boot processes...
taskkill /F /IM java.exe >nul 2>&1

echo Stopping Docker containers...
docker compose down

echo Cleaning Maven build...
cd app
call mvn clean >nul 2>&1
cd ..

echo Dev environment reset complete.