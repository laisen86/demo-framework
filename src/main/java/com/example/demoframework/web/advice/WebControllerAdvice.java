package com.example.demoframework.web.advice;

import com.example.demoframework.common.ResultModel;
import com.example.demoframework.common.enums.ResultModelCodeEnum;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;

/**
 * Controller层的异常处理示例
 *
 * @author zhangxueli
 * * @date 2019年2月18日23:48:24
 */
@ControllerAdvice
public class WebControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebControllerAdvice.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResultModel<String>> handleException(Throwable ex) {
        LOGGER.error("WebControllerAdvice handleException:{}", ex.getClass().getName(), ex); // 打印异常
        ResultModel<String> resultModel = new ResultModel<>();
        resultModel.setCode(ResultModelCodeEnum.FAULT.code());

        // --------------------------- 针对某些异常的特殊处理 --------------------------
        if (ex instanceof MissingServletRequestParameterException) {
            LOGGER.error(ex.toString()); // 打印异常
            MissingServletRequestParameterException e = (MissingServletRequestParameterException) ex;
            resultModel.setMsg(e.getParameterName() + "不能为空");
            return renderResponse(resultModel);
        }

        if (ex instanceof MethodArgumentTypeMismatchException) {
            LOGGER.error(ex.toString()); // 打印异常
            MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException) ex;
            resultModel.setMsg(e.getPropertyName() + "必须为" + e.getRequiredType() + "类型");
            return renderResponse(resultModel);

        }

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            LOGGER.error(ex.toString()); // 打印异常
            HttpRequestMethodNotSupportedException e = (HttpRequestMethodNotSupportedException) ex;
            resultModel.setMsg("不支持" + e.getMethod() + "方式的请求");
            return renderResponse(resultModel);
        }

        if (ex instanceof BindException) {
            resultModel.setMsg(processBindException((BindException) ex));
            return renderResponse(resultModel);
        }

        /**
         * 此异常都是spring拦截入参验证异常
         */
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = e.getBindingResult();
            if (bindingResult.hasErrors()) {
                Object object = bindingResult.getAllErrors().get(0);
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    resultModel.setMsg(fieldError.getDefaultMessage());
                }
            }
            LOGGER.error("MethodArgumentNotValidException handleException:{} , \n {}", ex.getClass().getName(), ex);
            return renderResponse(resultModel);
        }
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException e = (ConstraintViolationException) ex;
            if (e.getConstraintViolations() instanceof HashSet) {
                HashSet<ConstraintViolation<?>> constraintViolations = (HashSet<ConstraintViolation<?>>) e.getConstraintViolations();
                for (ConstraintViolation<?> violation : constraintViolations) {
                    String message = violation.getMessage();
                    return renderResponse(resultModel.setMsg(message));
                }
            }

        }

        resultModel.setMsg("系统异常");
        return renderResponse(resultModel);
    }

    public ResponseEntity<ResultModel<String>> renderResponse(ResultModel<String> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private String processBindException(BindException bindException) {
        List<ObjectError> errors = bindException.getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : errors) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(error.getDefaultMessage());
        }
        return stringBuilder.toString();
    }
}
