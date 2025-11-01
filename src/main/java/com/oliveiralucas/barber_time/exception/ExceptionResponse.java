package com.oliveiralucas.barber_time.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {

}
