package com.app.MyAppBackend.security.jwt;

import com.app.MyAppBackend.services.user.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    // armamos el filter pasando el header a la peticion
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // si no lo tiene
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7); // obtenemos el JWT
        String username = jwtService.extractUsername(jwt); // obtenemos el username
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){ // verfica que usuario no este authenticado
            UserDetails userDetails=myUserDetailService.loadUserByUsername(username);
            if(userDetails!=null && jwtService.isTokenValid(jwt)){ // verficamos que el token es valid
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // trackeamos quien esta logueando en el sistema
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request,response);

    }
}