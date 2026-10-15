package com.example.appareil.service;

import com.example.appareil.entity.Categorie;
import com.example.appareil.repository.CategorieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

/** Centralise les op?rations sur les cat?gories persist?es dans MySQL. */
@Service
public class CategorieService {

  @Autowired private CategorieRepository categorieRepository;

  public List<Categorie> findAll() {
    return categorieRepository.findAll();
  }

  public Categorie save(Categorie categorie) {
    return categorieRepository.save(categorie);
  }

  public Optional<Categorie> findById(Long id) {
    return categorieRepository.findById(id);
  }

  public void deleteById(Long id) {
    categorieRepository.deleteById(id);
  }

  public void update(Long id, Categorie catInfo) {
    Categorie categorie =
        categorieRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("cat not found with id " + id));
    categorie.setLabel(catInfo.getLabel());
    categorieRepository.save(categorie);
  }
}
