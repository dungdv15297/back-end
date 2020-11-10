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
@Table(name = "ward")
public class Ward extends AbstractAuditingEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "wardSeq")
    @SequenceGenerator(name = "wardSeq",sequenceName = "graduation_wardSeq",allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="DISTRICT_ID",referencedColumnName = "DISTRICT_ID")
    private District district;

    @ManyToOne
    @JoinColumn(name ="PROVINCE_ID",referencedColumnName = "PROVINCE_ID")
    private Province province;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "PREFIX")
    private String prefix;
}
