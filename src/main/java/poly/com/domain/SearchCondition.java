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
    private int id;

    @Column(name = "TYPE_OF_ROOM")
    private int typeOfRoom;

    @Column(name = "PROVINCE_ID")
    private int provinceId;

    @Column(name = "DISTRICT_ID")
    private int districtId;

    @Column(name = "PPRICE_MIN")
    private int priceMin;

    @Column(name = "PRICE_MAX")
    private int priceMax;

    @Column(name = "ACREAGE_MIN")
    private int acreageMin;
    @Column(name = "ACREAGE_MAX")
    private int acreageMax;

    @Column(name = "ACCOUNT_ID")
    private String accountId;

}
