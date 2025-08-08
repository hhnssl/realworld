package springboot.java17.realworld.api.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ErrorMessage> handleDuplicatedEmailException(DuplicatedEmailException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );
        log.error("DuplicatedEmailException", e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedUsernameException.class)
    public ResponseEntity<ErrorMessage> handleDuplicatedUsernameException(DuplicatedUsernameException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );
        log.error("DuplicatedUsernameException", e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ErrorMessage> handleUserNotAuthenticatedException(UserNotAuthenticatedException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );
        log.error("UserNotAuthenticatedException", e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleArticleNotFoundException(ArticleNotFoundException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );
        log.error("ArticleNotFoundException", e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(), // 400
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );

        log.error("IllegalArgumentException", e);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    /* 인증/인가 관련*/
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handleExpiredJwtException(ExpiredJwtException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(), // 401
            new Date(),
            "토큰이 만료되었습니다. 다시 로그인해주세요.",
            request.getDescription(false)
        );

        log.error("ExpiredJwtException", e);

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(), // 401
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );

        log.error("BadCredentialsException", e);

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }


    /* DB 관련 */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(), // 404
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );

        log.error("EntityNotFoundException", e);

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException e,
        WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(), // 404
            new Date(),
            e.getMessage(),
            request.getDescription(false)
        );

        log.error("UsernameNotFoundException", e);

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(
        DataIntegrityViolationException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.CONFLICT.value(), // 409
            new Date(),
            "이미 존재하는 데이터입니다. 요청 내용을 확인해주세요.",
            request.getDescription(false)
        );

        log.error("DataIntegrityViolationException", e);

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(), // 404
            new Date(),
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
            request.getDescription(false)
        );

        log.error("MethodArgumentNotValidException", e);

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }


    /* 500번대 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
            new Date(),
            "서버 내부 오류가 발생했습니다.",
            request.getDescription(false)
        );

        log.error("Exception", e);

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}