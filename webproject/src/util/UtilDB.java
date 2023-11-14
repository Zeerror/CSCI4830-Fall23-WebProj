/**
 */
package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import datamodel.Employee;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @since JavaSE-1.8
 */
public class UtilDB {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<Employee> listEmployees() {
      List<Employee> resultList = new ArrayList<Employee>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

      try {
         tx = session.beginTransaction();
         List<?> employees = session.createQuery("FROM Employee").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            resultList.add(employee);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<Employee> listEmployees(String keyword) {
      List<Employee> resultList = new ArrayList<Employee>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         System.out.println((Employee)session.get(Employee.class, 1)); // use "get" to fetch data
        // Query q = session.createQuery("FROM Employee");
         List<?> employees = session.createQuery("FROM Employee").list();
         for (Iterator<?> iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            if (employee.getName().startsWith(keyword)) {
               resultList.add(employee);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void createEmployees(String userName, String age) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new Employee(userName, Integer.valueOf(age)));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   public static boolean authenticate(String username, String password) {
       Session session = getSessionFactory().openSession();
       Transaction tx = null;

       try {
           tx = session.beginTransaction();

           // Use a parameterized query to avoid SQL injection
           Query query = session.createQuery("FROM User WHERE username = :username AND password = :password");
           query.setParameter("username", username);
           query.setParameter("password", password);

           // Execute the query and check if the result is not empty
           boolean isAuthenticated = !query.list().isEmpty();

           tx.commit();

           return isAuthenticated;
       } catch (HibernateException e) {
           if (tx != null) {
               tx.rollback();
           }
           e.printStackTrace();
           return false;
       } finally {
           session.close();
       }
   }
   
}
