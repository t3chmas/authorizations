package com.github.hasable.authorizations.storage.common;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@MappedSuperclass
public abstract class Traceable {

  /** Time when the object was created */
  Instant createdAt;

  /**
   * Time when the object was last updated. This field is never null and set to createAt for
   * convenience.
   */
  Instant modifiedAt;

  @PrePersist
  protected void preCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.modifiedAt = now;
  }

  @PreUpdate
  protected void preSave() {
    this.modifiedAt = Instant.now();
  }
}
