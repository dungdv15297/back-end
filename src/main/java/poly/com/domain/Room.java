package poly.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.config.common.domain.AbstractAuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "province")
public class Room extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROOM_ID")
    private String id;

    @Column(name = "PRICE_RANGE_ID")
    private Integer priceRangeId;

    @Column(name = "ACREAGE_RANGE_ID")
    private Integer acreageRangId;

    @Column(name = "STREET_ID")
    private Integer streetId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE_MIN")
    private Integer priceMin;

    @Column(name = "PRICE_MAX")
    private Integer priceMax;

    @Column(name = "ACREAGE_MIN")
    private Integer acreageMin;

    @Column(name = "ACREAGE_MAX")
    private Integer acreageMax;

    @Column(name = "LONGITUDE")
    private Integer longtitude;

    @Column(name = "LATITUDE")
    private Integer latitude;

    @Column(name = "ACCOUNT_ID")
    private Integer accountId;

    @Column(name = "STATUS")
    private Integer status;
}
