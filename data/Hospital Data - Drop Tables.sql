/**
 * Copyright (C) 2022-2022, HDM-Dev Team
 * All Rights Reserved

 * This file is part of HDM-Dev Team's project. The contents are
 * fully covered, controlled, and acknowledged by the terms of the
 * BSD-3 license, which is included in the file LICENSE.md, found
 * at the root of the project's source code/tree repository.
**/

/**
 * This module attempted to delete all tables and their 
 * relationship among classes.
 * 
 * --------------------------------------------------------------------
 * @database HospitalData
 * @version 0.0.1
 * References: http://www.sqlservertutorial.net/load-sample-database/
 * --------------------------------------------------------------------
 * 
**/

-- Drop the Tables
ALTER TABLE LiveState.MedicoRoom DROP CONSTRAINT UniquePair_MedicoRoom_1;
ALTER TABLE LiveState.MedicoRoom DROP CONSTRAINT UniquePair_MedicoRoom_2;
ALTER TABLE LiveState.MedicoRoom DROP CONSTRAINT UniquePair_MedicoRoom_3;
ALTER TABLE LiveState.PatientRoom DROP CONSTRAINT UniquePair_PatientRoom;
ALTER TABLE CoreData.MedicoTreatment DROP CONSTRAINT UniquePair_MedicoTreatment;
GO

DROP TABLE IF EXISTS LiveState.MedicoRoom;
DROP TABLE IF EXISTS LiveState.PatientRoom;

DROP TABLE IF EXISTS CoreData.MedicoTreatment;
DROP TABLE IF EXISTS CoreData.SicknessTreatment;
DROP TABLE IF EXISTS CoreData.Treatment;

DROP TABLE IF EXISTS CoreData.Medico;
DROP TABLE IF EXISTS CoreData.Patient;

DROP TABLE IF EXISTS LiveState.Room;
DROP TABLE IF EXISTS CoreData.SicknessEnum;
DROP TABLE IF EXISTS CoreData.DiagnosticEnum;
DROP TABLE IF EXISTS CoreData.JobEnum;
DROP TABLE IF EXISTS CoreData.DepartmentEnum;
GO

--Drop the Schemas
DROP SCHEMA IF EXISTS LiveState;
DROP SCHEMA IF EXISTS CoreData;
GO