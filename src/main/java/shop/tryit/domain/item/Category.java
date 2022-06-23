package shop.tryit.domain.item;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "category_id")
    private Long id; // 카테고리 식별자

    private String name; // 카테고리명 [CAT, DOG]

    private Category(String name) {
        this.name = name;
    }

    public static Category from(String name) {
        return new Category(name);
    }

}
