@echo off
echo Starting infrastructure...

REM Try 'docker compose' (newer syntax)
docker compose up -d
if %errorlevel% equ 0 goto :success

REM Fallback to 'docker-compose' (older syntax)
echo 'docker compose' failed, trying 'docker-compose'...
docker-compose up -d
if %errorlevel% equ 0 goto :success

echo.
echo Error: Could not start Docker containers.
echo Please ensure Docker Desktop is installed and running.
echo You can download it from: https://www.docker.com/products/docker-desktop/
pause
exit /b 1

:success
echo.
echo Infrastructure started successfully!
pause
