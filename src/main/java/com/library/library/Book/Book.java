package com.library.library.Book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Integer ID;
    private String title;
    private String author;
    private Date publicationYear;
    @Column(unique = true)
    private Integer ISBN;
    private Boolean available;
}
