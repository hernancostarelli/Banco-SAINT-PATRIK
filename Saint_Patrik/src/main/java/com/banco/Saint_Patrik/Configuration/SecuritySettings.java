package com.banco.Saint_Patrik.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SecuritySettings extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.csrf().disable()  
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login") // Que formulario esta mi login 
                .loginProcessingUrl("/logincheck")
                .usernameParameter("cardNumber") // Como viajan los datos del logueo 
                .passwordParameter("password")// Como viajan los datos del logueo 
                .defaultSuccessUrl("/main") // A que URL viaja 
                .permitAll()
                .and()
                .logout() // Aca configuro la salida 
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();
              

}
}