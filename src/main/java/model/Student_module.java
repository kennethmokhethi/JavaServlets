package model;

public class Student_module {
    private int s_no;
    private String m_code;
    private int marks;

    public Student_module(String m_code, int s_no, int marks) {
        setS_no(s_no);
        setM_code(m_code);
        setMarks(marks);
    }

    public int getS_no() {
        return s_no;
    }

    public void setS_no(int s_no) {
        this.s_no = s_no;
    }

    public String getM_code() {
        return m_code;
    }

    public void setM_code(String m_code) {
        this.m_code = m_code;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
