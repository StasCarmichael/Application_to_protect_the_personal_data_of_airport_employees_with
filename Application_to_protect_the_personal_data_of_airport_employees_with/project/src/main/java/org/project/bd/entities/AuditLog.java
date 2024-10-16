package org.project.bd.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private int auditId;

    @Column(name = "action")
    private String action;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "record_id")
    private int recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(name = "old_value")
    private String oldValue;

    // getters and setters
}
