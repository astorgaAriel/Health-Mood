package cl.healthmood.Health.Mood.security.jwt;

import cl.healthmood.Health.Mood.security.service.CustomerDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomerDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        String method = request.getMethod();

        // 🔓 Si es una ruta pública, continuar sin procesar JWT
        if (isPublicPath(path, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔐 Solo procesar JWT para rutas que requieren autenticación
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String username = jwtUtils.extractEmail(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtils.validateToken(token, username)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                // Log del error pero no interrumpir la cadena
                System.err.println("Error procesando JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Determina si una ruta es pública y no requiere autenticación
     */
    private boolean isPublicPath(String path, String method) {
        // Rutas de autenticación siempre públicas
        if (path.startsWith("/api/auth/")) {
            return true;
        }

        // Rutas públicas explícitas
        if (path.startsWith("/api/public/")) {
            return true;
        }

        // Solo GET requests son públicos para estos endpoints
        if ("GET".equals(method)) {
            return path.startsWith("/api/products/") ||
                    path.startsWith("/api/posts/") ||
                    path.startsWith("/api/images/") ||
                    path.startsWith("/api/categories/");
        }

        return false;
    }
}