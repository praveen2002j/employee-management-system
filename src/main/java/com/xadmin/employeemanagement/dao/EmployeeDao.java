package com.xadmin.employeemanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.xadmin.employeemanagement.bean.Employee;

public class EmployeeDao {
	
	 private String jdbcURL = "jdbc:mysql://localhost:3306/employeedb?useSSL=false";
	 private String jdbcUsername ="root";
	 private String jdbcPassword ="praveen.J25";
	 private String jdbcDriver ="com.mysql.cj.jdbc.Driver";
	 
	 
	 private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO employees" + " (name, email, address) VALUES "
			 + " (?, ?, ?);";
			
			 private static final String SELECT_EMPLOYEE_BY_ID = "select id, name, email,address from employees where id =?";
			 private static final String SELECT_ALL_EMPLOYEES = "select * from employees";
			 private static final String DELETE_EMPLOYEES_SQL = "delete from employees where id = ?;";
			 private static final String UPDATE_EMPLOYEES_SQL = "update employees set name = ?,email= ?, address =? where id = ?;";
		
 public EmployeeDao() {
				

			}
	 
 protected Connection getConnection() {
	    Connection connection = null;
	    try {
	        Class.forName(jdbcDriver);
	        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	    } catch (SQLException e) {
	        System.err.println("Failed to create a connection due to SQLException: " + e.getMessage());
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        System.err.println("JDBC Driver not found: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    if(connection == null) {
	        System.err.println("Database connection could not be established. Check if database is running and credentials are correct.");
	    }

	    return connection;
	}

	 
	 
	 //insert employee
	 public void insertEmployee(Employee employee) throws SQLException
	 {
	   System.out.println(INSERT_EMPLOYEES_SQL);
	   try(Connection connection = getConnection();
			   PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL)){
	             
				   preparedStatement.setString(1,employee.getName());
				   preparedStatement.setString(2,employee.getEmail());
				   preparedStatement.setString(3,employee.getAddress());
				   
				   System.out.println(preparedStatement);
				   preparedStatement.executeUpdate(); 
	 
			   } catch(SQLException e) { 
				   
				   printSQLException(e);
				   
			   }
	 
	 
	 
	 }
	 
	 // select employee by id
	 public Employee selectEmployee(int id) {
	 Employee employee = null;
	 
	 try (Connection connection = getConnection();
	
	 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);){
	 preparedStatement.setInt(1,id);
	 System.out.println (preparedStatement);

	 ResultSet rs = preparedStatement.executeQuery();
	
	 
	 while(rs.next()) {
	 String name = rs.getString("name");
	 String email = rs.getString("email");
	 String address = rs.getString("address");
	 employee = new Employee(id, name, email, address);
	 }
	 }catch (SQLException e) {
	      printSQLException(e);
	 }
	  return employee;
	 
	 
	 }
	 
	 
	 // select all users
	 public List<Employee> selectAllEmployees() {
		 
	
	        List<Employee> employees = new ArrayList<>();

	        
	        try (Connection connection = getConnection();
	            
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);) {

	            System.out.println(preparedStatement);

	            ResultSet rs = preparedStatement.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String address = rs.getString("address");
	                
	                employees.add(new Employee(id, name, email, address));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    return employees;
	 
	 }
	 
	 // update employee

	 public boolean updateEmployee(Employee employee) throws SQLException {
	     boolean rowUpdated;
	     try (Connection connection = getConnection();
	          PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEES_SQL);) {
	         
	         statement.setString(1, employee.getName());
	         statement.setString(2, employee.getEmail());
	         statement.setString(3, employee.getAddress());
	         statement.setInt(4, employee.getId());

	         rowUpdated = statement.executeUpdate() > 0;
	     }
	     return rowUpdated;
	 }
 
	 
	// Delete employee
	 public boolean deleteEmployee(int id) throws SQLException {
	     boolean rowDeleted;
	     try (Connection connection = getConnection();
	          PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEES_SQL);) {
	         
	         statement.setInt(1, id);

	         rowDeleted = statement.executeUpdate() > 0;
	     }
	     return rowDeleted;
	 }

	 
	 

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if(e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error code : " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause(); 
				while (t != null) {
					System.out.println("Cause: " +t);
					t = t.getCause();
				}
			}
			
		}
		
	}
	 
	 
	
	
	
	 }
	 
 

 