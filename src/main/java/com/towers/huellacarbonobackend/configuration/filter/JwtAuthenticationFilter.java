package com.towers.huellacarbonobackend.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.towers.huellacarbonobackend.dto.ErrorResponse;
import com.towers.huellacarbonobackend.dto.LoginResponse;
import com.towers.huellacarbonobackend.dto.ResponseDto;
import com.towers.huellacarbonobackend.entity.data.Usuario;
import com.towers.huellacarbonobackend.exception.UserAuthenticationException;
import com.towers.huellacarbonobackend.service.security.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.towers.huellacarbonobackend.configuration.TokenJwtConfig.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Usuario usuario;
        String username = null;
        String password = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = usuario.getUsername();
            password = usuario.getPassword();
        } catch (IOException e) {
            throw new UserAuthenticationException("Error al leer los datos del usuario de la solicitud.", e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        Usuario usuario = usuarioService.findByUsername(principal.getUsername());

        if (Boolean.FALSE.equals(usuario.getEsNuevo())) {
            Claims claims = Jwts.claims()
                    .add("firstName", usuario.getNombre())
                    .add("lastName", usuario.getApellido())
                    .add("company_id", usuario.getEmpresa().getId())
                    .add("company", usuario.getEmpresa().getRazonSocial())
                    .add("authorities", new ObjectMapper().writeValueAsString(principal.getAuthorities()))
                    .build();
            String token = Jwts.builder()
                    .subject(usuario.getUsername())
                    .claims(claims)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 7200000)) // 2 horas
                    .signWith(SECRET_KEY)
                    .compact();
            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
            LoginResponse loginResponse = new LoginResponse(
                    "Has iniciado sesión con éxito.",
                    usuario.getEsNuevo(),
                    token
            );
            ResponseDto responseDto = new ResponseDto(loginResponse, true);
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200);
        } else {
            LoginResponse loginResponse = new LoginResponse(
                    "El usuario es nuevo.",
                    usuario.getEsNuevo(),
                    null
            );
            ResponseDto responseDto = new ResponseDto(loginResponse, true);
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseDto errorResponse = null;

        if (failed instanceof BadCredentialsException) {
            errorResponse = generateErrorResponse("Username o Password incorrectos.");
        }

        if (failed instanceof DisabledException) {
            errorResponse = generateErrorResponse("No tiene acceso al sistema.");
        }

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(401);
    }

    private ResponseDto generateErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse("Error en la autenticación", message);
        return new ResponseDto(errorResponse, false);
    }
}
