package datamodel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE employee (
  id INT NOT NULL AUTO_INCREMENT,    
  name VARCHAR(30) NOT NULL,   
  tableNumber INT NOT NULL,
  date VARCHAR(30) NOT NULL,
  time VARCHAR(30) NOT NULL,
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "employee")
public class Employee {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column name. Without it, it will use method name
   private Integer id;

   @Column(name = "name")
   private String name;

   @Column(name = "tableNumber")
   private Integer tableNumber;
   
   @Column(name = "date")
   private String date;
   
   @Column(name = "time")
   private String time;

   public Employee() {
   }

   public Employee(Integer id, String name, Integer tableNumber, String date, String time) {
      this.id = id;
      this.name = name;
      this.tableNumber = tableNumber;
      this.date = date;
      this.time = time;
   }

   public Employee(String name, int tableNumber, String date, String time) {
	   this.name = name;
	   this.tableNumber = tableNumber;
	   this.date = date;
	   this.time = time;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getTableNumber() {
      return tableNumber;
   }

   public void setTableNumber(Integer tableNumber) {
      this.tableNumber = tableNumber;
   }
   
   public String getDate() {
	      return date;
	   }

   public void setDate(String date) {
	      this.date = date;
	   }
   
   public String getTime() {
	      return time;
	   }

   public void setTime(String time) {
	      this.time = time;
	   }

   @Override
   public String toString() {
      return "Employee: " + this.id + ", " + this.name + ", " + this.tableNumber + ", " + this.date + " ," + this.time;
   }
}