package shop.tryit.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shop.tryit.domain.common.Address;
import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDB {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    //TODO: Mysql 구축 후 삭제 예정(관리자 같은 경우 DB에 직접 INSERT 해서 관리하기로 결정)
    @PostConstruct
    public void initDB() {
        log.info("initialize DB");
        initTestAdmin();
        initTestUser();

    }

    private void initTestUser() {
        Address address = Address.builder()
                .zipcode(8793L)
                .street_address("서울 관악구 낙성대역3길 3")
                .jibeon_address("서울 관악구 봉천동 1630-11")
                .detail_address("지하 1층")
                .build();

        repository.save(Member.builder()
                .email("test123@gmail.com")
                .name("홍길동")
                .password(passwordEncoder.encode("Test123@"))
                .address(address)
                .phoneNumber("01011112222")
                .role(MemberRole.ROLE_USER)
                .build());

    }

    private void initTestAdmin() {
        Address address = Address.builder()
                .zipcode(13529L)
                .street_address("경기 성남시 분당구 판교역로 166")
                .jibeon_address("경기 성남시 분당구 백현동 532")
                .detail_address("카카오 판교 아지트 1층")
                .build();

        repository.save(Member.builder()
                .email("admin@tryit.shop")
                .name("관리자")
                .password(passwordEncoder.encode("Admin123@"))
                .address(address)
                .phoneNumber("01012346578")
                .role(MemberRole.ROLE_ADMIN)
                .build());
    }

}
