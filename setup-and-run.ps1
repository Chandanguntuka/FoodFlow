#!/usr/bin/env powershell
<#
.SYNOPSIS
    HCL Restaurant App - Automated Database Setup & Application Run
.DESCRIPTION
    Automatically initializes the database and starts both backend and frontend
#>

param(
    [switch]$SkipMySQLCheck = $false,
    [switch]$FrontendOnly = $false,
    [switch]$BackendOnly = $false
)

$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendPath = Join-Path $scriptPath "hcl-backend"
$frontendPath = Join-Path $scriptPath "hcl-frontend"

# Color output
function Write-Success { Write-Host $args -ForegroundColor Green }
function Write-Error-Custom { Write-Host $args -ForegroundColor Red }
function Write-Warning-Custom { Write-Host $args -ForegroundColor Yellow }
function Write-Info { Write-Host $args -ForegroundColor Cyan }

Write-Info "========================================"
Write-Info "HCL Restaurant App - Automated Setup"
Write-Info "========================================"

# Environment Variables
$env:DB_URL = "jdbc:mysql://localhost:3306/hcl"
$env:CORS_ALLOWED_ORIGIN_PATTERNS = "http://localhost:5173,http://localhost:3000"
$env:MAIL_HOST = "smtp.gmail.com"
$env:MAIL_PORT = "587"
$env:MAIL_USERNAME = "your-email@gmail.com"
$env:MAIL_PASSWORD = "your-app-password"
$env:MAIL_FROM = "your-email@gmail.com"

Write-Info "Setting environment variables..."
Write-Info "DB_URL: $env:DB_URL"
Write-Info "CORS_ALLOWED_ORIGIN_PATTERNS: $env:CORS_ALLOWED_ORIGIN_PATTERNS"
Write-Host ""

# Check MySQL connection (unless skipped)
if (-not $SkipMySQLCheck -and -not $FrontendOnly) {
    Write-Info "Checking MySQL connection..."
    try {
        $mysqlTest = mysql -h localhost -u root -proot -e "SELECT 1" 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-Success "[OK] MySQL is running"
        } else {
            Write-Warning-Custom "[WARNING] MySQL connection failed!"
            Write-Warning-Custom "Please ensure MySQL is running with user 'root' and password 'root'"
            Read-Host "Press Enter to continue anyway or close this window to exit"
        }
    } catch {
        Write-Warning-Custom "[WARNING] MySQL not found in PATH"
        Write-Warning-Custom "Continuing without MySQL check..."
    }
    Write-Host ""
    
    # Create database
    Write-Info "Creating database 'hcl' if it doesn't exist..."
    try {
        mysql -h localhost -u root -proot -e "CREATE DATABASE IF NOT EXISTS hcl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-Success "[OK] Database created/verified"
        } else {
            Write-Error-Custom "[ERROR] Failed to create database"
        }
    } catch {
        Write-Warning-Custom "[WARNING] Could not create database automatically"
    }
    Write-Host ""
}

# Start Backend
if (-not $FrontendOnly) {
    Write-Info "========================================"
    Write-Info "Starting Backend Server..."
    Write-Info "========================================"
    Write-Info "Backend will be available at: http://localhost:8080"
    Write-Host ""
    
    Push-Location $backendPath
    
    # Run Maven
    & ".\mvnw.cmd" clean spring-boot:run
    
    Pop-Location
}

# Start Frontend (if not backend-only)
if (-not $BackendOnly) {
    Write-Info "========================================"
    Write-Info "Starting Frontend Server..."
    Write-Info "========================================"
    Write-Info "Frontend will be available at: http://localhost:5173"
    Write-Host ""
    
    Push-Location $frontendPath
    
    # Install dependencies if needed
    if (-not (Test-Path "node_modules")) {
        Write-Info "Installing frontend dependencies..."
        npm install
    }
    
    # Start dev server
    npm run dev
    
    Pop-Location
}

Write-Host ""
Write-Success "Setup complete!"
