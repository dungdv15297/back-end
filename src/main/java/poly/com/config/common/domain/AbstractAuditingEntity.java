package poly.com.config.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractAuditingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @javax.validation.constraints.NotEmpty(message = "validation.constraints.NotEmpty")
    @org.springframework.data.annotation.CreatedBy
    @javax.persistence.Column(name = "CREATED_BY", nullable = false, length = 30, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.lang.String createdBy;
    @javax.validation.constraints.NotNull(message = "validation.constraints.NotNull")
    @org.springframework.data.annotation.CreatedDate
    @javax.persistence.Column(name = "CREATED_DATE", updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.time.Instant createdDate;
    @org.springframework.data.annotation.LastModifiedBy
    @javax.persistence.Column(name = "UPDATED_BY", length = 30)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.lang.String lastModifiedBy;
    @org.springframework.data.annotation.LastModifiedDate
    @javax.persistence.Column(name = "UPDATE_DATE")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.time.Instant lastModifiedDate;
}
