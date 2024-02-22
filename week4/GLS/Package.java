package GLS;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "deliver_status")
    private DeliverStatus deliverStatus;
    public enum DeliverStatus {
        PENDING,
        IN_TRANSIT,
        DELIVERED
    }

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "aPackage")
    private Set<Shipment> shipments = new HashSet<>();


    @PrePersist
    public void onPrePersist() {
        this.setCreatedDate(LocalDateTime.now());
        this.setLastUpdated(LocalDateTime.now());

    }

    @PreUpdate
    public void onPreUpdate() {
        this.setLastUpdated(LocalDateTime.now());
    }
}