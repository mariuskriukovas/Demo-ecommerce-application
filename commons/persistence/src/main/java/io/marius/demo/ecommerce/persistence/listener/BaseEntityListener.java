package io.marius.demo.ecommerce.persistence.listener;

import io.marius.demo.ecommerce.persistence.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

public class BaseEntityListener {
    private static final Log log = LogFactory.getLog(BaseEntityListener.class);

    @PrePersist
    private void beforeAnyUpdate(BaseEntity entity) {
        if (entity.getUid() == null) {
            entity.setUid(UUID.randomUUID().toString());
            log.info(String.format("Created entity :%s with uid: %s", entity.getClass(), entity.getUid()));
        }

    }
}
