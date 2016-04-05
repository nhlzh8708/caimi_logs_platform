package com.caimi.logsagent.exceptions;

public class DbConnManagerException extends  Exception {
	String errorMessage;
	 
    public DbConnManagerException(String errorMessage)
    {
         this.errorMessage = errorMessage;
    }
 
    public String toString()
    {
         return errorMessage;
    }
 
    public String getMessage()
    {
         return errorMessage;
    }
}
