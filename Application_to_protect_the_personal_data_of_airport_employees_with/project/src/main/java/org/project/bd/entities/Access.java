package org.project.bd.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "data_access")
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private int accessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "data_category")
    private String dataCategory;

    @Column(name = "access_level")
    private String accessLevel;

    @Column(name = "access_start_date")
    @Temporal(TemporalType.DATE)
    private Date accessStartDate;

    @Column(name = "access_end_date")
    @Temporal(TemporalType.DATE)
    private Date accessEndDate;

    // getters and setters
}
