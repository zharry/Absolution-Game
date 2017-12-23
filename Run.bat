@echo off
echo Removing Old Versions...
del /q /f /s output\*
rd /s /q output
cls
echo Compiling...
setlocal enabledelayedexpansion
(for /f "delims=" %%f in ('dir /S /B src\*.java') do @set v=%%f&@echo "!v:\=\\!") >> sources.txt
mkdir output
javac -d output @sources.txt
del sources.txt
pause
echo Running...
java -cp output Game.AbsolutionGame
pause