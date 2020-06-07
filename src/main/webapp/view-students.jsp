<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <title>view students</title>
    <link type="text/css" rel="stylesheet" href="Stylesheet/style.css">
</head>


<body>
<div id="wrapper">
    <div id="header">
        <h2>Registered students </h2>
    </div>
</div>

<div id="container">
    <div id="content">
        <%--     Adding the student vutton     --%>
        <input type="button" value="Register" onclick="window.location.href='add-student-form.jsp';return false"
               class="add-student-button"/>

        <input type="button" value="Log out" onclick="window.location.href='login.jsp';return false"
               class="add-student-button"/>
        <table>
            <tr>
                <th>Student number</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Degree name</th>
                <th>Module code</th>
                <th>Module Name</th>
                <th>Duration</th>
                <th>Credits</th>
                <th>Marks</th>
                <th>Action</th>
            </tr>
            <c:forEach var="student" items="${STUDENT_LIST}">
                <%--                     set up a link for each student--%>
                <c:url var="tempLink" value="ServletWebApp">
                    <c:param name="command" value="LOAD"></c:param>
                    <c:param name="student_Num" value="${student.s_No}"></c:param>
                </c:url>
                <%--                     set up of the link for deleting a student--%>
                <c:url var="deleteLink" value="ServletWebApp">
                    <c:param name="command" value="DELETE"></c:param>
                    <c:param name="student_no" value="${student.s_No}"></c:param>
                    <c:param name="module_code_1" value="${student.getStudent_modules().get(0).getM_Code()}"> </c:param>
                    <c:param name="module_code_2" value="${student.getStudent_modules().get(1).getM_Code()}"> </c:param>
                </c:url>
                <tr>
                    <td>${student.s_No}</td>
                    <td>${student.s_Name}</td>
                    <td>${student.s_Surname}</td>
                    <td>${student.s_Degree}</td>
                    <td>${student.getStudent_modules().get(0).getM_Code()}</td>
                    <td>${student.getStudent_modules().get(0).getM_Name()}</td>
                    <td>${student.getStudent_modules().get(0).getM_Duration()}</td>
                    <td>${student.getStudent_modules().get(0).getM_Credits()}</td>
                    <td>${student.getStudent_modules().get(0).getM_Marks()}</td>
                    <td><a href="${tempLink}">Update </a>
                        |
                        <a href="${deleteLink}"
                           onclick="if(!(confirm('Please confirm to delete this student'))) return false"> Delete </a>
                    </td>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>${student.getStudent_modules().get(1).getM_Code()}</td>
                    <td>${student.getStudent_modules().get(1).getM_Name()}</td>
                    <td>${student.getStudent_modules().get(1).getM_Duration()}</td>
                    <td>${student.getStudent_modules().get(1).getM_Credits()}</td>
                    <td>${student.getStudent_modules().get(1).getM_Marks()}</td>
                </tr>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
