# 🚀 Automated Setup Guide - HCL Restaurant Application

This guide explains how to automatically set up and run the HCL Restaurant Application with database initialization.

---

## 📋 Prerequisites

- **Java 21+** (for backend)
- **Node.js 18+** (for frontend)
- **MySQL 8.0+** (for database)
- **Docker & Docker Compose** (optional, for containerized setup)

---

## ⚡ Quick Start

### Option 1: Automated Script (Recommended for Local Development)

#### Windows (PowerShell)
```powershell
# Run the setup script
.\setup-and-run.ps1

# Or with specific options
.\setup-and-run.ps1 -BackendOnly      # Start only backend
.\setup-and-run.ps1 -FrontendOnly     # Start only frontend
```

#### Windows (Command Prompt)
```cmd
setup-and-run.bat
```

**What it does automatically:**
✅ Sets environment variables  
✅ Checks MySQL connection  
✅ Creates database `hcl`  
✅ Builds backend with Maven  
✅ Loads dummy data from `data.sql`  
✅ Starts backend on `http://localhost:8080`  
✅ Starts frontend on `http://localhost:5173`  

---

### Option 2: Docker Compose (Recommended for Production)

```bash
# Build and start all services
docker-compose up --build

# Or run in background
docker-compose up -d
```

**Services started:**
- MySQL 8.0 on port 3306
- Backend on http://localhost:8080
- Frontend on http://localhost:5173

**Cleanup:**
```bash
docker-compose down          # Stop services
docker-compose down -v       # Stop services and remove volumes
```

---

### Option 3: Manual Setup

#### Step 1: MySQL Setup
```bash
# Start MySQL service (if not running)
# Windows: Use MySQL Workbench or Command Line Client

# Create database
mysql -h localhost -u root -proot -e "CREATE DATABASE hcl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

#### Step 2: Set Environment Variables
```powershell
# PowerShell
$env:DB_URL = "jdbc:mysql://localhost:3306/hcl"
$env:CORS_ALLOWED_ORIGIN_PATTERNS = "http://localhost:5173,http://localhost:3000"
```

Or create a `.env` file in `hcl-backend/` with:
```
DB_URL=jdbc:mysql://localhost:3306/hcl
CORS_ALLOWED_ORIGIN_PATTERNS=http://localhost:5173,http://localhost:3000
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
MAIL_FROM=your-email@gmail.com
```

#### Step 3: Start Backend
```bash
cd hcl-backend
mvnw clean spring-boot:run
```

#### Step 4: Start Frontend
```bash
cd hcl-frontend
npm install
npm run dev
```

---

## 📊 Database Automation Details

### What Gets Loaded Automatically

The `data.sql` file contains:
- **6 Users** (5 customers + 1 admin)
- **5 Restaurants** with cuisine types
- **25 Menu Items** across all restaurants
- **5 Shopping Carts**
- **10 Cart Items**
- **6 Orders** with different statuses
- **13 Order Items**

### How It Works

1. **Schema Creation** (`application.properties`):
   ```properties
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.sql.init.mode=always
   spring.sql.init.data-locations=classpath:data.sql
   ```

2. **On Application Startup:**
   - Spring drops existing tables
   - JPA creates new tables from entity definitions
   - `data.sql` is automatically executed to populate dummy data

3. **Database Connection:**
   - URL: `jdbc:mysql://localhost:3306/hcl`
   - Username: `root`
   - Password: `root`

---

## 🔍 Verify Setup

### Check Backend
```bash
curl http://localhost:8080/api/restaurants
# Should return list of 5 restaurants
```

### Check Frontend
Open `http://localhost:5173` in your browser

### Check Database
```bash
mysql -h localhost -u root -proot hcl -e "SELECT COUNT(*) as total_users FROM users;"
```

---

## 🛠️ Troubleshooting

### MySQL Connection Failed
```bash
# Check if MySQL is running
mysql -h localhost -u root -proot -e "SELECT 1"

# If error, start MySQL service:
# Windows: Services → MySQL → Start
# Or use MySQL Command Line Client
```

### Port Already in Use
```bash
# Backend (8080)
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Frontend (5173)
netstat -ano | findstr :5173
taskkill /PID <PID> /F
```

### Build Fails
```bash
cd hcl-backend
mvnw clean
mvnw install
```

### Node Modules Issues
```bash
cd hcl-frontend
rm -r node_modules package-lock.json
npm install
```

---

## 📝 Configuration Files

| File | Purpose |
|------|---------|
| `setup-and-run.bat` | Windows batch automation script |
| `setup-and-run.ps1` | Windows PowerShell automation script |
| `docker-compose.yml` | Docker Compose configuration |
| `hcl-backend/Dockerfile` | Backend Docker image |
| `hcl-frontend/Dockerfile` | Frontend Docker image |
| `hcl-backend/src/main/resources/data.sql` | Dummy data loader |
| `hcl-backend/src/main/resources/application.properties` | Spring Boot configuration |

---

## 🌐 Application URLs

| Service | URL | Port |
|---------|-----|------|
| Frontend | http://localhost:5173 | 5173 |
| Backend API | http://localhost:8080 | 8080 |
| MySQL | localhost:3306 | 3306 |

---

## 📚 API Endpoints

```
GET    /api/restaurants              - List all restaurants
GET    /api/restaurants/{id}         - Get restaurant details
GET    /api/menu-items               - List menu items
POST   /api/auth/register            - Register user
POST   /api/auth/login               - Login user
GET    /api/orders                   - List user's orders
POST   /api/orders                   - Create new order
GET    /api/cart                     - Get user's cart
POST   /api/cart/items               - Add item to cart
```

---

## 🔐 Default Admin Credentials

```
Email: admin@example.com
Password: (check data.sql for hashed password)
```

---

## ✨ Next Steps

1. ✅ Run automated setup
2. ✅ Verify all services are running
3. ✅ Open frontend at http://localhost:5173
4. ✅ Test login with dummy user data
5. ✅ Browse restaurants and menu items
6. ✅ Customize dummy data in `data.sql` as needed

---

## 📞 Support

For issues or questions, check:
- Backend logs: `hcl-backend/target/app.log`
- Frontend console: Browser DevTools → Console
- MySQL logs: `MySQL Workbench → Administration → Server Logs`

