package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.algaworks.algalog.domain.exception.BusinessException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Delivery {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Client client;

  @Embedded
  private Recipient recipient;

  private BigDecimal rate;

  @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
  private List<Occurrence> occurrences = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  private OffsetDateTime orderDate;

  private OffsetDateTime finalizationDate;

  public Occurrence addOccurrence(String description) {
    Occurrence occurrence = new Occurrence();
    occurrence.setDescription(description);
    occurrence.setRegistrationDate(OffsetDateTime.now());
    occurrence.setDelivery(this);

    this.getOccurrences().add(occurrence);

    return occurrence;
  }

  public void finish() {
    if(cannotBeFinished()) {
      throw new BusinessException("Entrega não pode ser finalizada");
    }

    setStatus(DeliveryStatus.FINISHED);
    setFinalizationDate(OffsetDateTime.now());
  }

  public boolean canBeFinished() {
    return DeliveryStatus.PENDING.equals(getStatus());
  }

  public boolean cannotBeFinished() {
   return !DeliveryStatus.PENDING.equals(getStatus());
  }
}
