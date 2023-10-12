package com.enqbs.security.handle;

import com.enqbs.common.util.GsonUtil;
import com.enqbs.common.util.R;
import com.enqbs.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("请求地址:'{}',鉴权异常,msg:'{}'", request.getRequestURI(), accessDeniedException.getMessage());
        String resultJson = GsonUtil.obj2Json(R.error(HttpStatus.SC_FORBIDDEN, "权限不足,拒绝访问"));
        WebUtil.renderString(response, HttpStatus.SC_FORBIDDEN, resultJson);
    }

}
