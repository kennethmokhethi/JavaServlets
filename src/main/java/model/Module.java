package model;

public class Module {
    private String M_Name;
    private String M_Code;
    int M_Duration;
    int M_Credits;
    private int M_Marks;

    public Module(String M_Name, String M_Code, int M_Duration, int M_Credits, int M_Marks) {
        setM_Name(M_Name);
        setM_Code(M_Code);
        setM_Marks(M_Marks);
        setM_Duration(M_Duration);
        setM_Credits(M_Credits);
    }

    public String getM_Name() {
        return M_Name;
    }

    public void setM_Name(String m_Name) {
        M_Name = m_Name;
    }

    public String getM_Code() {
        return M_Code;
    }

    public void setM_Code(String m_Code) {
        M_Code = m_Code;
    }

    public int getM_Marks() {
        return M_Marks;
    }

    public void setM_Marks(int m_Marks) {
        M_Marks = m_Marks;
    }

    public int getM_Duration() {
        return M_Duration;
    }

    public void setM_Duration(int m_Duration) {
        M_Duration = m_Duration;
    }

    public int getM_Credits() {
        return M_Credits;
    }

    public void setM_Credits(int m_Credits) {
        M_Credits = m_Credits;
    }
}
