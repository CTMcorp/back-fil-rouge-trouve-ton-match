package fr.initiativedeuxsevres.ttm.config;

import fr.initiativedeuxsevres.ttm.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager
            //SecurityContextRepository securityContextRepository
    ) throws Exception {
        // permet de tout nettoyer dans le navigateur (cache, cookies, local storage...)
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));

        // désactivation de CSRF
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors
                        .configurationSource(myWebsiteConfigurationSource())
                )
                .authenticationManager(authenticationManager)
                //.securityContext((context) -> context.securityContextRepository(securityContextRepository))
                // crée une nouvelle session à chaque login d'un même user (plus secure)
                //.sessionManagement((session) -> session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession))

                // logout == gestion de déconnexion
                // new HttpStatusReturningLogoutSuccessHandler() == renvoie une 200 lors d'un success
                .logout((logout) -> logout.addLogoutHandler(clearSiteData).logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .authorizeHttpRequests((requests) ->
                        // nécessite d'être authentifié
                        requests.requestMatchers("/ttm/**").authenticated()
                                .anyRequest().permitAll());

        // gestion des exceptions d'authentification
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        // ajout du filtre jwt avant le filtre d'auth par username et password
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // config cors pour permettre les requetes depuis l'origine spécifiée
    UrlBasedCorsConfigurationSource myWebsiteConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // autorisation des requetes depuis cette origine
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // autorise toutes les méthodes (GET, POST...)
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // applique la config à toutes les requetes
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    // gestionnaire de l'authentification
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


// TrainerService == service de gestion des users
    /*public AuthenticationManager authenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        // fournisseur d'authentification
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // définit l'encodeur de password
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        // définit le service de gestion des users
        daoAuthenticationProvider.setUserDetailsService(userService);
        // return la gestion du fournisseur d'authentification
        return new ProviderManager(daoAuthenticationProvider);
    }*/

// configure la stratégie de gestion du contexte de sécurité
    /*@Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }*/

// sauvegarde de sessions http
    /*@Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }*/