@echo off
REM ========================================
REM HCL Restaurant App - Command Prompt Setup & Run
REM ========================================

echo.
echo ========================================
echo HCL Restaurant App - Setup & Run
echo ========================================
echo.

REM Defaults use the embedded H2 database so the app runs from CMD without MySQL.
set DB_URL=jdbc:h2:mem:springbeans;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1
set DB_USERNAME=sa
set DB_PASSWORD=
set JPA_DDL_AUTO=update
set CORS_ALLOWED_ORIGIN_PATTERNS=http://localhost:5173,http://localhost:3000
set MAIL_HOST=smtp.gmail.com
set MAIL_PORT=587
set MAIL_USERNAME=
set MAIL_PASSWORD=
set MAIL_FROM=no-reply@springbeans.local

echo Setting environment variables...
echo DB_URL: %DB_URL%
echo CORS_ALLOWED_ORIGIN_PATTERNS: %CORS_ALLOWED_ORIGIN_PATTERNS%
echo.

echo.
echo ========================================
echo Starting backend and frontend in separate CMD windows...
echo ========================================
echo.

start "HCL Backend - http://localhost:8080" cmd /k "cd /d ""%~dp0hcl-backend"" && mvn spring-boot:run"
start "HCL Frontend - http://localhost:5173" cmd /k "cd /d ""%~dp0hcl-frontend"" && if not exist node_modules npm.cmd install && npm.cmd run dev"

echo Backend:  http://localhost:8080
echo Frontend: http://localhost:5173
echo.
echo Login seed users:
echo   admin@springbeans.com / Admin@123
echo   user@springbeans.com  / User@123
echo.

pause
