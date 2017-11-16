package app.configs;

import app.security.JwtAuthenticationEntryPoint;
import app.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public CorsFilter corsFilterBean() throws Exception {
        return new CorsFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // /login and /register urls can be viewed only by
                // not authenticated users.
                .antMatchers("/register", "/login").anonymous()

                // /authentication url can be viewed by all the users.
                .antMatchers("/authenticated", "/categories/**", "/products/**", "/res/**").permitAll()

                // /admin/** urls can be viewed only by the admins.
                .antMatchers("/admin/**").hasRole("ADMIN")

                // Other urls are accessed if the authenticated user have role USER.
                .anyRequest().hasRole("USER");

        http
                .addFilterBefore(corsFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();
    }

}