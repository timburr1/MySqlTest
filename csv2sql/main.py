import pandas as pd
import sqlite3
import sys
import os

currentDir = str(os.path.basename(os.path.dirname(os.path.realpath(__file__))))#Gets current directory

print("----- CSV -> SQL DB -----")
filePath = input(str("Insert file path to CSV file: "))#Prompts user for file path
dbName = input(str("Insert name for new DB file: "))#Prompts user for new db file name
conn = sqlite3.connect(currentDir + "/" + dbName+ '.db')#Creating/connecting to new DB
csvData = pd.read_csv(filePath)#read file from filePath variable

printCSV = input(str("Print CSV contents (Y/N): "))
if printCSV == "Y":
    print("\n=============================================")
    print(csvData)#print contents of csvData
    print("=============================================\n")
    print("\nCreating DB from CSV...")
else:
    print("Creating DB from CSV...")

cursor = conn.cursor()#Initialize cursor (writes data to DB)
cursor.execute("CREATE TABLE IF NOT EXISTS dbTable ({})".format(' ,'.join(csvData.columns)))#Create table in DB

for row in csvData.iterrows():
    sql = "INSERT INTO dbTable ({}) VALUES ({})".format(' ,'.join(csvData.columns), ','.join(["?"] * len(csvData.columns)))
    cursor.execute(sql, tuple(row[1]))#For rwo in CSV, insert that row's values into a new row in the DB table

conn.commit()#Finalize changes to DB
conn.close()#Close DB connection
sys.exit()#Closes program after all code has run