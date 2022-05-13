/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This module attempted to create the all tables and their 
 * relationship among classes.
 * 
 * --------------------------------------------------------------------
 * @database HospitalData
 * @version 0.0.1
 * References: 
 * 1) https://sqlserverguides.com/sql-server-bulk-insert-from-csv-file/
 * 2) https://www.mssqltips.com/sqlservertip/6109/bulk-insert-data-into-sql-server/
 * 3) https://stackoverflow.com/questions/41223453/bulk-insert-error-when-loading-date-format
 * 4) https://docs.microsoft.com/en-us/sql/t-sql/statements/bulk-insert-transact-sql?view=sql-server-ver15 (Most important)
 * --------------------------------------------------------------------
 * 
**/

USE HospitalData
GO

-- Load data into Enum
BULK INSERT HospitalData.CoreData.DepartmentEnum
FROM '\DepartmentEnum.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
    TABLOCK
    )
SELECT * FROM CoreData.DepartmentEnum
GO

BULK INSERT HospitalData.CoreData.DiagnosticEnum
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\DiagnosticEnum.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
    TABLOCK
    )
SELECT * FROM CoreData.DiagnosticEnum
GO

BULK INSERT HospitalData.CoreData.JobEnum
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\JobEnum.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
    TABLOCK
    )
SELECT * FROM CoreData.JobEnum
GO

BULK INSERT HospitalData.CoreData.SicknessEnum
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\SicknessEnum.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
    TABLOCK
    )
SELECT * FROM CoreData.SicknessEnum
GO

BULK INSERT HospitalData.LiveState.Room
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\Room.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
    TABLOCK
    )
SELECT * FROM LiveState.Room
GO

-- Load data into Data
SET DATEFORMAT ymd;
BULK INSERT HospitalData.CoreData.Patient
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\Patient.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM CoreData.Patient
GO

BULK INSERT HospitalData.CoreData.Medico
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\Medico.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM CoreData.Medico
GO

BULK INSERT HospitalData.CoreData.Treatment
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\Treatment.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM CoreData.Treatment
GO

-- Load Edge table
BULK INSERT HospitalData.CoreData.SicknessTreatment
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\SicknessTreatment.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM CoreData.SicknessTreatment
GO

BULK INSERT HospitalData.CoreData.MedicoTreatment
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\MedicoTreatment.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM CoreData.MedicoTreatment
GO

BULK INSERT HospitalData.LiveState.PatientRoom
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\PatientRoom.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM LiveState.PatientRoom
GO

BULK INSERT HospitalData.LiveState.MedicoRoom
FROM 'D:\A3 workshop\IU Workshop\PDM\Project Database\PDM_HospitalDataManagement\data\MedicoRoom.csv'
WITH
    (
    FIRSTROW = 2,
	FORMAT = 'CSV',
	CHECK_CONSTRAINTS,
    FIELDTERMINATOR = ',',  --CSV field delimiter
    ROWTERMINATOR = '\n',   --Use to shift the control to next row
	MAXERRORS=100,
	BATCHSIZE=500,
    TABLOCK
    )
SELECT * FROM LiveState.MedicoRoom
GO