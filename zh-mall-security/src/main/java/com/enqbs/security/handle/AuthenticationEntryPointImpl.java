package com.enqbs.security.handle;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.R;
import com.enqbs.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("请求地址:'{}',认证异常,msg:'{}'", request.getRequestURI(), authException.getMessage());
        String resultJson = GsonUtil.obj2Json(R.error(HttpStatus.SC_UNAUTHORIZED, "请登录后再操作"));
        WebUtil.renderString(response, HttpStatus.SC_UNAUTHORIZED, resultJson);
    }

}
