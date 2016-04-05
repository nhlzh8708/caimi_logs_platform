package com.caimi.logsagent.exceptions;

public class DataTransferException extends Exception {
	String errorMessage;
	 
    public DataTransferException(String errorMessage)
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
