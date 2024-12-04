package fr.initiativedeuxsevres.ttm.filter;

import fr.initiativedeuxsevres.ttm.config.JwtTokenProvider;
import fr.initiativedeuxsevres.ttm.domain.services.servicesimpl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImpl userService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, @Lazy UserServiceImpl userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // récup le token jwt de la requete
        String token = getTokenFromRequest(request);

        // si le token est présent et valide
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // recup username à partir du token
            String username = jwtTokenProvider.getUsername(token);
            // charge details du user
            UserDetails userDetails = userService.loadUserByUsername(username);
            // création d'un objet d'authentication
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            // ajoute des détails supplémentaires à l'authentication
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // définit l'auth dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // continue le filtrage de la requete
        filterChain.doFilter(request, response);
    }

    // méthode pour extraire le token du header de la requete
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // si le header contient un token et commence par Bearer
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // extrait le token sans Bearer
            return bearerToken.substring(7);
        }
        // return null si aucun token trouvé
        return null;
    }
}
