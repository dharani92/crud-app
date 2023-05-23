package com.interview.prep.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255)
    private String title;
    @Column(length = 255)
    private String author;
    private int editionYear;
    @Column(length = 255)
    private String isbn;
    @Column(length = 255)
    private String publisher;

}

