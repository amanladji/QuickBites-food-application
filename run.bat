@echo off
cd /d "%~dp0"
echo QuickBite - Food Delivery App
echo ==============================
echo.
echo Step 1: Finding a free port...
set PORT=8082
:checkport
netstat -ano 2>nul | findstr ":%PORT% " >nul
if %errorlevel% equ 0 (
    set /a PORT=PORT+1
    if %PORT% gtr 8100 (
        echo ERROR: No free port found
        pause
        exit /b 1
    )
    goto checkport
)
echo Using port %PORT%
echo.
echo Step 2: Starting server...
echo Open http://localhost:%PORT%/ in your browser
echo Close this window to stop the server
echo ==============================
java -jar "target\quickbite.jar" %PORT%
pause
