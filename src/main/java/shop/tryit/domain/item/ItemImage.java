package shop.tryit.domain.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage {

    @Id
    @GeneratedValue
    private Long id;

    private String originFileName; // 원본 파일명
    private String storeFileName;  // 서버에 저장 되는 파일명

    private ItemImage(String originFileName, String storeFileName) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
    }

    public ItemImage of(String originFileName, String storeFileName) {
        return new ItemImage(originFileName, storeFileName);
    }

}
