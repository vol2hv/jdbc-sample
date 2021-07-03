package com.madv.jdbcsample;

public enum SqlQuery {
    SQL_CREATE(new StringBuffer()
            .append("CREATE TABLE EMPLOYEE (ID serial, NAME varchar(100) NOT NULL,")
            .append(" SALARY numeric(15, 2) NOT NULL,")
            .append("CREATED_DATE timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,")
            .append(" PRIMARY KEY (ID))")
            .toString()),
    SQL_DROP(new StringBuffer()
            .append("DROP TABLE IF EXISTS EMPLOYEE")
            .toString()),
    SQL_INSERT(new StringBuffer()
            .append("INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)")
            .toString()),
    SQL_SELECT(new StringBuffer()
            .append("SELECT * FROM EMPLOYEE")
            .toString()),
    SQL_UPDATE(new StringBuffer()
            .append("UPDATE EMPLOYEE SET SALARY=? WHERE NAME=?")
            .toString()),
    SQL_DELETE(new StringBuffer()
            .append("DELETE FROM EMPLOYEE WHERE NAME=?")
            .toString());

    private String text;

    SqlQuery(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}


