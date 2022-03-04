package com.alkemy.disney.auth.config;


import com.alkemy.disney.auth.filter.JwtRequestFilter;
import com.alkemy.disney.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
/**
 * permite que Spring encuentre y aplique automáticamente la clase a la seguridad web global
 * */
@EnableWebSecurity
/**proporciona seguridad AOP en los métodos.
 * Habilita @PreAuthorize, @PostAuthorize también es compatible con JSR-250
 * */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**Spring Security cargará los detalles del usuario para realizar la autenticación y autorización.
     * Entonces tiene una UserDetailsCustomService que necesitamos implementar.
     * La implementación de UserDetailsService se utilizará para configurar
     * DaoAuthenticationProvider por el método AuthenticationManagerBuilder.userDetailsService()
     * */
    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfiguration(@Lazy JwtRequestFilter jwtRequestFilter){
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userDetailsCustomService).passwordEncoder(passwordEncoder());
    }

    /**También necesitamos un PasswordEncoder para el DaoAuthenticationProvider.
     * Si no lo especificamos, utilizará texto sin formato.
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/auth/*").permitAll()
                .antMatchers("generos/*").permitAll()
                .antMatchers("movies/*").permitAll()
                .antMatchers("characters/*").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
