package io.github.mat3e.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {

    private LocalDateTime created_on;

    private LocalDateTime updated_on;

    @PrePersist
    private void prePersist(){
        created_on = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        updated_on = LocalDateTime.now();
    }

}
