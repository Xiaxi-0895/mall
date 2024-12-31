package com.xiaxi.mall.error;

import com.xiaxi.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception ex){
        logger.error("Default Exception", ex);
        return ApiRestResponse.error(MallExceptionEnum.SYSTEM_ERROR);
    }
    @ExceptionHandler(value = MallException.class)
    @ResponseBody
    public Object handleMallException(MallException ex){
        logger.error("MallException", ex);
        return ApiRestResponse.error(ex.getCode(), ex.getMsg());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.error("MethodArgumentNotValidException", ex);
        return handleBingingResult(ex.getBindingResult());
    }
    public ApiRestResponse handleBingingResult(BindingResult bindingResult){
        List<String> list =new ArrayList<>();
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for(ObjectError objectError : allErrors){
                String msg =objectError.getDefaultMessage();
                list.add(msg);
            }
        }
        if(list.isEmpty()){
            return ApiRestResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR.getCode(),MallExceptionEnum.REQUEST_PARAM_ERROR.getMessage()+":"+ list);
    }
}
