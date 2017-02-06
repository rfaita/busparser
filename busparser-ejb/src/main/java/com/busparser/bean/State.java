package com.busparser.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author rafael
 */
public class State implements Serializable {

    private String type;
    private Date time;
    private Boolean isHere;
    private Boolean isGoingToHere;
    private Integer idLine;
    private String line;
    private Integer idBusStop;
    private String busStop;

    public Integer getIdBusStop() {
        return idBusStop;
    }

    public void setIdBusStop(Integer idBusStop) {
        this.idBusStop = idBusStop;
    }

    public Integer getIdLine() {
        return idLine;
    }

    public void setIdLine(Integer idLine) {
        this.idLine = idLine;
    }

    public Boolean getIsGoingToHere() {
        return isGoingToHere;
    }

    public void setIsGoingToHere(Boolean isGoingToHere) {
        this.isGoingToHere = isGoingToHere;
    }

    public Boolean getIsHere() {
        return isHere;
    }

    public void setIsHere(Boolean isHere) {
        this.isHere = isHere;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date nextStop) {
        this.time = nextStop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "State{" + "type=" + type + ", time=" + time + ", isHere=" + isHere + ", isGoingToHere=" + isGoingToHere + ", idLine=" + idLine + ", line=" + line + ", idBusStop=" + idBusStop + ", busStop=" + busStop + '}';
    }
}
