package poly.com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "search_condition")
public class SearchCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYPE_OF_ROOM")
    private Integer typeOfRoom;

    @Column(name = "PROVINCE_ID")
    private Integer provinceId;

    @Column(name = "DISTRICT_ID")
    private Integer districtId;

    @Column(name = "PPRICE_MIN")
    private Integer priceMin;

    @Column(name = "PRICE_MAX")
    private Integer priceMax;

    @Column(name = "ACREAGE_MIN")
    private Integer acreageMin;
    @Column(name = "ACREAGE_MAX")
    private Integer acreageMax;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

}
