package com.ernesto.moneyapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ernesto.moneyapi.event.RecursoCriadoEvent;
import com.ernesto.moneyapi.model.Categoria;
import com.ernesto.moneyapi.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {
  
  @Autowired
  private CategoriaRepository categoriaRepository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @GetMapping
  public List<Categoria> listar() {
    return categoriaRepository.findAll();
    // List<Categoria> categorias = categoriaRepository.findAll();
    // return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
  }

  @PostMapping
  // @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
    Categoria categoriaSalva = categoriaRepository.save(categoria);

    publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));


    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Categoria> categoria = categoriaRepository.findById(codigo);
    return !categoria.isEmpty() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
  }
}
