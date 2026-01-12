package com.example.expenses_tracker.error;

import lombok.Data;
import org.springframework.http.HttpStatusCode;


import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime time;
    private String error;
    private HttpStatusCode code;

    public ApiError(){
        this.time=LocalDateTime.now();
    }
    public ApiError(String error,HttpStatusCode code){
        this();
        this.code=code;
        this.error=error;
    }

}
