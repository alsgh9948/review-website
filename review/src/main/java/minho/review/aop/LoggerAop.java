package minho.review.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import minho.review.common.utils.ErrorResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
public class LoggerAop {
//    private static final Logger logger = LogManager.getLogger(UserController.class);

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s : %s)",
                        entry.getKey(), String.join(", ", entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    private HashMap<String, Object> getRequest(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "[Parameter None]";
        if (!paramMap.isEmpty()) {
            params = " [" + paramMapToString(paramMap) + "]";
        }

        HashMap<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("request",request);
        requestMap.put("params",params);

        return requestMap;
    }

    private String getLogString(String methodInfo, Long start, Long end, String response){
        HashMap<String, Object> requestMap = getRequest();
        HttpServletRequest request = (HttpServletRequest)requestMap.get("request");
        String params = (String) requestMap.get("params");

        return String.format(
                        "%s" +
                        "\nRequest: %s %s %s < %s (%dms)" +
                        "\nResponse: %s",
                        methodInfo,
                        request.getMethod(), request.getRequestURI(),
                        params, request.getRemoteHost(), end - start, response);
    }

    private String getLogString(String methodInfo, String response){
        HashMap<String, Object> requestMap = getRequest();
        HttpServletRequest request = (HttpServletRequest)requestMap.get("request");
        String params = (String) requestMap.get("params");

        return String.format(
                        "%s" +
                        "\nRequest: %s %s %s < %s" +
                        "\nResponse: %s",
                        methodInfo,
                        request.getMethod(), request.getRequestURI(),
                        params, request.getRemoteHost(), response);
    }
    @Around("execution(* minho.review..*Controller.*(..))")
    public Object requestLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = null;
        String response = null;
        boolean errorFlag = false;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
            response = new ObjectMapper().writeValueAsString(result);
            return result;
        }
        catch (Exception e){
            errorFlag = true;
            throw e;
        }
        finally {
            if (!errorFlag) {
                long end = System.currentTimeMillis();
                String methodInfo = joinPoint.getSignature().toString();
                log.info(getLogString(methodInfo, start, end, response));
            }
        }
    }

    @AfterReturning(value = "execution(* minho.review..*ExceptionHandler.*(..))", returning = "errorResponse")
    public void exceptionLog(JoinPoint joinPoint, ResponseEntity<ErrorResponse> errorResponse){

        String errorResponseBody = errorResponse.getBody() != null ? errorResponse.getBody().toString().toString() : "Error Response None";
        String methodInfo = joinPoint.getSignature().toString();

        log.info(getLogString(methodInfo, errorResponseBody));
    }
}