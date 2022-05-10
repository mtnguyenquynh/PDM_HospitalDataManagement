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
 * @author Ichiru Take
 * @version 0.0.1
 * References: http://www.sqlservertutorial.net/load-sample-database/
 * --------------------------------------------------------------------
 * 
**/


-- Create Schemas
CREATE SCHEMA CoreData;
GO

CREATE SCHEMA LiveState;
GO

-- Create Tables
-- 1) Enum/Dead-classes
CREATE TABLE CoreData.DepartmentEnum (
	CODE VARCHAR (50) PRIMARY KEY,
	Description VARCHAR (50) NOT NULL
);
GO

CREATE TABLE CoreData.JobEnum (
	CODE VARCHAR (50) PRIMARY KEY,
	Description VARCHAR (50) NOT NULL
);
GO

CREATE TABLE CoreData.DiagnosticEnum (
	CODE VARCHAR (50) PRIMARY KEY,
	Description VARCHAR (250) NOT NULL
);
GO

CREATE TABLE CoreData.SicknessEnum (
	CODE VARCHAR (50) PRIMARY KEY,
	Description VARCHAR (250) NOT NULL
);
GO

CREATE TABLE LiveState.Room (
	CODE VARCHAR (50) PRIMARY KEY,
	Description VARCHAR (250) NOT NULL,
	FunctionalRoom BIT NOT NULL
);
GO

-- 2) Data/Live-classes
CREATE TABLE CoreData.Patient (
	ID VARCHAR (50) PRIMARY KEY, 
	FirstName VARCHAR (50) NOT NULL ,
	LastName VARCHAR (50) NOT NULL ,
	Address VARCHAR (250) NOT NULL ,
	Country VARCHAR (50) NOT NULL ,
	Brithdate DATETIME NOT NULL,
	Email NVARCHAR (100) NOT NULL UNIQUE ,
	IsFemale BIT NOT NULL,
	Blood VARCHAR (25) ,
	Phone VARCHAR (25) ,
)
GO

CREATE TABLE CoreData.Medico (
	ID VARCHAR (50) PRIMARY KEY, 
	FirstName VARCHAR (50) NOT NULL ,
	LastName VARCHAR (50) NOT NULL ,
	Address VARCHAR (250) NOT NULL ,
	Country VARCHAR (50) NOT NULL ,
	Brithdate DATETIME NOT NULL,
	Email NVARCHAR (100) NOT NULL UNIQUE ,
	IsFemale BIT NOT NULL,
	Blood VARCHAR (25) ,
	Phone VARCHAR (25) ,
	-- Reference Keys --
	DeptCode VARCHAR (50) FOREIGN KEY REFERENCES CoreData.DepartmentEnum(CODE) ON DELETE CASCADE ON UPDATE CASCADE, 
	Job VARCHAR (50) FOREIGN KEY REFERENCES CoreData.JobEnum(CODE) ON DELETE CASCADE ON UPDATE CASCADE, 
);
GO

CREATE TABLE CoreData.Treatment (
	ID VARCHAR (50) PRIMARY KEY, 
	Description VARCHAR (1000) NOT NULL,
	Creation_Datetime DATETIME NOT NULL,
	IsActive BIT NOT NULL,

	-- Reference Keys --
	Patient_ID VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.Patient(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	Room_CODE VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES LiveState.Room(CODE) ON DELETE NO ACTION ON UPDATE CASCADE,
	Operation_CODE VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.DiagnosticEnum(CODE) ON DELETE NO ACTION ON UPDATE CASCADE,
);
GO

-- 3.1) Edge-tables in CoreData
CREATE TABLE CoreData.SicknessTreatment (
	-- Reference Keys --
	Treatment_ID VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.Treatment(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	Sickness_CODE VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.SicknessEnum(CODE) ON DELETE CASCADE ON UPDATE CASCADE,

	-- Constraint --
	CONSTRAINT UniquePair UNIQUE (Treatment_ID, Sickness_CODE),
);
GO

CREATE TABLE CoreData.MedicoTreatment (
	-- Reference Keys --
	Treatment_ID VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.Treatment(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	Medico_ID VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.Medico(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	Medico_Work VARCHAR (250) NOT NULL,

	-- Constraint --
	CONSTRAINT UniquePair UNIQUE (Treatment_ID, Medico_ID),
);
GO

-- 3.2) Edge-tables in LiveState
CREATE TABLE LiveState.PatientRoom (
	-- Reference Keys --
	Room_CODE VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES LiveState.Room(CODE) ON DELETE CASCADE ON UPDATE CASCADE,
	Patient_ID VARCHAR (50) NOT NULL UNIQUE FOREIGN KEY REFERENCES CoreData.Patient(ID) ON DELETE CASCADE ON UPDATE CASCADE,

	-- Constraint --
	CONSTRAINT UniquePair UNIQUE (Room_CODE, Patient_ID),
);
GO

CREATE TABLE LiveState.MedicoRoom (
	-- Reference Keys --
	Room_CODE VARCHAR (50) PRIMARY KEY NOT NULL FOREIGN KEY REFERENCES LiveState.Room(CODE) ON DELETE CASCADE ON UPDATE CASCADE,
	Medico_ID VARCHAR (50) NOT NULL FOREIGN KEY REFERENCES CoreData.Medico(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	Opt_Medico_ID VARCHAR (50) DEFAULT NULL FOREIGN KEY REFERENCES CoreData.Medico(ID) ON DELETE CASCADE ON UPDATE CASCADE,

	-- Constraint --
	CONSTRAINT UniquePair UNIQUE (Room_CODE, Medico_ID),
);
GO
