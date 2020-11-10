package poly.com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ValueGenerationType;
import poly.com.config.common.domain.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "picture")
public class Picture extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pictureSeq")
    @SequenceGenerator(name = "pictureSeq",sequenceName = "graduation_pictureSeq",allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID",referencedColumnName = "ROOM_ID")
    private Room room;

    @Column(name = "SRC")
    private String src;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name ="name")
    private String name;

    @Column(name = "type")
    private String type;

}
