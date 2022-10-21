@echo off
REM OneDriveBackup for CaravanMaintenance data files and reports

if not exist "%userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\CaravanMaintenance\" mkdir %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\CaravanMaintenance

xcopy *.xml %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\CaravanMaintenance /Y

xcopy out\*.* %userprofile%\OneDrive\Documents\App_Data_and_Reporting_Backups\CaravanMaintenance\out /I /Y
