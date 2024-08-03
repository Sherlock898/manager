<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
    <link rel="stylesheet" href="path/to/your/css/file.css">
</head>

<body>
    <div>
        <h2>Register</h2>
        <form:form action="/add-user" method="POST" modelAttribute="user">
            <div>
                <form:label path="firstName" for="firstName">First Name:</form:label>
                <form:input path="firstName" id="firstName" name="firstName" type="text" required="true"/>
                <form:errors path="firstName" />
            </div>
            <div>
                <form:label path="lastName" for="lastName">Last Name:</form:label>
                <form:input path="lastName" id="lastName" name="lastName" type="text" required="true"/>
                <form:errors path="lastName" />
            </div>
            <div>
                <form:label path="email" for="email">Email:</form:label>
                <form:input path="email" id="email" name="email" type="email" required="true"/>
                <form:errors path="email" />
            </div>
            <div>
                <form:label path="password" for="password">Password:</form:label>
                <form:input path="password" id="password" name="password" type="password" required="true"/>
                <form:errors path="password" />
            </div>
            <div>
                <form:label path="confirmPassword" for="confirmPassword">Password Confirmation:</form:label>
                <form:input path="confirmPassword" id="confirmPassword" name="confirmPassword" type="password" required="true"/>
                <form:errors path="confirmPassword" />
            </div>
            <div>
                <input type="hidden" name="role" value="USER"/>
                <form:checkbox path="role" id="role" name="role" value="ADMIN"/>
                <form:label path="role" for="role">Admin</form:label>
                <div>${error}</div>
            </div>
            <input type="submit" value="Register" />
        </form:form>
    </div>
</body>

</html>
