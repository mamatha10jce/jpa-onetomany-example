package com.code4fun.jpaonetomanymapping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    @Size(max = 250)
    @Column(unique = true)
    private String title;
}
