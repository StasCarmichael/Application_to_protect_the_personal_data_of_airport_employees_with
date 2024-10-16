package org.project.bd.dao;

import org.project.bd.entities.Employee;
import org.project.services.EncryptionUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeDAO {

    private static final String PERSISTENCE_UNIT_NAME = "hibernate-demo";
    private EntityManagerFactory emf;

    private EncryptionUtil encryptionUtil = new EncryptionUtil();

    public EmployeeDAO() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public void close() {
        emf.close();
    }


    public void saveEmployee(Employee employee) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Encrypt sensitive data
        try {
            String encryptedEmail = encryptionUtil.encrypt(employee.getEmail());
            employee.setEmail(encryptedEmail);

            String encryptedPhone = encryptionUtil.encrypt(employee.getPhone());
            employee.setPhone(encryptedPhone);
        } catch (Exception e) {
            e.printStackTrace(); // Handle encryption exception appropriately
        }

        em.persist(employee);
        em.getTransaction().commit();
        em.close();
    }

    public Employee getEmployeeById(int employeeId) {
        EntityManager em = emf.createEntityManager();
        Employee employee = em.find(Employee.class, employeeId);

        // Decrypt sensitive data
        try {
            if (employee != null) {
                String decryptedEmail = encryptionUtil.decrypt(employee.getEmail());
                employee.setEmail(decryptedEmail);

                String decryptedPhone = encryptionUtil.decrypt(employee.getPhone());
                employee.setPhone(decryptedPhone);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle decryption exception appropriately
        }

        em.close();
        return employee;
    }

    public List<Employee> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM Employee e");
        List<Employee> employees = query.getResultList();

        // Decrypt sensitive data for all employees
        try {
            for (Employee employee : employees) {
                String decryptedEmail = encryptionUtil.decrypt(employee.getEmail());
                employee.setEmail(decryptedEmail);

                String decryptedPhone = encryptionUtil.decrypt(employee.getPhone());
                employee.setPhone(decryptedPhone);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle decryption exception appropriately
        }

        em.close();
        return employees;
    }


    public void updateEmployee(Employee employee) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Decrypt sensitive data
        try {
            if (employee != null) {
                String decryptedEmail = encryptionUtil.encrypt(employee.getEmail());
                employee.setEmail(decryptedEmail);

                String decryptedPhone = encryptionUtil.decrypt(employee.getPhone());
                employee.setPhone(decryptedPhone);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle decryption exception appropriately
        }

        em.merge(employee);
        em.getTransaction().commit();
        em.close();
    }



    public void deleteEmployee(int employeeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, employeeId);
        if (employee != null) {
            em.remove(employee);
        }
        em.getTransaction().commit();
        em.close();
    }


    public List<Employee> getEmployeesWithFilters(int page, int perPage, String name, String position,
                                                  String department, String hireDateStr, String email,
                                                  String phone) {
        EntityManager em = emf.createEntityManager();
        StringBuilder queryString = new StringBuilder("SELECT e FROM Employee e WHERE 1=1");

        if (name != null && !name.isEmpty())
            queryString.append(" AND LOWER(e.name) LIKE LOWER(:name)");
        if (position != null && !position.isEmpty())
            queryString.append(" AND LOWER(e.position) LIKE LOWER(:position)");
        if (department != null && !department.isEmpty())
            queryString.append(" AND LOWER(e.department) LIKE LOWER(:department)");
        if (hireDateStr != null && !hireDateStr.isEmpty())
            queryString.append(" AND LOWER(e.hireDate) LIKE LOWER(:hireDate)");

        if (email != null && !email.isEmpty())
            queryString.append(" AND LOWER(e.email) LIKE LOWER(:email)");
        if (phone != null && !phone.isEmpty())
            queryString.append(" AND LOWER(e.phone) LIKE LOWER(:phone)");

        Query query = em.createQuery(queryString.toString());

        if (name != null && !name.isEmpty())
            query.setParameter("name", "%" + name + "%");
        if (position != null && !position.isEmpty())
            query.setParameter("position", "%" + position + "%");
        if (department != null && !department.isEmpty())
            query.setParameter("department", "%" + department + "%");
        if (hireDateStr != null && !hireDateStr.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date hireDate = dateFormat.parse(hireDateStr);
                query.setParameter("hireDate", hireDateStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (phone != null && !phone.isEmpty())
                query.setParameter("phone", "%" + encryptionUtil.encrypt(phone) + "%");
            if (email != null && !email.isEmpty())
                query.setParameter("email", "%" + encryptionUtil.encrypt(email) + "%");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        query.setFirstResult((page - 1) * perPage);
        query.setMaxResults(perPage);


        List<Employee> employees = query.getResultList();
        em.close();


        try {
            for (Employee employee : employees) {
                String decryptedEmail = encryptionUtil.decrypt(employee.getEmail());
                employee.setEmail(decryptedEmail);

                String decryptedPhone = encryptionUtil.decrypt(employee.getPhone());
                employee.setPhone(decryptedPhone);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }


    public int getTotalEmployees() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT COUNT(e) FROM Employee e");
        int count = Integer.parseInt(query.getSingleResult()+"");
        em.close();
        return count;
    }
}
