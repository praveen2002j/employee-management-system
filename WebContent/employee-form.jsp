<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Employee Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: blue">
			<div>
				<a href="https://www.xadmin.net" class="navbar-brand"> Employee Management Application </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Employees</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
			<caption>
				<h2>
					<c:if test="${employee != null}">
            			Edit Employee
            		</c:if>
					<c:if test="${employee == null}">
            			Add New Employee
            		</c:if>
				</h2>
			</caption>

			<c:if test="${employee != null}">
    <form action="update" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="id" value="<c:out value='${employee.id}' />" />
</c:if>
<c:if test="${employee == null}">
    <form action="insert" method="post" onsubmit="return validateForm()">
</c:if>


				<fieldset class="form-group">
					<label>Employee Name</label> 
					<input type="text" value="<c:out value='${employee.name}' />" class="form-control" name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Employee Email</label> 
					<input type="text" value="<c:out value='${employee.email}' />" class="form-control" name="email" id="email" onblur="checkEmailValidity()">
				</fieldset>
				
				<fieldset class="form-group">
					<label>Employee Address</label> 
					<input type="text" value="<c:out value='${employee.address}' />" class="form-control" name="address">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
			</form>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	function validateEmail(email) {
        var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return emailRegex.test(email);
    }

    function checkEmailValidity() {
        var email = document.getElementById('email').value;
        if (!validateEmail(email)) {
            alert('Please enter a valid email address.');
            return false;
        }
        return true;
    }

    function validateForm() {
        var name = document.querySelector('input[name="name"]').value;
        var email = document.getElementById('email').value;
        var address = document.querySelector('input[name="address"]').value;

        if (!name || !email || !address) {
            alert('Please fill in all the details.');
            return false;
        }
        
        return checkEmailValidity();
    }
</script>
</body>
</html>