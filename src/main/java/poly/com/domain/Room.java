package poly.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.config.common.domain.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROOM_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "STREET_ID",referencedColumnName = "STREET_ID")
    private Street street;

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
    private Float longtitude;

    @Column(name = "LATITUDE")
    private Float latitude;


    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID",referencedColumnName = "ACCOUNT_ID")
    private Account account;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "UP_TOP_STATUS")
    private Integer upTopStatus;

    @Column(name = "LAST_UP_TOP")
    private Instant lastUpTop;

    @Column(name="TITLE")
    private String title;

    @Column(name="TYPE_OF")
    private Integer typeOfRoom;
}
