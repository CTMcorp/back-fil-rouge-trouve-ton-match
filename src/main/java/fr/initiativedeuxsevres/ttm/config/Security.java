package fr.initiativedeuxsevres.ttm.config;

import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
    ) throws Exception {
        // permet de tout nettoyer dans le navigateur (cache, cookies, local storage...)
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));

        // désactivation de CSRF
        http.csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .securityContext((context) -> context.securityContextRepository(securityContextRepository))
                // crée une nouvelle session à chaque login d'un même user (plus secure)
                .sessionManagement((session) -> session.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession))

                // logout == gestion de déconnexion
                // new HttpStatusReturningLogoutSuccessHandler() == renvoie une 200 lors d'un success
                .logout((logout) -> logout.addLogoutHandler(clearSiteData).logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .authorizeHttpRequests((requests) ->
                        // nécessite d'être authentifié
                        requests.requestMatchers("/ttm/**").authenticated()
                                .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    // TrainerService == service de gestion des users
    public AuthenticationManager authenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        // fournisseur d'authentification
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // définit l'encodeur de password
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        // définit le service de gestion des users
        daoAuthenticationProvider.setUserDetailsService(userService);
        // return la gestion du fournisseur d'authentification
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // configure la stratégie de gestion du contexte de sécurité
    @Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return SecurityContextHolder.getContextHolderStrategy();
    }

    // sauvegarde de sessions http
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}
