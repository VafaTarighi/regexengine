import nfa.NFA;
import nfa.State;

import java.util.*;

public class RegEng {

    private final NFA nfa;

    private final Map<Integer, Integer> matches = new HashMap<>();

    private static final HashMap<Character, Integer>oprPrecedence = new HashMap<>();
    static
    {
        // add and subtract
        oprPrecedence.put('*', 3);

        // multiply and divide
        oprPrecedence.put('°', 2);

        // power
        oprPrecedence.put('|', 1);
    }

    public RegEng(String pattern) {

        pattern = formatPattern(pattern);

        String postFix = shuntingYard(pattern);

        postFix = postFix.replaceAll("¿", "");

        Stack<NFA> stack = new Stack<>();

        NFA a, b;
        for (char c : postFix.toCharArray()) {
            if (c == '°') {
                b = stack.pop();
                a = stack.pop();
                stack.push(NFA.concatNFA(a, b));
            } else if (c == '|') {
                b = stack.pop();
                a = stack.pop();
                stack.push(NFA.unionNFA(a, b));
            } else if (c == '*') {
                a = stack.pop();
                stack.push(NFA.closureNFA(a));
            } else {
                stack.push(NFA.fromLiteral(c));
            }
        }

        nfa = stack.pop();

    }

    private static String formatPattern(String pattern) {
        StringBuilder sb = new StringBuilder();
        int len = pattern.length();

        for (int i = 0; i < len; i++) {
            char c = pattern.charAt(i);
            sb.append(c);

            if (c == '*') {
                sb.append('¿');
            }

            char nextC = (i < len - 1)? pattern.charAt(i + 1) : '+';
            if (c == '(' || c == '+' || c == '|') continue;
            if (nextC == '+' || nextC == '|' || nextC == ')' || nextC == '*') continue;

            sb.append('°');

        }

        return sb.toString();
    }

    private static String shuntingYard(String input) {
        Queue<Character> items = new LinkedList<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder result = new StringBuilder();

        input = "(" + input + ")";

        for (char c : input.toCharArray()) {
            items.add(c);
        }

        while (!items.isEmpty()) {
            char element = items.poll();
            if (element == '(' || element == ')' || element == '*'
                    || element == '|' || element == '°') { // if element is a operator
                if (element == '(') {
                    operators.push(element);
                }
                else if (element == ')') {
                    while (!(operators.peek() == '(')) {
                        result.append(operators.pop());
                    }
                    operators.pop();
                }
                else if (operators.isEmpty() || operators.peek() == '(' || element == '*'
                        || oprPrecedence.get(element) > oprPrecedence.get(operators.peek())) {
                    operators.push(element);
                }
                else {
                    while (!operators.isEmpty() && !(operators.peek() == '(')
                            && oprPrecedence.get(element) <= oprPrecedence.get(operators.peek())) {
                        result.append(operators.pop());
                    }
                    operators.push(element);
                }
            } else { // if element is a literal
                result.append(element);
            }

        }

        while(!operators.isEmpty()) {
            result.append(operators.pop());
        }

        return result.toString();
    }

    public Map<Integer, Integer> analyze(String input) {
        char[] chars = input.toCharArray();

        int start = 0;

        while (start < chars.length) {
            int len = walkNFA(start, start, nfa.getStart(), chars);
            if (len == 0) start++;
            else start += len;
        }

        return matches;
    }

    public int walkNFA(int start, int index, State curState, char[] chars) {
        if (curState.isAccept()) {
            matches.put(start, index - start);
            return matches.get(start);
        }

        int l1,l2,l3,l4;
        char q1Label = curState.getQ1Label();
        if (q1Label == '⁁') {
            l1 = walkNFA(start, index, curState.getQ1(), chars);
            if (l1 > 0) return l1;
        } else if (index < chars.length && q1Label == chars[index]) {
            l2 = walkNFA(start, index + 1, curState.getQ1(), chars);
            if (l2 > 0) return l2;
        }

        char q2Label = curState.getQ2Label();
        if (curState.getQ2() != null) {
            if (q2Label == '⁁') {
                l3 = walkNFA(start, index, curState.getQ2(), chars);
                if (l3 > 0) return l3;
            } else if (index < chars.length && q2Label == chars[index]) {
                l4 = walkNFA(start, index + 1, curState.getQ2(), chars);
                if (l4 > 0) return l4;
            }
        }

        return 0;
    }
}
