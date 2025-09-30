package com.bunnet.year4.controller;

import com.bunnet.year4.config.jwt.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService; // This will be your CustomUserDetailsService
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    // DTOs for request and response
    record AuthenticationRequest(String username, String password) {

    }

    record AuthenticationResponse(String token) {

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response // <-- ADD THIS
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        final String jwt = jwtService.generateToken(userDetails);

        // --- CREATE AND SET THE COOKIE ---
        Cookie jwtCookie = new Cookie("jwt-token", jwt);
        jwtCookie.setHttpOnly(true); // Makes the cookie inaccessible to JavaScript (more secure)
        jwtCookie.setSecure(false); // Set to true if you are using HTTPS
        jwtCookie.setPath("/"); // The cookie is valid for all paths
        jwtCookie.setMaxAge(24 * 60 * 60); // 1 day in seconds

        response.addCookie(jwtCookie); // Add the cookie to the response

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
