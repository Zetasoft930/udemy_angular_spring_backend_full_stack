package ao.co.celsodesousa.helpDesk.security;

import ao.co.celsodesousa.helpDesk.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {

    private Integer id;
    private String email;
    private String senha;

    public  UserSecurity(Integer id,String email, String senha, Set<Perfil> perfils)
    {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfils.stream().map( x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toSet());

    }
    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
public Integer getId()
{
    return id;
}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
