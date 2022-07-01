package shop.tryit.domain.member;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class KakaoMember {
    @Id
    Long id;
    String email;
    String nickname;

    @Builder
    public KakaoMember(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

}
