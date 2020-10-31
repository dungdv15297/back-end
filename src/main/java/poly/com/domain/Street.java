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
@Table(name = "street")
public class Street extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "STREET_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "streetSeq")
    @SequenceGenerator(name = "streetSeq",sequenceName = "graduation_streetSeq",allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="DISTRICT_ID",referencedColumnName = "DISTRICT_ID")
    private District district;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PREFIX")
    private String prefix;

    @Column(name = "PROVINCE_ID")
    private Integer provinceId;
}
