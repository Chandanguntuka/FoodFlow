#!/usr/bin/env powershell
<#
.SYNOPSIS
    HCL Restaurant App - Automated Database Setup & Application Run
.DESCRIPTION
    Starts both backend and frontend for local development.
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

# Environment Variables. Defaults use H2 so local CMD/PowerShell startup does not require MySQL.
$env:DB_URL = "jdbc:h2:mem:springbeans;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1"
$env:DB_USERNAME = "sa"
$env:DB_PASSWORD = ""
$env:JPA_DDL_AUTO = "update"
$env:CORS_ALLOWED_ORIGIN_PATTERNS = "http://localhost:5173,http://localhost:3000"
$env:MAIL_HOST = "smtp.gmail.com"
$env:MAIL_PORT = "587"
$env:MAIL_USERNAME = ""
$env:MAIL_PASSWORD = ""
$env:MAIL_FROM = "no-reply@springbeans.local"

Write-Info "Setting environment variables..."
Write-Info "DB_URL: $env:DB_URL"
Write-Info "CORS_ALLOWED_ORIGIN_PATTERNS: $env:CORS_ALLOWED_ORIGIN_PATTERNS"
Write-Host ""

# Start Backend
if (-not $FrontendOnly) {
    Write-Info "========================================"
    Write-Info "Starting Backend Server..."
    Write-Info "========================================"
    Write-Info "Backend will be available at: http://localhost:8080"
    Write-Host ""
    
    Start-Process cmd.exe -ArgumentList "/k", "cd /d `"$backendPath`" && mvn spring-boot:run"
}

# Start Frontend (if not backend-only)
if (-not $BackendOnly) {
    Write-Info "========================================"
    Write-Info "Starting Frontend Server..."
    Write-Info "========================================"
    Write-Info "Frontend will be available at: http://localhost:5173"
    Write-Host ""
    
    Start-Process cmd.exe -ArgumentList "/k", "cd /d `"$frontendPath`" && if not exist node_modules npm.cmd install && npm.cmd run dev"
}

Write-Host ""
Write-Success "Setup complete!"
Write-Success "Backend:  http://localhost:8080"
Write-Success "Frontend: http://localhost:5173"
Write-Success "Seed users: admin@springbeans.com / Admin@123, user@springbeans.com / User@123"
