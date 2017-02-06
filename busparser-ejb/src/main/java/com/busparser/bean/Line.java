package com.busparser.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class Line implements Serializable {

    private Integer id;
    private String line;
    private State currentBusStop;
    private List<State> states = new ArrayList<State>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public State getCurrentBusStop() {
        return currentBusStop;
    }

    public void setCurrentBusStop(State currentBusStop) {
        this.currentBusStop = currentBusStop;
    }

    @Override
    public String toString() {
        return "Line{" + "id=" + id + ", line=" + line + ", currentBusStop=" + currentBusStop + ", states=" + states + '}';
    }

    public static Line parse(String s) {
        Line b = new Line();

        b.setLine(s.split("<")[0].trim().replaceAll("/|\\(.*\\)", "").trim());

        return b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Line other = (Line) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.line == null) ? (other.line != null) : !this.line.equals(other.line)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.line != null ? this.line.hashCode() : 0);
        return hash;
    }
}
