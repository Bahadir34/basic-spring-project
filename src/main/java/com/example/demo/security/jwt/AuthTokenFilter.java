package com.example.demo.security.jwt;

import com.example.demo.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // !!! requestin icinden JWT token çekiliyor.
        String jwt = parseJwt(request);
        //System.out.println("jwt : " + jwt);
        //System.out.println("jwtUtils.validateJwtToken(jwt)" + jwtUtils.validateJwtToken(jwt));
        // !!! Validate JWT Token
        if(jwt != null && jwtUtils.validateJwtToken(jwt))
        {
            try {
                // !!! username bilgisini JWT tokenden çekiyoruz.
                System.out.println("1");
                String userName = jwtUtils.getUserNameFromJwtToken(jwt);
                System.out.println("tokenden çekilen username : " + userName);
                System.out.println("2");
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                System.out.println("3");
                request.setAttribute("userName" , userName);
                System.out.println("4");

                // Security Context'e authenticate edilen kullanıcı gönderiliyor.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails , null , userDetails.getAuthorities()
                );

                System.out.println("5");

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("6");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("7");
            } catch (UsernameNotFoundException e) {
                LOGGER.error("Cannot set user authentication" , e);
            }
        }

        filterChain.doFilter(request,response);

    }

    private String parseJwt(HttpServletRequest request)
    {
        String headerAuth =  request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer"))
        {
            return headerAuth.substring(7);
        }

        return null;
    }
}
