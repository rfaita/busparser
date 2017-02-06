package com.busparser;

import com.busparser.bean.BusStop;
import com.busparser.bean.Line;
import com.busparser.bean.State;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author rafael
 */
public class Parser {

    private String host = "http://cdfararas.auttran.com/";
    private String path = "ajax/fulltable.php?codlinha={0}&city={1}&d={2}";
    private List<Integer> linesToParse = new ArrayList<Integer>();
    private String city;
    private List<BusStop> busStops = new ArrayList<BusStop>();
    private List<BusStop> busStopsOnlyName = new ArrayList<BusStop>();
    private Set<Line> lines = new HashSet<Line>();
    private static final Logger LOG = Logger.getLogger(Parser.class.getName());

    public List<BusStop> getBusStopsOnlyName() {
        return busStopsOnlyName;
    }

    public void setBusStopsOnlyName(List<BusStop> busStopsOnlyName) {
        this.busStopsOnlyName = busStopsOnlyName;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    public List<Integer> getLinesToParse() {
        return linesToParse;
    }

    public void setLinesToParse(List<Integer> linesToParse) {
        this.linesToParse = linesToParse;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void parse() throws IOException {

        lines.clear();
        busStops.clear();

        for (Integer idLine : getLinesToParse()) {

            LOG.log(Level.INFO, "Parsing line {0}", idLine);

            Document doc = Jsoup.connect(getHost() + getPath().replace("{0}", idLine.toString()).replace("{1}", getCity()).replace("{3}", System.currentTimeMillis() + "")).get();
            Elements lineName = doc.select(".tidtd1");
            Elements nextTimes = doc.select(".inttd1_0");
            Elements busIsHereParse = doc.select(".inttd1_1");
            Elements busStopsParse = doc.select(".inttd1_2");

            Boolean isGoingToHere = false;

            Line line = Line.parse(lineName.get(0).text());
            line.setId(idLine);

            for (int i = 0; i < busStopsParse.size(); i++) {
                State state = new State();

                state.setType(busStopsParse.get(i).parent().parent().child(0).child(1).toString().indexOf("apov") > -1
                        && busStopsParse.get(i).parent().parent().child(0).child(2).text().indexOf("TERMINAL") > -1 ? "going" : "returning");

                Calendar c = Calendar.getInstance();
                if (nextTimes.get(i) != null && !nextTimes.get(i).text().isEmpty()) {
                    String[] hour = nextTimes.get(i).text().split(":");
                    c.set(Calendar.HOUR, Integer.parseInt(hour[0]));
                    c.set(Calendar.MINUTE, Integer.parseInt(hour[1]));
                    c.set(Calendar.SECOND, Integer.parseInt(hour[2]));
                    state.setTime(c.getTime());
                }

                state.setIsHere(busIsHereParse.get(i).toString().indexOf("apov4") > -1
                        || busIsHereParse.get(i).toString().indexOf("apov4") > -1
                        || busIsHereParse.get(i).toString().indexOf("apov5") > -1
                        || busIsHereParse.get(i).toString().indexOf("apov6") > -1
                        || busIsHereParse.get(i).toString().indexOf("aipov4") > -1
                        || busIsHereParse.get(i).toString().indexOf("aipov5") > -1
                        || busIsHereParse.get(i).toString().indexOf("aipov6") > -1);

                if (state.getType().equals("going")) {
                    state.setIsGoingToHere(isGoingToHere);
                    isGoingToHere = busStopsParse.get(i).parent().nextElementSibling().getElementsByTag("img").size() > 0;
                } else {
                    isGoingToHere = busStopsParse.get(i).parent().nextElementSibling().getElementsByTag("img").size() > 0;
                    state.setIsGoingToHere(isGoingToHere);
                }


                BusStop busStop = BusStop.parse(busStopsParse.get(i).text());
                BusStop busStopOnlyName = BusStop.parse(busStopsParse.get(i).text());
                if (busStops.contains(busStop)) {
                    busStop = busStops.get(busStops.indexOf(busStop));
                } else {
                    busStops.add(busStop);
                    busStopsOnlyName.add(busStopOnlyName);
                }

                state.setIdBusStop(busStop.getId());
                state.setBusStop(busStop.getBusStop());
                state.setIdLine(line.getId());
                state.setLine(line.getLine());

                busStop.getStates().add(state);

                if (state.getIsGoingToHere() || state.getIsHere()) {
                    line.setCurrentBusStop(state);
                }

                line.getStates().add(state);
            }
            lines.add(line);

            LOG.log(Level.INFO, "Done Parsing line {0}", idLine);

        }

        for (BusStop b : busStops) {
            Collections.sort(b.getStates(), new Comparator<State>() {

                @Override
                public int compare(State o1, State o2) {
                    if (o1.getTime() == null) {
                        return 1;
                    } else if (o2.getTime() == null) {
                        return -1;
                    }
                    return (o1.getTime().after(o2.getTime())) ? 1 : -1;
                }
            });
            b.setNextBus(b.getStates().get(0));
        }


    }
}
