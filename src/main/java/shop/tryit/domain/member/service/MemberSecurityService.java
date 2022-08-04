package shop.tryit.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.tryit.domain.member.entity.Member;
import shop.tryit.domain.member.entity.MemberRole;
import shop.tryit.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> _member = this.repository.findByEmail(email);

        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = _member.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin@tryit.shop".equals(email)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ROLE_ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ROLE_USER.getValue()));
        }

        return new User(member.getEmail(), member.getPassword(), authorities);
    }

}
