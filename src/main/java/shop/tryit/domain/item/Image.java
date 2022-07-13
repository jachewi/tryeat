package shop.tryit.domain.item;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    private String originFileName; // 원본 파일명
    private String storeFileName;  // 서버에 저장되는 파일명

    @Enumerated(STRING)
    private ImageType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Image(String originFileName, String storeFileName, ImageType type) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
        this.type = type;
    }

    public static Image of(String originFileName, String storeFileName, ImageType type) {
        return new Image(originFileName, storeFileName, type);
    }

    // Item 정보 저장
    public void setItem(Item item) {
        this.item = item;
    }

    public void update(Image newImage) {
        this.originFileName = newImage.getOriginFileName();
        this.storeFileName = newImage.getStoreFileName();
        this.type = newImage.getType();
    }

    public Long getItemId() {
        return item.getId();
    }

}
