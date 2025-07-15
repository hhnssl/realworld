package springboot.java17.realworld.api.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {

    private int statusCode;

    private Date timestamp;

    private String message;

    private String detail;
}
