package com.busparser.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class BusStop implements Serializable {

    private Integer id;
    private String busStop;
    private State nextBus;
    private List<State> states = new ArrayList<State>();

    public State getNextBus() {
        return nextBus;
    }

    public void setNextBus(State nextStop) {
        this.nextBus = nextStop;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BusStop{" + "id=" + id + ", busStop=" + busStop + ", nextBus=" + nextBus + ", states=" + states + '}';
    }

    public static BusStop parse(String s) {
        BusStop b = new BusStop();

        int qtdCharsId = 3;
        try {
            b.setId(Integer.parseInt(s.substring(0, qtdCharsId)));
        } catch (Exception e) {
            try {
                qtdCharsId = 2;
                b.setId(Integer.parseInt(s.substring(0, qtdCharsId)));
            } catch (Exception ex) {
                b.setId(null);
            }
        }

        if (b.getId() == null) {
            b.setBusStop(s.trim());
        } else {
            b.setBusStop(s.substring(qtdCharsId, s.length() - 1).replaceAll("/", "-").replaceAll("\\s?\\(", "").replaceAll("\\s?\\)", "").trim());
        }

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
        final BusStop other = (BusStop) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.busStop == null) ? (other.busStop != null) : !this.busStop.equals(other.busStop)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.busStop != null ? this.busStop.hashCode() : 0);
        return hash;
    }
}
