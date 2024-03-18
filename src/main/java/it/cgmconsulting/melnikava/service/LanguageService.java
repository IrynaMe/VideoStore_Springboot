package it.cgmconsulting.melnikava.service;

import it.cgmconsulting.melnikava.entity.Language;
import it.cgmconsulting.melnikava.repository.LanguageRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Optional<Language> getByLanguageId(long languageId) {
        return languageRepository.getByLanguageId(languageId);
    }
}
