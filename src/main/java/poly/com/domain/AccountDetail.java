package poly.com.domain;


import poly.com.client.dto.accountDetail.AccountDetailDto;
import poly.com.config.common.domain.AbstractAuditingEntity;
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
@Table(name = "account_detail")
public class AccountDetail extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACCOUNT_DETAIL_ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BIRTHDAY")
    private Instant birthday;

    @Column(name = "GENDER")
    private Integer gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "BALANCE")
    private Integer balance;

    @Column(name = "STATUS")
    private Integer status;

    public AccountDetailDto toDto() {
        return AccountDetailDto.builder()
                .id(id)
                .name(name)
                .birthday(birthday.toString())
                .gender(gender)
                .address(address)
                .email(email)
                .phone(phone)
                .balance(balance)
                .status(status)
                .updated(getLastModifiedDate().toString())
                .build();
    }

}
