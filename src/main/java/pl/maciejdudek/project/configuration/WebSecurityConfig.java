package pl.maciejdudek.project.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pl.maciejdudek.project.configuration.jtw.JsonObjectAuthenticationFilter;
import pl.maciejdudek.project.configuration.jtw.JwtAuthenticationFilter;
import pl.maciejdudek.project.configuration.jtw.RestAuthenticationFailureHandler;
import pl.maciejdudek.project.configuration.jtw.RestAuthenticationSuccessHandler;
import pl.maciejdudek.project.services.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;
    private final String secret;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper,
                             RestAuthenticationSuccessHandler successHandler,
                             RestAuthenticationFailureHandler failureHandler,
                             @Value("${jwt.secret}") String secret) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                // H2 DB
                .antMatchers("/h2-console/**").permitAll()
                // register
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                // API
                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/api/**").permitAll()
                // stateless session
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // H2 console from browser
                .and()
                .headers().frameOptions().disable()
                // authentication filter
                .and()
                .addFilter(authenticationFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userDetailsService, secret))
                // handling exception
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter authenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        return authenticationFilter;
    }
}
