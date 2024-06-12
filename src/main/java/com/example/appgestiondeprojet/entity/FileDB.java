package com.example.appgestiondeprojet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class FileDB implements Serializable {


	private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  
  private Long id;
  
  @NonNull
  private String name;
  
  @NonNull
  private String type;
  
  @Lob
  @NonNull
  @Column(columnDefinition="LONGBLOB")
  private byte[] data;
  
  
  
  @OneToOne (mappedBy = "file")
  @JsonIgnore
  private User user;
  



  
  
 
}