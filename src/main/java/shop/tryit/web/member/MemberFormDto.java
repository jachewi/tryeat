package shop.tryit.web.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberFormDto {

    //@NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    //@NotBlank(message = "이메일은 필수 입력 값입니다.")
//    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    //@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password1;

    //@NotEmpty(message = "비밀번호가 일치하지 않습니다.")
    private String password2;

    //@NotEmpty(message = "주소는 필수 입력 값입니다.")
    private Long zipcode; //우편번호
    private String street_address; //도로명 주소
    private String jibeon_address; //지번 주소
    private String detail_address; //상세 주소

    private String phone;

    @Builder
    public MemberFormDto(String name, String email,
                         String password1, String password2,
                         Long zipcode, String street_address,
                         String jibeon_address, String detail_address, String phone) {
        this.name = name;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.zipcode = zipcode;
        this.street_address = street_address;
        this.jibeon_address = jibeon_address;
        this.detail_address = detail_address;
        this.phone = phone;
    }

}

