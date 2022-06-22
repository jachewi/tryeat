package shop.tryit.domain.member;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.tryit.domain.common.Address;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    @Embedded
    private Address address;

    private String phoneNumber;
    private String password;

    @Enumerated(STRING)
    private MemberRole role;

    @Builder
    private Member(String email,
                   String name,
                   Address address,
                   String phoneNumber,
                   String password,
                   MemberRole role) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

}