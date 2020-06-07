package controller;

import IConnectionpac.IConnection;
import connection.IConnectioinImplOracle;
import dao.Module_DAO;
import dao.Student_DAO;
import dao.Student_module_DAO;
import model.Module;
import model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@WebServlet("/ServletWebApp")
public class ServletMain extends HttpServlet {
    private Student_DAO student_dao;
    private Module_DAO module_dao;
    private Student_module_DAO student_module_dao;


    @Override
    public void init() throws ServletException {
        super.init();
        IConnection connection_class;
        try {
            connection_class = new IConnectioinImplOracle();
            module_dao = new Module_DAO(connection_class.getConnection());
            student_dao = new Student_DAO(connection_class.getConnection());
            student_module_dao = new Student_module_DAO(connection_class.getConnection());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String hiddenCommand = request.getParameter("command");

            if (hiddenCommand == null) {
                hiddenCommand = "LOGIN";
            }

            switch (hiddenCommand) {
                case "LOGIN":
                    login_student(request, response);
                    break;
                case "LIST":
                    list_students(request, response);
                    break;

                case "ADD":
                    add_student(request, response);
                    break;
                case "LOAD":
                    load_student_data(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE":
                    deleteStudent(request, response);
                    break;
                default:
                    login_student(request, response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void login_student(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int S_No = Integer.parseInt(request.getParameter("S_No"));
        String Password = hash_password(request.getParameter("Password"));
        Student student = student_dao.getStudent2(S_No, Password);
        if (student != null) {
            list_students(request, response);
        } else {
            boolean wrong_credentials = true;
            request.setAttribute("Wrong_credentials", wrong_credentials);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void list_students(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ArrayList<Student> student_list = student_dao.getAllStudents();
        request.setAttribute("STUDENT_LIST", student_list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view-students.jsp");
        dispatcher.forward(request, response);
    }

    private void add_student(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student obj_Student = getStudent(request, "stu_password", "firstname", "Surname", "s_Degree");
        Module module = getModule(request, "Module_1", "M_Code_1", "M_Duration_1", "M_Credits_1", "Module_1_Mark");
        obj_Student.addModule(module);
        Module module2 = getModule(request, "Module_2", "M_Code_2", "M_Duration_2", "M_Credits_2", "Module_2_Mark");
        obj_Student.addModule(module2);

        student_dao.add_Student(obj_Student);
        for (int i = 0; i < obj_Student.getStudent_modules().size(); i++) {
            module_dao.add_module(obj_Student, i);
            student_module_dao.add_Student_module(obj_Student, i);
        }
        list_students(request, response);
    }

    private Module getModule(HttpServletRequest request, String module_1, String m_code_1, String m_duration_1, String m_credits_1, String module_1_mark) {
        String M_Name = request.getParameter(module_1);
        String M_Code = request.getParameter(m_code_1);
        String M_Duration = request.getParameter(m_duration_1);
        String M_Credits = request.getParameter(m_credits_1);
        String Str_M_Marks = request.getParameter(module_1_mark);
        return new Module(M_Name, M_Code, Integer.parseInt(M_Duration), Integer.parseInt(M_Credits), Integer.parseInt(Str_M_Marks));
    }

    private Student getStudent(HttpServletRequest request, String password, String firstname, String surname, String Degree) {
        String passwordToSave = hash_password(request.getParameter(password));
        String s_Name = request.getParameter(firstname);
        String s_Surname = request.getParameter(surname);
        String s_Degree = request.getParameter(Degree);
        return new Student(passwordToSave, s_Name, s_Surname, s_Degree);
    }

    private void load_student_data(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student objStudent = student_dao.getStudent(Integer.parseInt(request.getParameter("student_Num")));
        request.setAttribute("THE_STUDENT", objStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Student student = getStudent(request, "password", "firstname", "Surname", "s_Degree");
        int student_no = Integer.parseInt(request.getParameter("student_no"));
        student.setS_No(student_no);
        student.addModule(getModule(request, "Module_1", "M_Code_1", "M_Duration_1", "M_Credits_1", "Module_1_Mark"));
        student.addModule(getModule(request, "Module_2", "M_Code_2", "M_Duration_2", "M_Credits_2", "Module_2_Mark"));
        String m_code_1_init = request.getParameter("module_code_1_init");
        String m_code_2_init = request.getParameter("module_code_2_init");
        student_dao.updateStudent(student, m_code_1_init, m_code_2_init);
        list_students(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int student_no = Integer.parseInt(request.getParameter("student_no"));
        String module_code_1 = request.getParameter("module_code_1");
        String module_code_2 = request.getParameter("module_code_2");
        student_dao.deleteStudent(student_no, module_code_1, module_code_2);
        list_students(request, response);
    }

    private String hash_password(String passwordToBehashed) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(passwordToBehashed.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException nsa) {
            throw new RuntimeException(nsa);
        }
    }
}
