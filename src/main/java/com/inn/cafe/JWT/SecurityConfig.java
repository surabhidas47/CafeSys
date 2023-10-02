package com.inn.cafe.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

//creates a bean whenever project starts
@Configuration
@EnableWebSecurity

//This class is used to customize the security configuration for Spring app
public class SecurityConfig extends WebSecurityConfigurerAdapter{

   // responsible for loading user details from a data source for authentication... uses the userDao
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;


    // overrides the configure method from WebSecurityConfigurerAdapter.
    // It configures the authentication manager to use the customerUserDetailsService for user authentication.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(customerUserDetailsService);
    }

    //creating a bean
    // means that no password encoding (plaintext passwords) is used
    public PasswordEncoder passwordEncoder(){
        //we will encode later
        return NoOpPasswordEncoder.getInstance();
    }

    //defines a bean for the AuthenticationManager. It is annotated with @Bean and named authenticationManagerBean
    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //sets up CORS (Cross-Origin Resource Sharing) configuration,
    // disables CSRF protection (for simplicity),
    // specifies authorization rules,
    // sets the session creation policy to stateless (
    // meaning no session will be created and each request is treated independently..
    // they don't require the server to maintain session state for each user.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .authorizeRequests()
                //bypassing apis from UserRest..besides these three, authentica te and handle exceptions
                .antMatchers("/user/login","user/signup","/user/forgotPassword")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
