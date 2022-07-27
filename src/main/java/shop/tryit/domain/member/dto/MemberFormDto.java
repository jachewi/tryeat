package shop.tryit.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "비밀번호는 8~20자/영문 대소문자, 숫자, 특수문자($,@,!,%,*,#,?,&)를 1개 이상 포함해야 합니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String password2;

    @NotNull(message = "우편번호는 필수 입력 값입니다.")
    private Long zipcode; //우편번호

    @NotEmpty(message = "도로명 주소는 필수 입력 값입니다.")
    private String street_address; //도로명 주소

    @NotEmpty(message = "지번 주소는 필수 입력 값입니다.")
    private String jibeon_address; //지번 주소

    private String detail_address; //상세 주소

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$",
            message = "전화번호는 - 없이 입력해야합니다.")
    private String phone;

    private boolean admin = false;

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

