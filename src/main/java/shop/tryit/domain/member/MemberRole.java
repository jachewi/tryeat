package shop.tryit.domain.member;

import lombok.Getter;

@Getter
public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    MemberRole(String value) {
        this.value = value;
    }
}
