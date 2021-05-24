package com.imadelfetouh.tweetservice.security;

import com.google.gson.Gson;
import com.imadelfetouh.tweetservice.jwt.ValidateJWTToken;
import com.imadelfetouh.tweetservice.model.jwt.UserData;
import com.imadelfetouh.tweetservice.rabbit.RabbitConfiguration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("jwt-token")).findFirst().orElse(null);
            if(cookie == null) {
                httpServletResponse.setStatus(401);
                return;
            }
            else{
                String userData = ValidateJWTToken.getInstance().getUserData(cookie.getValue());
                if(userData == null) {
                    httpServletResponse.setStatus(401);
                    return;
                }

                if(!RabbitConfiguration.getInstance().getConnection().isOpen() && !httpServletRequest.getMethod().equals("GET")) {
                    httpServletResponse.setStatus(503);
                    return;
                }

                httpServletRequest.setAttribute("userdata", userData);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }

        httpServletResponse.setStatus(401);
    }
}
