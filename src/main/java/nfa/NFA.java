package nfa;

import java.util.HashSet;

public class NFA {

//    private final HashSet<State> Q = new HashSet<>();
    private State S;
    private State F;

    public State getStart() {
        return S;
    }
    public State getFinal() {
        return F;
    }

    public void setStart(State S) {
        this.S = S;
//        Q.add(S);
    }
    public void setFinal(State F) {
        this.F = F;
        F.setAccept(true);
//        Q.add(F);
    }

    public static NFA fromLiteral(char c) {
        NFA nfa = new NFA();

        State s = new State();
        State f = new State();

        nfa.setStart(s);
        nfa.setFinal(f);

        nfa.getStart().setQ1(c, nfa.getFinal());

        return nfa;
    }

    public static NFA concatNFA(NFA nfa1, NFA nfa2) {
        NFA result = NFA.fromLiteral('⁁');

        State n1f = nfa1.getFinal();
        State n2f = nfa2.getFinal();

        result.getStart().setQ1('⁁', nfa1.getStart());

        n1f.setAccept(false);
        n1f.setQ1('⁁', nfa2.getStart());
        n2f.setAccept(false);
        n2f.setQ1('⁁', result.getFinal());

        return result;
    }

    public static NFA unionNFA(NFA nfa1, NFA nfa2) {
        NFA result = NFA.fromLiteral('⁁');

        State rs = result.getStart();
        State n1f = nfa1.getFinal();
        State n2f = nfa2.getFinal();

        rs.setQ1('⁁', nfa1.getStart());
        rs.setQ2('⁁', nfa2.getStart());

        n1f.setAccept(false);
        n1f.setQ1('⁁', result.getFinal());

        n2f.setAccept(false);
        n2f.setQ1('⁁', result.getFinal());

        return result;
    }

    public static NFA closureNFA(NFA nfa) {
        NFA res = NFA.fromLiteral('⁁');

        State s = res.getStart();
        State oldF = nfa.getFinal();

        s.setQ1('⁁', nfa.getStart());
        s.setQ2('⁁', nfa.getFinal());

        oldF.setAccept(false);
        oldF.setQ1('⁁', nfa.getStart());
        oldF.setQ2('⁁', res.getFinal());

        return res;
    }
}
