package co.com.confiar.bank_demo.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(
            name = "client_nit",
            referencedColumnName = "nit",
            nullable = false
    )
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
