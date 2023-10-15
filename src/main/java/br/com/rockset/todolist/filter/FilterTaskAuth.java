package br.com.rockset.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rockset.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
           var servletPath = request.getServletPath();
        if(servletPath.equals("/tasks/")){

            //Pegar a autenticação (usuario senha)    
            var authorization = request.getHeader("Authorization");
            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecode);
            String[] credenctials = authString.split(":");
            String userName = credenctials[0];
            String password = credenctials[1];
    
            // Valida Usuário
            var user = this.userRepository.findByUserName(userName);
    
            if(user == null){
                response.sendError(401);
            }else{
                //Valida senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
                    if(passwordVerify.verified){
                        // Segue viagem
                        request.setAttribute("idUser", user.getId());
                        filterChain.doFilter(request, response);
                    }else{
                    response.sendError(401);
                }
            }
        }else{
             filterChain.doFilter(request, response);
        }
    }
}