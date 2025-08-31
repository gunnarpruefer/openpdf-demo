package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class PdfUser extends PanacheEntity {
    public String firstName;
    public String lastName;
    public String email;
}
