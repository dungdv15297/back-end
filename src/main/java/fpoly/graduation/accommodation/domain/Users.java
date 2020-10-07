package fpoly.graduation.accommodation.domain;


import fpoly.graduation.accommodation.config.common.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class Users extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userSeq")
    @SequenceGenerator(name = "userSeq",sequenceName = "graduation_userSeq",allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "verified")
    private Integer verified;

    @Column(name = "status")
    private Integer status;
}
