package app.configs;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Configuration
    public static class RestSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .formLogin().loginPage("/login")
                    // If the user have been authenticated successful it will show JSON object
                    // { "authenticated" : true } .
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            Authentication authentication)
                                throws IOException, ServletException {

                            JSONObject loginSucceed = new JSONObject();
                            loginSucceed.put("authenticated", true);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(loginSucceed.toString());
                        }
                    })

                    // If the user haven't been authenticated successful it will show JSON object
                    // { "authenticated" : false } .
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                            HttpServletResponse httpServletResponse,
                                                            AuthenticationException e)
                                throws IOException, ServletException {

                            JSONObject loginFailed = new JSONObject();
                            loginFailed.put("authenticated", false);

                            httpServletResponse.setContentType("application/json");

                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(loginFailed.toString());
                        }
                    })

                    .and().authorizeRequests()
                    // /rest/login and /rest/register urls can be viewed only by
                    // not authenticated users.
                    .antMatchers("/login", "/register").anonymous()

                    // /rest/authentication url can be viewed by all the users.
                    .antMatchers("/authentication").permitAll()

                    // /admin/** urls can be viewed only by the admins.
                    .antMatchers("/admin/**").hasRole("ADMIN")

                    // Other urls are accessed if the authenticated user have role USER.
                    .anyRequest().hasRole("USER")

                    // That is the url that is logging out the authenticated user
                    // by deleting his/her cookie for authentication from the server.
                    .and().logout().logoutSuccessUrl("/authentication")

                    // Disabling the csrf.
                    .and().csrf().disable();
        }
    }
}