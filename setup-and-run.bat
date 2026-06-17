@echo off
REM ========================================
REM HCL Restaurant App - Automated Setup & Run
REM ========================================

echo.
echo ========================================
echo HCL Backend - Database Setup & Run
echo ========================================
echo.

REM Set environment variables
set DB_URL=jdbc:mysql://localhost:3306/hcl
set CORS_ALLOWED_ORIGIN_PATTERNS=http://localhost:5173,http://localhost:3000
set MAIL_HOST=smtp.gmail.com
set MAIL_PORT=587
set MAIL_USERNAME=your-email@gmail.com
set MAIL_PASSWORD=your-app-password
set MAIL_FROM=your-email@gmail.com

echo Setting environment variables...
echo DB_URL: %DB_URL%
echo CORS_ALLOWED_ORIGIN_PATTERNS: %CORS_ALLOWED_ORIGIN_PATTERNS%
echo.

REM Check if MySQL is running
echo Checking MySQL connection...
mysql -h localhost -u root -proot -e "SELECT 1" >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo [WARNING] MySQL is not running or credentials are incorrect!
    echo Please ensure MySQL is running with user 'root' and password 'root'
    echo.
    pause
    exit /b 1
)

echo [OK] MySQL is running
echo.

REM Create database if not exists
echo Creating database 'hcl' if it doesn't exist...
mysql -h localhost -u root -proot -e "CREATE DATABASE IF NOT EXISTS hcl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
if %errorlevel% equ 0 (
    echo [OK] Database created/verified
) else (
    echo [ERROR] Failed to create database
    pause
    exit /b 1
)
echo.

REM Navigate to backend directory
cd /d "%~dp0hcl-backend"
if %errorlevel% neq 0 (
    echo [ERROR] Failed to navigate to backend directory
    pause
    exit /b 1
)

echo.
echo ========================================
echo Building and starting backend...
echo ========================================
echo.

REM Run Maven to build and start the application
call mvnw.cmd clean spring-boot:run -Dspring-boot.run.arguments="--DB_URL=%DB_URL% --CORS_ALLOWED_ORIGIN_PATTERNS=%CORS_ALLOWED_ORIGIN_PATTERNS%"

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Failed to start backend application
    pause
    exit /b 1
)

pause
