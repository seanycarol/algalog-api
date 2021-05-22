package com.algaworks.algalog.domain.model;

import com.algaworks.algalog.domain.ValidationGroups;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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

  @Valid
  @ConvertGroup(from = Default.class, to = ValidationGroups.ClientId.class)
  @NotNull
  @ManyToOne
  private Client client;

  @Valid
  @NotNull
  @Embedded
  private Recipient recipient;

  @NotNull
  private BigDecimal rate;

  @JsonProperty(access = Access.READ_ONLY)
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  @JsonProperty(access = Access.READ_ONLY)
  private LocalDateTime orderDate;

  @JsonProperty(access = Access.READ_ONLY)
  private LocalDateTime finalizationDate;
}