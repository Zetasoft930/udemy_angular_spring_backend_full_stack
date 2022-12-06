package ao.co.celsodesousa.helpDesk.services.security;

import ao.co.celsodesousa.helpDesk.domain.Pessoa;
import ao.co.celsodesousa.helpDesk.repository.PessoaRepository;
import ao.co.celsodesousa.helpDesk.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      Optional<Pessoa> optionalPessoa = pessoaRepository.findByEmail(email);

      if(optionalPessoa.isPresent())
      {
          Pessoa pessoa = optionalPessoa.get();
          return new UserSecurity(pessoa.getId(),pessoa.getEmail(),pessoa.getSenha(),pessoa.getPerfils());

      }
        throw new UsernameNotFoundException("Usuario nao encontrado");
    }
}
