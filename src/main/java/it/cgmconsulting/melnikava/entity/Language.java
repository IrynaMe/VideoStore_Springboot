package it.cgmconsulting.melnikava.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Language {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long languageId;
    @Column(nullable = false, unique = true, length = 20)
    private String languageName;

    public Language(String languageName) {
        this.languageName = languageName;
    }
}
