# Refinement App

## What can this tool do?
Use this tool to convert .csv or .xlsx (Excel) and save or update data to PostgreSQL database into two tables joined by relation.
Persisted data contains 2 different entity:
 - client (id, name(unique), update_at, created_at)
 - data (id, client_id, code1(unique), code2(unique), some_data, updated_at, created_at)
 
## File requirements:

### file should contain 4 columns in order:
* client name
* code1
* code2
* some_data

## How to start: 
### Build the project 
```
mvn package
```
You may test the newly compiled and packaged JAR with the following command:
```
java -jar RefinementTask-1.0-SNAPSHOT.jar path_to_csv_or_xlsx_file
```
### Configuration 
application.properties contains datasource to make correct connection to database

__you need rebuild project after change it__

