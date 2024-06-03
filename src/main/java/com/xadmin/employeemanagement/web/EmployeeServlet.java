package com.xadmin.employeemanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xadmin.employeemanagement.bean.Employee;
import com.xadmin.employeemanagement.dao.EmployeeDao;


@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private EmployeeDao employeeDao;
   
	
	public void init() throws ServletException {
		
		employeeDao = new EmployeeDao();
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          
		String action = request.getServletPath();
		try { switch(action) {
		
		case "/new":
			showNewForm(request,response);
			break;
	    
		case "/insert":
			insertEmployee(request,response);
			
			break;
			
		case "/delete":
			deleteEmployee(request,response);
			break;
			
		case "/edit":
			showEditForm(request,response);
			break;
			
		case "/update":
			updateEmployee(request,response);
			break;
			
			default:
				listEmployee(request,response);
				break;
			
			
		}
			
			
		}catch (SQLException ex) {
            throw new ServletException(ex);
        }
			
		}
		
		
		
		 private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			 
			 
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		        
		        dispatcher.forward(request, response);
		    }

		
		 private void insertEmployee(HttpServletRequest request, HttpServletResponse response) 
		            throws SQLException, IOException {
		        String name = request.getParameter("name");
		        String email = request.getParameter("email");
		        String address = request.getParameter("address");
		        
		        
		        Employee newEmployee = new Employee(name, email, address);
		        employeeDao.insertEmployee(newEmployee);
		        response.sendRedirect("list");
		    }

		
		
	
		 private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
		            throws SQLException, IOException {
		       int id = Integer.parseInt(request.getParameter("id"));
		       try { employeeDao.deleteEmployee(id);
		       } catch(Exception e) {
		    	   
		    	   e.printStackTrace();
		       
		       }
		        response.sendRedirect("list");
		    }
	   
		 
		 private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		            throws SQLException, ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        Employee existingEmployee;
		        try {
		      existingEmployee = employeeDao.selectEmployee(id);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		        request.setAttribute("employee", existingEmployee);
		        dispatcher.forward(request, response);
		        } catch (Exception e) {
		        	
		        	e.printStackTrace();
		        }
		        
		    }

	
		 private void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
		            throws SQLException, IOException {
		       
		        String idStr = request.getParameter("id");
		        if (idStr == null || idStr.trim().isEmpty()) {
		            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Employee ID");
		            return;
		        }

		        int id;
		        try {
		            id = Integer.parseInt(idStr);
		        } catch (NumberFormatException e) {
		            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Employee ID Format");
		            return;
		        }
		        
		        
		        String name = request.getParameter("name");
		        String email = request.getParameter("email");
		        String address = request.getParameter("address");

		        Employee employee = new Employee(id, name, email, address);
		        employeeDao.updateEmployee(employee);
		        response.sendRedirect("list");
		    }
	
		 private void listEmployee(HttpServletRequest request, HttpServletResponse response)
		            throws SQLException, IOException, ServletException {
		   try {     List<Employee> listEmployee = employeeDao.selectAllEmployees();
		        request.setAttribute("listEmployee", listEmployee);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
		        dispatcher.forward(request, response);
		   } catch (Exception e) {
			   
			   e.printStackTrace();
		   }
		   
		   
		    }

		 
		 

}
