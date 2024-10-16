package org.project.bd.entities;

import javax.persistence.*;

@Entity
@Table(name = "data_categories")
public class DataCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "sensitivity_level")
    private String sensitivityLevel;

    // getters and setters
}
