package com.eigaas.imf.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "missions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mission extends AbstractAuditableEntity<Spy, Long> implements Serializable {

    @Column
    private String codename;

    @Column
    private Spy spy;

}
