call runcrud.bat
if "%ERRORLEVEL%" == "0" goto showtasks
echo.
echo Running runcrud.bat failed - breaking work
goto fail

:showtasks
start chrome "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot open browser - breaking work

:fail
echo.
echo There were errors

:end
echo.
echo Showtasks - work is finished.