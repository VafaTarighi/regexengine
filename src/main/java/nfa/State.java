package nfa;

public class State {
    private boolean isAccept;

    private char[] label = new char[2];
    private State[] trans = new State[2];

    public void setAccept(boolean isFinal) {
        this.isAccept = isFinal;
    }

    public State() {}

    public State(boolean isAccept) {
        this.isAccept = isAccept;
    }

    public void setQ1(char l, State q1) {
        label[0] = l;
        trans[0] = q1;
    }

    public void setQ2(char l, State q2) {
        label[1] = l;
        trans[1] = q2;
    }

    public char getQ1Label() {
        return label[0];
    }

    public char getQ2Label() {
        return label[1];
    }

    public State getQ1() {
        return trans[0];
    }

    public State getQ2() {
        return trans[1];
    }

    public boolean isAccept() {
        return isAccept;
    }
}
