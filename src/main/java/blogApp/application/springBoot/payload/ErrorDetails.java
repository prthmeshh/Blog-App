package blogApp.application.springBoot.payload;

import java.util.Date;

public class ErrorDetails {

    private Date date;
    private String message;
    private String details;

    public ErrorDetails(Date date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
