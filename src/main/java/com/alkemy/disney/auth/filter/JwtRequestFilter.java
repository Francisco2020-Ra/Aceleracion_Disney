package com.alkemy.disney.auth.filter;

import com.alkemy.disney.auth.service.JwtUtils;
import com.alkemy.disney.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/** OncePerRequestFilter realiza una única ejecución por cada petición a nuestra API.
 * Proporciona un doFilterInternal() método que implementaremos analizando y validando JWT,
 * cargando los detalles del usuario (usando UserDetailsCustomService),
 * verificando la autorización (usando UsernamePasswordAuthenticationToken).
 * */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsCustomService.loadUserByUsername(username);


            if(jwtUtil.validateToken(jwt, userDetails)){
                /**UsernamePasswordAuthenticationToken
                 * obtiene {nombre de usuario, contraseña} de la Solicitud de inicio de sesión,
                 * AuthenticationManager lo usará para autenticar una cuenta de inicio de sesión.
                 * */
                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                userDetails.getPassword(), userDetails.getAuthorities());
                /**AuthenticationManager tiene un DaoAuthenticationProvider
                 * (con la ayuda de UserDetailsService & PasswordEncoder)
                 * para validar el UsernamePasswordAuthenticationTokenobjeto.
                 * Si tiene éxito, AuthenticationManager devuelve un objeto de autenticación completo
                 * (incluidas las autorizaciones otorgadas).
                 * */

                authReq.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Authentication auth = authenticationManager.authenticate(authReq);

                SecurityContextHolder.getContext().setAuthentication(authReq);

            }
        }
        chain.doFilter(request, response);
    }
}
