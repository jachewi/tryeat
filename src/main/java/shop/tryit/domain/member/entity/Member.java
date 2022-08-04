package shop.tryit.domain.member.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.tryit.domain.order.entity.Order;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<Order> orders = new ArrayList<>();

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

    public void Password(String password) {
        this.password = password;
    }

    public void update(String name, Address address,
                       String phoneNumber, String password) {
        changeName(name);
        changeAddress(address);
        changePhone(phoneNumber);
        changePassword(password);
    }

    private void changePassword(String password) {
        this.password = password;
    }

    private void changePhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void changeAddress(Address address) {
        this.address = address;
    }

    private void changeName(String name) {
        this.name = name;
    }

    public Long addressZipcode() {
        return address.getZipcode();
    }

    public String addressStreet() {
        return address.getStreet_address();
    }

    public String addressJibeon() {
        return address.getJibeon_address();
    }

    public String addressDetail() {
        return address.getDetail_address();
    }

}
