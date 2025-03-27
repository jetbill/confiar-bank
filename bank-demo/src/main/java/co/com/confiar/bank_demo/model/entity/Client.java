package co.com.confiar.bank_demo.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients")
public class Client {
@Id
@Column(name = "nit", nullable = false, unique = true)
private String nit;

@Column(name = "name", nullable = false)
private String name;

@Column(name = "entry_date", nullable = false)
private LocalDate entryDate;

@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Account> accounts;
}
