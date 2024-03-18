package it.cgmconsulting.melnikava.repository;

import it.cgmconsulting.melnikava.entity.Language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query(value = "SELECT l FROM Language l WHERE languageId = :languageId")
    Optional<Language> getByLanguageId(@Param("languageId") long languageId);
}
