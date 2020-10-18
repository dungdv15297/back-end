package poly.com.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;

    private String name;

    @JsonDeserialize(using = CustomInstantDeserializer.class)
    private Instant birthday;

    private Integer gender;

    private String address;

    private String email;

    private String phone;

    private Integer balance;

    private Integer verified;

    private Integer status;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;
}
