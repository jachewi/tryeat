package shop.tryit.domain.item;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ItemImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    private String originFileName; // 원본 파일명
    private String storeFileName;  // 서버에 저장되는 파일명

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private ItemImage(String originFileName, String storeFileName) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
    }

    public ItemImage of(String originFileName, String storeFileName) {
        return new ItemImage(originFileName, storeFileName);
    }

}
