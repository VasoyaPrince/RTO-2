package anon.rtoinfo.rtovehical.RTOExamDatabase;

public class RTO_Question {
    private Integer ANSWER;
    private int ID;
    private String IMAGEPATH;
    private String OPTA;
    private String OPTB;
    private String OPTC;
    private String QUESTION;

    public RTO_Question() {
        this.ID = 0;
        this.QUESTION = "";
        this.OPTA = "";
        this.OPTB = "";
        this.OPTC = "";
        this.ANSWER = 0;
        this.IMAGEPATH = "";
    }

    public RTO_Question(String str, String str2, String str3, String str4, Integer num, String str5) {
        this.QUESTION = str;
        this.OPTA = str2;
        this.OPTB = str3;
        this.OPTC = str4;
        this.ANSWER = num;
        this.IMAGEPATH = str5;
    }

    public int getID() {
        return this.ID;
    }

    public String getQUESTION() {
        return this.QUESTION;
    }

    public String getOPTA() {
        return this.OPTA;
    }

    public String getOPTB() {
        return this.OPTB;
    }

    public String getOPTC() {
        return this.OPTC;
    }

    public Integer getANSWER() {
        return this.ANSWER;
    }

    public String getIMAGEPATH() {
        return this.IMAGEPATH;
    }

    public void setID(int i) {
        this.ID = i;
    }

    public void setQUESTION(String str) {
        this.QUESTION = str;
    }

    public void setOPTA(String str) {
        this.OPTA = str;
    }

    public void setOPTB(String str) {
        this.OPTB = str;
    }

    public void setOPTC(String str) {
        this.OPTC = str;
    }

    public void setANSWER(Integer num) {
        this.ANSWER = num;
    }

    public void setIMAGEPATH(String str) {
        this.IMAGEPATH = str;
    }
}
