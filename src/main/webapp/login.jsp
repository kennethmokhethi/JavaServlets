<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kenneth.Mokhethi
  Date: 4/15/2020
  Time: 1:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="Stylesheet/login.css">
    <title>Login</title>

</head>
<body>
<div class="loginbox">
    <img src="images/student_avatar.png" class="avatar">
    <h1>Login form</h1>
    <form action="ServletWebApp" method="GET">
        <input type="hidden" name="command" value="LOGIN"/>
        <p>Student number</p>
        <input type="number" name="S_No" placeholder="Enter student number" required/>
        <p>Password</p>
        <input type="password" name="Password" placeholder="Enter password" required/>

        <c:if test="${Wrong_credentials}">
            <p>Not yet registered.Please register before logging in</p>
        </c:if>

        <input type="submit" value="Login"/>
        <a href="add-student-form.jsp">Not registered?</a>

    </form>

</div>

</body>
</html>
