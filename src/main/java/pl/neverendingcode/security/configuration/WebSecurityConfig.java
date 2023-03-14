package pl.neverendingcode.security.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.neverendingcode.security.jwt.AuthEntryPoint;
import pl.neverendingcode.security.jwt.AuthTokenFilter;
import pl.neverendingcode.security.service.UserDetailsServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthEntryPoint unauthorizedHandler;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthTokenFilter authTokenFilter;

    private static final List<String> PERMITTED_URLS = Arrays.asList(
            "/api/auth/user/**",
            "/api/car/get-all",
            "/api/car/get/**",
            "/api/car/add/**"
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(PERMITTED_URLS.toArray(new String[0])).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(getAllowedOrigins());
        corsConfiguration.setAllowedMethods(getAllowedMethods());
        corsConfiguration.setAllowedHeaders(getAllowedHeaders());
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private List<String> getAllowedMethods() {
        String allowedMethods = System.getenv("ALLOWED_METHODS");
        if (allowedMethods != null && !allowedMethods.isEmpty()) {
            return Arrays.asList(allowedMethods.split(","));
        } else {
            return Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH");
        }
    }

    private List<String> getAllowedOrigins() {
        String allowedOrigins = System.getenv("ALLOWED_ORIGINS");
        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
            return Arrays.asList(allowedOrigins.split(","));
        } else {
            return Collections.singletonList("http://localhost:4200");
        }
    }

    private List<String> getAllowedHeaders() {
        String allowedHeaders = System.getenv("ALLOWED_HEADERS");
        if (allowedHeaders != null && !allowedHeaders.isEmpty()) {
            return Arrays.asList(allowedHeaders.split(","));
        } else {
            return Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "Access-Control");
        }
    }

}