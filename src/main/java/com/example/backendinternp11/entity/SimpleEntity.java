package com.example.backendinternp11.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "simple")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleEntity {

  @Id
  private String id;
  private String name;
  private String birth;
  private String death;
  private String profession;

}
