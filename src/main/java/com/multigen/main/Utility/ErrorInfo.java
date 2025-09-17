package com.multigen.main.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    private String message;
    private int statusCode;
    private LocalDateTime localDateTime;
}
