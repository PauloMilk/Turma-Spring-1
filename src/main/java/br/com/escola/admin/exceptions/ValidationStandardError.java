package br.com.escola.admin.exceptions;

import java.time.LocalDateTime;

public class ValidationStandardError extends StandardError {

    private String fields;
    private String fieldsMessage;

    public ValidationStandardError(LocalDateTime instant, Integer status, String error, String message, String path) {
        super(instant, status, error, message, path);
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getFieldsMessage() {
        return fieldsMessage;
    }

    public void setFieldsMessage(String fieldsMessage) {
        this.fieldsMessage = fieldsMessage;
    }

}
