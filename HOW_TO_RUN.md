# How to Run HomeHeaven Application

## Prerequisites
- Java 21 (LTS) installed
- MySQL 8.0+ running locally
- Maven 3.9.x installed

## Quick Start

### Step 1: Ensure MySQL is Running
```bash
# Make sure MySQL service is running on localhost:3306
# Default credentials in application.properties:
# - Username: root
# - Password: Pass@123
# - Database: homeheaven (will be created automatically)
```

### Step 2: Build the Project
```bash
cd C:\Users\Mladda\Downloads\homeheaven_full

# Clean build and package
C:\Users\Mladda\tools\maven\bin\mvn.cmd clean package -DskipTests
```


Get-Process java -ErrorAction SilentlyContinue | Stop-Process -Force; Start-Sleep -Seconds 2; C:\Users\Mladda\tools\maven\bin\mvn.cmd -DskipTests clean package 2>&1 | Select-Object -Last 20 



### Step 3: Run the Application

#### Option A: Run with Java directly (Foreground - See Logs)
```bash
cd C:\Users\Mladda\Downloads\homeheaven_full
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "target\home-heaven-0.0.1-SNAPSHOT.jar"
```

#### Option B: Run in Background (PowerShell)
```bash
cd C:\Users\Mladda\Downloads\homeheaven_full
Start-Process -FilePath "C:\Program Files\Java\jdk-21\bin\java.exe" -ArgumentList '-jar', 'target\home-heaven-0.0.1-SNAPSHOT.jar' -NoNewWindow
```

### Step 4: Access the Application
Open your browser and go to:
```
http://localhost:8080/login.html
```

## Default Login Credentials

**For Testing:**
- Username: `testuser` (create via registration first)
- Or use any account you register

## Features

### User Registration
1. Go to `http://localhost:8080/login.html`
2. Click on "Register" link
3. Fill in username, email, phone, password
4. Account will be created

### Login
1. Enter username and password
2. You'll be redirected to the dashboard

### Upload Property
1. After login, click "Upload Property"
2. Fill in property details (name, city, rent, type, etc.)
3. Upload images
4. Click "Upload"
5. You'll see a success message and a back link to dashboard

### View & Manage Your Properties
1. After login, click "My Properties" (top right)
2. You'll see all your uploaded properties
3. Click "Delete" to remove a property
4. Click "View Details" to see full property information

### Search Properties
1. On the dashboard, use the search form
2. Filter by: City, Rent range, Property type
3. View results and click "View Details" for more info

### Password Reset (OTP)
1. Go to `http://localhost:8080/forgot.html`
2. Enter your email
3. You'll receive an OTP (if Gmail is configured - see GMAIL_SETUP_INSTRUCTIONS.md)
4. Enter the OTP and new password
5. Your password will be reset

## Important Configuration

### Gmail Setup (for OTP emails)
See `GMAIL_SETUP_INSTRUCTIONS.md` for detailed steps to:
1. Enable "Less Secure App Access" on your Gmail account
2. Configure the email credentials in `application.properties`

Current Gmail settings:
- Host: smtp.gmail.com
- Port: 587
- Username: homeheaven987@gmail.com
- Password: Pass@123

### Database Configuration
File: `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/homeheaven?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=Pass@123
```

## Troubleshooting

### Port 8080 Already in Use
```bash
# Find process using port 8080
netstat -a -n -o | Select-String ':8080'

# Kill the process (replace PID with actual process ID)
Stop-Process -Id <PID> -Force
```

### MySQL Connection Error
1. Verify MySQL is running: `mysql -u root -p` (password: Pass@123)
2. Check database exists: `SHOW DATABASES;` (should show 'homeheaven')
3. Restart MySQL service if needed

### Build Fails
```bash
# Clean cache and rebuild
C:\Users\Mladda\tools\maven\bin\mvn.cmd clean
C:\Users\Mladda\tools\maven\bin\mvn.cmd package -DskipTests
```

### App Doesn't Start
1. Check Java version: `java -version` (should be 21)
2. Check logs while running in foreground to see error messages
3. Verify all port 8080 processes are stopped

## Project Structure

```
homeheaven_full/
├── src/main/java/com/homeheaven/
│   ├── controller/           # REST endpoints
│   ├── service/              # Business logic
│   ├── model/                # JPA entities
│   ├── repository/           # Database queries
│   └── config/               # Spring configuration
├── src/main/resources/
│   ├── static/               # HTML, CSS, JavaScript
│   │   ├── login.html
│   │   ├── register.html
│   │   ├── dashboard.html
│   │   ├── admin.html       # My Properties page
│   │   ├── property-details.html
│   │   ├── upload-property.html
│   │   ├── forgot.html      # Password reset page
│   │   └── uploads/         # User uploaded images
│   └── application.properties
├── pom.xml                   # Maven configuration
└── target/
    └── home-heaven-0.0.1-SNAPSHOT.jar  # Runnable JAR
```

## What's Been Recently Added

✅ **Owner Property Management**
- View your own uploaded properties
- Delete properties you own
- See property details with owner contact info

✅ **Property Images**
- Upload multiple images during property upload
- Images served from `/uploads/` endpoint
- Thumbnail display on dashboard
- Full-size images on property details page

✅ **UI Improvements**
- Back navigation after property upload
- Better error handling
- Custom error pages (404, 500)

## Next Steps

1. **Enable Gmail for OTP emails** - See GMAIL_SETUP_INSTRUCTIONS.md
2. **Test the complete workflow:**
   - Register a new user
   - Upload a property with images
   - View your properties in admin panel
   - Try to delete a property
   - Reset password to receive OTP

## Support

For issues or questions, check:
- Application logs in the terminal
- Browser console (F12 in browser)
- MySQL error logs if database issues occur
- GMAIL_SETUP_INSTRUCTIONS.md for email configuration
