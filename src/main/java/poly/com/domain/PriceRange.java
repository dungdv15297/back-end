package poly.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.com.config.common.domain.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "price_range")
public class PriceRange extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PRICE_RANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "priceSeq")
    @SequenceGenerator(name = "priceSeq",sequenceName = "graduation_priceSeq",allocationSize = 1)
    private Integer id;

    @Column(name = "MIN")
    private Integer min;

    @Column(name = "MAX")
    private Integer max;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private Integer status;
}
