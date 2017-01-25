package br.com.sgi.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GenericDAO<T> {

    private EntityManagerFactory entityManagerFactory = null;

    public EntityManagerFactory getEMF() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            entityManagerFactory = Persistence.createEntityManagerFactory("sgiPU");
        }
        return entityManagerFactory;
    }

    public boolean persist(T obj) {
        EntityManager entityManager = getEMF().createEntityManager();
        boolean is = false;
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
            is = true;
        } catch (Exception e) {
            is = false;
            entityManager.getTransaction().rollback();
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return is;
    }

    public boolean save(T obj) {
        EntityManager entityManager = getEMF().createEntityManager();
        boolean is = false;
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
            is = true;
        } catch (Exception e) {
            is = false;
            entityManager.getTransaction().rollback();
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return is;
    }

    public boolean remove(T obj) {
        EntityManager entityManager = getEMF().createEntityManager();
        boolean is = false;
        try {
            entityManager.getTransaction().begin();
            if (!entityManager.contains(obj)) {
                obj = entityManager.merge(obj);
            }
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
            is = true;
        } catch (Exception e) {
            is = false;
            entityManager.getTransaction().rollback();
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return is;
    }

    public boolean remove(Class<T> classe, Integer id) {
        EntityManager entityManager = getEMF().createEntityManager();
        boolean is = false;
        try {
            entityManager.getTransaction().begin();
            T obj = entityManager.find(classe, id);
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
            is = true;
        } catch (Exception e) {
            is = false;
            
            if(entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return is;

    }

    public T findById(Class<T> classe, Integer id) {
        EntityManager entityManager = getEMF().createEntityManager();
        T obj = null;
        try {
            obj = entityManager.find(classe, id);
        } catch (Exception e) {
            obj = null;
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return obj;
    }

    public List<T> findAll(String classe, String orderField) {
        EntityManager entityManager = getEMF().createEntityManager();
        List<T> objs = new ArrayList<T>();
        try {
            String jpql = "SELECT obj FROM " + classe + " obj ORDER BY obj." + orderField;
            Query query = entityManager.createQuery(jpql);
            objs = query.getResultList();
        } catch (Exception e) {
            objs = null;
            e.printStackTrace();
            System.err.println("Erro: " + e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
        return objs;
    }

    public int count(String classe) {
        EntityManager entityManager = getEMF().createEntityManager();
        try {
            String jpql = "SELECT COUNT(obj) FROM " + classe + " AS obj";
            return ((Long) entityManager.createQuery(jpql).getSingleResult()).intValue();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return 0;
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
