-- Table Schema
CREATE TABLE IF NOT EXISTS inventory (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    name TEXT NOT NULL,                   
    price REAL NOT NULL,                  
    stock INTEGER NOT NULL,               
    expiry_date TEXT                      
);