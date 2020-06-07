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
    <h3>Update student</h3>

    <form action="ServletWebApp" method="GET">
        <input type="hidden" name="command" value="UPDATE"/>
        <input type="hidden" name="student_no" value="${THE_STUDENT.s_No}"/>
        <input type="hidden" name="password" value="${THE_STUDENT.st_password}"/>
        <input type="hidden" name="module_code_1_init" value="${THE_STUDENT.student_modules.get(0).getM_Code()}"/>
        <input type="hidden" name="module_code_2_init" value="${THE_STUDENT.student_modules.get(1).getM_Code()}"/>

        <table>
            <tbody>
            <tr>
                <th>Student number</th>
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
                <td><input type="number" name="s_No" value="${THE_STUDENT.s_No}" readonly="true"></td>
                <td><input type="text" name="firstname" value="${THE_STUDENT.s_Name}"/></td>
                <td><input type="text" name="Surname" value="${THE_STUDENT.s_Surname}"/></td>
                <td><input type="text" name="s_Degree" value="${THE_STUDENT.s_Degree}"/></td>

                <td><input type="text" name="Module_1" value="${THE_STUDENT.student_modules.get(0).getM_Name()}"/></td>
                <td><input type="text" name="M_Code_1" value="${THE_STUDENT.student_modules.get(0).getM_Code()}"/></td>
                <td><input type="number" name="M_Duration_1"
                           value="${THE_STUDENT.student_modules.get(0).getM_Duration()}"/></td>
                <td><input type="number" name="M_Credits_1"
                           value="${THE_STUDENT.student_modules.get(0).getM_Credits()}"/></td>
                <td><input type="number" name="Module_1_Mark"
                           value="${THE_STUDENT.student_modules.get(0).getM_Marks()}"/></td>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td><input type="text" name="Module_2" value="${THE_STUDENT.student_modules.get(1).getM_Name()}"/></td>
                <td><input type="text" name="M_Code_2" value="${THE_STUDENT.student_modules.get(1).getM_Code()}"/></td>
                <td><input type="number" name="M_Duration_2"
                           value="${THE_STUDENT.student_modules.get(1).getM_Duration()}"/></td>
                <td><input type="number" name="M_Credits_2"
                           value="${THE_STUDENT.student_modules.get(1).getM_Credits()}"/></td>
                <td><input type="number" name="Module_2_Mark"
                           value="${THE_STUDENT.student_modules.get(1).getM_Marks()}"/></td>
            </tr>
            <td><input type="submit" value="save" class="save"/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>