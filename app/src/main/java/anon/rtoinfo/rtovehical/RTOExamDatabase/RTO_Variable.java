package anon.rtoinfo.rtovehical.RTOExamDatabase;

import java.util.ArrayList;
import java.util.List;

public class RTO_Variable {
    private static RTO_Variable mInstance;
    public int adc = 0;
    public int counter = 0;
    public List<Integer> nlist = new ArrayList();
    public int qid = 0;
    public int right_score = 0;
    public int wrong_score = 0;

    protected RTO_Variable() {
    }

    public static synchronized RTO_Variable getInstance() {
        RTO_Variable rTO_Variable;
        synchronized (RTO_Variable.class) {
            synchronized (RTO_Variable.class) {
                if (mInstance == null) {
                    mInstance = new RTO_Variable();
                }
                rTO_Variable = mInstance;
            }
            return rTO_Variable;
        }
    }
}
