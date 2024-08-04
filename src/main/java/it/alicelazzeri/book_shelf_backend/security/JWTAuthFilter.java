package it.alicelazzeri.book_shelf_backend.security;

import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.exceptions.NotFoundException;
import it.alicelazzeri.book_shelf_backend.exceptions.UnauthorizedException;
import it.alicelazzeri.book_shelf_backend.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Fill the authorization header with a proper token.");
        }

        String accessToken = authHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        long id = jwtTools.extractIdFromToken(accessToken);

        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()){
            User user = userOptional.get();

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            throw new NotFoundException("User with id: " + id + " not found");
        }

        filterChain.doFilter(request, response);
    }

    // overridden method allowing to avoid authentication in order to use authentication and swagger services

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/auth/**", request.getServletPath()) ||
                pathMatcher.match("/v3/api-docs/**", request.getServletPath()) ||
                pathMatcher.match("/swagger-ui/**", request.getServletPath()) ||
                pathMatcher.match("/swagger-ui.html", request.getServletPath());
    }
}

