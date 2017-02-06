package com.busparser;

import com.busparser.firebase.FirebaseUpper;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 *
 * @author rafael
 */
@Singleton
public class ParserMakerBean {

    @Inject
    private FirebaseUpper firebaseUpper;
    @Inject
    private Parser parser;
    private static final Logger LOG = Logger.getLogger(ParserMakerBean.class.getName());

    @Schedules({
        @Schedule(hour = "5-23", minute = "*/3")
    })
    public void parse() {
        LOG.log(Level.INFO, "Lets the parse begin");
        try {
            parser.setCity("ARR");
            parser.setLinesToParse(Arrays.asList(
                    101, 102, 103, 104, 105,
                    201, 202, 203, 204, 206, 207,
                    301, 302, 303,
                    401, 402,
                    501, 502, 503,
                    601, 602,
                    701, 702,
                    801, 802,
                    901, 902,
                    1001, 1002,
                    1101, 1102, 1103,
                    1201, 1202,
                    1301, 1302,
                    1401, 1402, 1403, 1404,
                    1501));
            
            parser.parse();

            firebaseUpper.writeBusStops(parser.getBusStops());
            firebaseUpper.writeBusStopsOnlyName(parser.getBusStopsOnlyName());
            firebaseUpper.writeLines(parser.getLines());

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            LOG.log(Level.INFO, "Lets the parse end");
        }


    }
}
