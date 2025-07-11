package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.entities.User;
import it.unicam.cs.ids.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "certifier_requests", schema = "ids_schema")
@Data
@NoArgsConstructor
public class CertifierRequest  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApprovalStatus status;

}
