package com.distribuida.authors.repo;
import com.distribuida.authors.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class BookRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Book> findAll() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }

    public void create(Book p) {
        entityManager.persist(p);
    }

    public void update(Book p) {
        entityManager.merge(p);
    }

    public void delete(Long id) {
        entityManager.remove(findById(id));
    }
}
