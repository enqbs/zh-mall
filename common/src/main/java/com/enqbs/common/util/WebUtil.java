package com.enqbs.common.util;

import com.enqbs.common.constant.Constants;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class WebUtil {

    public static void renderString(HttpServletResponse response, int httpStatus, String str) {
        try {
            response.setStatus(httpStatus);
            response.setContentType(Constants.CONTENT_TYPE_JSON);
            response.setCharacterEncoding(Constants.CHAR_ENCODE_UTF8);
            response.getWriter().write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
