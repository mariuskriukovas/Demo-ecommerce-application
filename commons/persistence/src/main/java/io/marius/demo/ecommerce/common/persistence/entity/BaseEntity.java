package io.marius.demo.ecommerce.common.persistence.entity;

import io.marius.demo.ecommerce.common.persistence.listener.BaseEntityListener;
import jakarta.persistence.*;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "uid", length = 40)
  private String uid;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }
}
