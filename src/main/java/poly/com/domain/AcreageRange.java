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
@Table(name = "acreage_range")
public class AcreageRange extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACREAGE_RANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "acreageSeq")
    @SequenceGenerator(name = "acreageSeq",sequenceName = "graduation_acreageSeq",allocationSize = 1)
    private Integer id;

    @Column(name = "MIN")
    private Integer min;

    @Column(name = "MAX")
    private Integer max;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "DESCRIPTION")
    private String description;
}
