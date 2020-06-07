<!DOCTYPE html>
<html>
<head>

    <title>Add student</title>
    <link type="text/css" rel="stylesheet" href="Stylesheet/style.css"/>
    <link type="text/css" rel="stylesheet" href="Stylesheet/add-student-style.css"/>

</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>Registration</h2>
    </div>
</div>

<div id="container">
    <h3>Add student</h3>

    <form action="ServletWebApp" method="GET">
        <input type="hidden" name="command" value="ADD"/>
        <table>
            <tbody>
            <tr>
                <%--                    <th>Student number</th>--%>
                <th>Password</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Degree name</th>
                <th>Module Name</th>
                <th>Module code</th>
                <th>Duration</th>
                <th>Credits</th>
                <th>Marks</th>

            </tr>
            <tr>
                <td><input type="password" name="stu_password" required/></td>
                <td><input type="text" name="firstname" required/></td>
                <td><input type="text" name="Surname" required/></td>
                <td><input type="text" name="s_Degree" required/></td>

                <td><input type="text" name="Module_1" required/></td>
                <td><input type="text" name="M_Code_1" required/></td>
                <td><input type="number" name="M_Duration_1" required/></td>
                <td><input type="number" name="M_Credits_1" required/></td>
                <td><input type="number" name="Module_1_Mark" required/></td>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td><input type="text" name="Module_2" required/></td>
                <td><input type="text" name="M_Code_2" required/></td>
                <td><input type="number" name="M_Duration_2" required/></td>
                <td><input type="number" name="M_Credits_2" required/></td>
                <td><input type="number" name="Module_2_Mark" required/></td>
            </tr>
            <td><input type="submit" value="save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>


</body>


</html>