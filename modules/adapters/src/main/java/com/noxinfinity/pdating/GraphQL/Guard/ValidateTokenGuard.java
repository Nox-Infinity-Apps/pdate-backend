package com.noxinfinity.pdating.GraphQL.Guard;

import com.noxinfinity.pdating.Applications.Auth.JWTServiceApp;
import com.noxinfinity.pdating.Exception.UnauthorizedException;
import com.noxinfinity.pdating.graphql.types.UserFromGoogle;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ValidateTokenGuard {

    @Autowired
    private JWTServiceApp jwtService;

    @Before("@annotation(validateToken)")
    public void validateToken(ValidateToken validateToken) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Không có ngữ cảnh yêu cầu hiện tại.");
        }

        HttpServletRequest request = attributes.getRequest();

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Header Authorization không hợp lệ hoặc bị thiếu.");
        }

        String token = authorizationHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "Token không hợp lệ.");
        }

        UserFromGoogle decodedToken = jwtService.decodeToken(token);
        request.setAttribute("decodedToken", decodedToken);
    }
}
