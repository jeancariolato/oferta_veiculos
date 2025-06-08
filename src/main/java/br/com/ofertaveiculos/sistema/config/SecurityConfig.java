package br.com.ofertaveiculos.sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Permite acesso público a estas URLs
                .requestMatchers("/", "/categoria/**", "/veiculo/**", "/pesquisar", "/css/**", "/js/**", "/images/**").permitAll()
                // Qualquer requisição para /admin/** requer autenticação
                .requestMatchers("/admin/**").authenticated()
                // Qualquer outra requisição, permite
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login").permitAll() // Define uma página de login customizada (opcional)
            )
            .logout(logout -> logout.permitAll());

        return http.build();
    }
    
    // Para simplificar, vamos usar um usuário em memória.
    // O ideal é implementar UserDetailsService para buscar do banco de dados.
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}