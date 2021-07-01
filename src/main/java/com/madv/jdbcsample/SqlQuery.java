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
            .toString());

    private String text;

    SqlQuery(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
}


