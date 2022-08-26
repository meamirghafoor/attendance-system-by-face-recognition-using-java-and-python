# -*- coding: utf-8 -*-
"""
Created on Wed Sep 23 23:48:19 2020

@author: amine gasa
"""
import sqlite3
from datetime import datetime
conn=sqlite3.connect('databaseAttendance.db')
c = conn.cursor()
def insert_data(id,name,course,datetime):
    with conn:
        c.execute(f"INSERT INTO Attendance(id,fullname,course,datetime)values ('{id}','{name}','{course}','{datetime}');")
def check_course(name):
    c.execute(f"SELECT course from Student where id='{name}'")
    row = c.fetchall()
    return row[0][0]

def verify():
    c.execute(f"SELECT cor from pid")
    row1 = c.fetchall()
    return row1[0][0]
def exist_name(name,d1):
          c.execute(f"SELECT fullname FROM Attendance  where datetime between '{d1} 00:00:00' and '{d1} 23:59:59' ")
          row =c.fetchall()
          for ro in row:
              if(name==ro[0]):
                  print(ro[0])
                  return True
          return False