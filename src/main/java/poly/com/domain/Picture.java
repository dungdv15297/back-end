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
@Table(name = "picture")
public class Picture extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PICTURE_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID",referencedColumnName = "ROOM_ID")
    private Room room;

    @Column(name = "SRC")
    private String src;

    @Column(name = "STATUS")
    private Integer status;
}
