package com.distribuida.authors.repo;

import com.distribuida.authors.db.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import java.util.List;


@ApplicationScoped
public class AuthorRepository  {

    @PersistenceContext
    EntityManager entityManager;

    public List<Author> findAll(){
        return entityManager.createQuery("SELECT a FROM Author a",Author.class).getResultList();

    }

    public Author findById(Integer id) {
        return entityManager.find(Author.class, id);
    }

    public void create(Author p) {
        entityManager.persist(p);
    }

    public void update(Author p) {
        entityManager.merge(p);
    }

    public void delete(Integer id) {
        entityManager.remove(findById(id));
    }



}
