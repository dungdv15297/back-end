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
