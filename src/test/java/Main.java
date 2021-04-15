import java.util.Map;

public class Main {

    public static void main(String[] args) {

        RegEng regEng = new RegEng("aab*a");

        Map<Integer, Integer> results = regEng.analyze("abbbaaabababaaabba");

        for (Map.Entry<Integer, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }
}
