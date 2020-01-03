@echo off

for /f %%i in (#dependencyLibList#) do (set "dependencyLibList=%%i")

java -cp #appName#.jar;%dependencyLibList% #mainClass#

pause