package com.noxinfinity.pdating.Applications.Auth.ApplicationService;

import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.internal.DgsRequestData;
import com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword.ChangePasswordRequest;
import com.noxinfinity.pdating.Applications.Auth.Dto.ChangePassword.ChangePasswordResponse;
import com.noxinfinity.pdating.Applications.Auth.Dto.Login.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Dto.Login.LoginResponse;
import com.noxinfinity.pdating.Applications.Auth.Dto.Register.RegisterRequest;
import com.noxinfinity.pdating.Applications.Auth.Dto.Register.RegisterResponse;
import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthService;
import com.noxinfinity.pdating.Domains.JwtManagement.CustomUserDetailsService;
import com.noxinfinity.pdating.Domains.JwtManagement.JwtProvider;
import com.noxinfinity.pdating.Domains.UserManagement.Users;
import com.noxinfinity.pdating.Domains.UserManagement.UsersService;
import graphql.schema.DataFetchingEnvironment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthApplicationService {
    private final UsersService usersService;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthApplicationService(UsersService usersService, AuthService authService, JwtProvider jwtTokenProvider, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.usersService = usersService;
        this.authService = authService;
        this.jwtProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String rawPassword = request.password();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            System.out.println(encodedPassword);*/
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username, request.password)
            );
            Auth auth = (Auth)customUserDetailsService.loadUserByUsername(request.username);
            String token = jwtProvider.createToken(auth.getUsername(),auth.getUserId().toString(), auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

            return new LoginResponse(HttpStatus.OK,token);

        } catch (BadCredentialsException e) {
            return new LoginResponse(HttpStatus.UNAUTHORIZED,null);
        } catch (Exception e) {
            return new LoginResponse(HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
    public RegisterResponse register(RegisterRequest request){
        try {
            Auth findAuth = authService.get(request.username);
            if(findAuth != null){
                return new RegisterResponse(HttpStatus.CONFLICT,null);
            }
            Users users = new Users();
            Auth auth = new Auth(request.username, request.password, List.of("USER"));
            users.setAuth(auth);
            auth.setUser(users);
            usersService.save(users);
            return new RegisterResponse(HttpStatus.OK,null);
        } catch (Exception e) {
            return new RegisterResponse(HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
    public ChangePasswordResponse changePassword(ChangePasswordRequest request, DataFetchingEnvironment dfe){
        try {
            HttpHeaders httpHeaders = DgsContext.getRequestData(dfe).getHeaders();
            if (httpHeaders == null) {
                return new ChangePasswordResponse(HttpStatus.UNAUTHORIZED,null);
            }
            String token = httpHeaders.get("Authorization").get(0);
            String id = jwtProvider.getUserId(token);

            if (!authService.checkPassword(id,request.oldPassword)){
                return new ChangePasswordResponse(HttpStatus.FORBIDDEN,"Old password wrong");
            }
            if (!authService.updatePasswordByUserId(id, request.newPassword)){
               return new ChangePasswordResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Update failed");
            }
            return new ChangePasswordResponse(HttpStatus.OK,null);
        } catch (Exception e) {
            return new ChangePasswordResponse(HttpStatus.INTERNAL_SERVER_ERROR,"internal");
        }
    }
}
