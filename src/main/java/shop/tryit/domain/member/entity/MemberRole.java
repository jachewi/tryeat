package shop.tryit.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;

    MemberRole(String value) {
        this.value = value;
    }
}
