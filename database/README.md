The `database` module will contain all the data represented the state/status provided by the real-world hospital. 
Some are written at the place of source code, but they all considering the state of the data and software, but not the real-world data. Note that in real-world, we normally used the absolute directory to have an isolated data pool, but since the cloud/remote development and deployment is not applicable, we ignored them and used relative directory only.

We divide into six (or even more) folder to manage. Note that not all JSON, YAML, BSON, or SQL-DB is managed.
   - GlobalPool: This is a folder contain the two DB databases of (global) tool pool and (global) resources pool
   - MedicoData: This is a folder contain the one DB database recording all medicos (that are currently working here). Although the retired medico can be inserted, it is strongly varied due to the hospital regulation and thus, we don't introduce here.
   - MedicoTask: This is a folder containing all the tasks of every medico in `medico_data`, and distributed via folder only. In each child folder, we make JSON to store all the tasks that are 'active' and JSON or SQL-DB for 'inactive' tasks (a.k.a 'storage')
   - PatientData: This is a folder contain the one DB database recording all patients. One database only but tons of tables
   - PatientRecord: This is a folder containing all the medico_records of every patients in `patient_data`, and distributed via folder only (named by patient_ID). In each child folder, we build multiple of child folders (named by medico_record_ID) and in these child folder we store the treatment information. The storage of each treatment record can be seen in the document and software
   - Room: This is a folder where we control and manipulate the room code, patient, and medicos of the hospital.
   - TreatmentCode: This stored numerous of code that is used to classify the state of the Treatment
 