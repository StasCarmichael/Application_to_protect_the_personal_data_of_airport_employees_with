package org.project.bd.dao;

import org.project.bd.entities.Employee;
import org.project.bd.entities.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LoginCredentialsDAO {

    private static final String PERSISTENCE_UNIT_NAME = "hibernate-demo";
    private EntityManagerFactory emf;


    public LoginCredentialsDAO() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void close() {
        emf.close();
    }

    public LoginCredentials findById(int id) {
        EntityManager em = emf.createEntityManager();
        LoginCredentials credentials = em.find(LoginCredentials.class, id);
        em.close();
        return credentials;
    }

    public void save(LoginCredentials loginCredentials) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(loginCredentials);
        em.getTransaction().commit();
        em.close();
    }

    public void update(LoginCredentials loginCredentials) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(loginCredentials);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, id);
        if (employee != null) {
            em.remove(employee);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<LoginCredentials> findAll() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM LOGIN_CREDENTIALS e");
        List<LoginCredentials> credentials = query.getResultList();
        em.close();
        return credentials;
    }





    public LoginCredentials findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<LoginCredentials> criteriaQuery = builder.createQuery(LoginCredentials.class);

        Root<LoginCredentials> root = criteriaQuery.from(LoginCredentials.class);
        criteriaQuery.select(root).where(builder.equal(root.get("username"), username));
        LoginCredentials ret= em.createQuery(criteriaQuery).getSingleResult();
        em.close();
        return ret;
    }
    public List<LoginCredentials> getAllLoginCredentials() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CriteriaQuery<LoginCredentials> criteriaQuery = em.getCriteriaBuilder().createQuery(LoginCredentials.class);
        criteriaQuery.from(LoginCredentials.class);
        List<LoginCredentials> ret=  em.createQuery(criteriaQuery).getResultList();
        em.close();
        return ret;
    }

    public LoginCredentials getCredentialsByEmployee(Employee employee) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<LoginCredentials> criteriaQuery = builder.createQuery(LoginCredentials.class);

        Root<LoginCredentials> root = criteriaQuery.from(LoginCredentials.class);
        criteriaQuery.select(root).where(builder.equal(root.get("employee"), employee));
        LoginCredentials ret= em.createQuery(criteriaQuery).getSingleResult();

        em.close();
        return ret;
    }


}
