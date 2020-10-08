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
@Table(name = "street")
public class Street extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "STREET_ID")
    private Integer id;

    @Column(name = "DISTRICT_ID")
    private Integer districtId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Integer status;
}
