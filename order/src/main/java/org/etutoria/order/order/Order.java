package org.etutoria.order.order;


import jakarta.persistence.*;
import lombok.*;
import org.etutoria.order.orderline.OrderLine;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)////pour activer l'audit de la classe cad pour activer la gestion des dates de création et de dernière modification
@NoArgsConstructor
@Table(name = "customer_order")
public class Order {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(unique = true,  nullable = false)
  private String reference;

  private BigDecimal totalAmount;

  @Enumerated(EnumType.STRING)//pour que le type de paiement soit enregistré en tant que string
  private PaymentMethod paymentMethod;

  private String customerId;

  @OneToMany(mappedBy = "order")
  private List<OrderLine> orderLines;//liste des lignes de commande cad les produits commandés avec leur quantité

  @CreatedDate
  @Column(updatable = false, nullable = false)//pour que la date de création ne soit pas modifiable
  private LocalDateTime createdDate;//date de création de la commande pour audit cad pour savoir quand la commande a été créée

  @LastModifiedDate
  @Column(insertable = false)//pour que la date de dernière modification ne soit pas insérée
  private LocalDateTime lastModifiedDate;//date de dernière modification de la commande
}
