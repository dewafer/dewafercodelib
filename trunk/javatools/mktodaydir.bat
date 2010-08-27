@echo off
Title=mktodaydir
set datetime=%date%
set yy=%datetime:~0,4%
set mm=%datetime:~5,2%
set dd=%datetime:~8,2%
REM set /a d=(dd-1)
REM if exist %yymm%-%d% rd /s/q %yymm%-%d%
if not exist %yy%%mm%%dd% md %yy%%mm%%dd%