DROP DATABASE IF EXISTS docs;
CREATE DATABASE docs DEFAULT CHARACTER SET utf8;
USE docs;

CREATE TABLE paper (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    Document_title TEXT,
    Authors TEXT,
    Author_Affiliations TEXT,
    Publication_Title TEXT,
    Date_Added_To_Xplore TEXT,
    Publication_Year VARCHAR(10),
    Volume TEXT,
    Issue TEXT,
    Start_Page VARCHAR(10),
    End_Page VARCHAR(10),
    Abstract TEXT,
    ISSN TEXT,
    ISBNs TEXT,
    DOI TEXT,
    Funding_Information TEXT,
    PDF_Link TEXT,
    Author_Keywords TEXT,
    IEEE_Terms TEXT,
    INSPEC_Controlled_Terms TEXT,
    INSPEC_Non_Controlled_Terms TEXT,
    Mesh_Terms TEXT,
    Article_Citation_Count VARCHAR(10),
    Reference_Count VARCHAR(10),
    License TEXT,
    Online_Date TEXT,
    Issue_Date TEXT,
    Meeting_Date TEXT,
    Publisher VARCHAR(45),
    Document_Identifier VARCHAR(45)
)ENGINE=InnoDB;


CREATE TABLE simplepaper(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    paper_id  INT,
    Authors TEXT,
    Author_Affiliations TEXT,
    Publication_Title TEXT,
    Publication_Year TEXT,
    Author_Keywords TEXT
)ENGINE=InnoDB;


COMMIT;