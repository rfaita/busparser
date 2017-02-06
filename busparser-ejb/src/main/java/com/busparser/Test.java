package com.busparser;

import com.busparser.firebase.FirebaseUpper;
import java.util.Arrays;

/**
 *
 * @author rafael
 */
public class Test {

    public static void main(String args[]) throws Exception {

        Parser p = new Parser();
        p.setCity("ARR");
        p.setLinesToParse(Arrays.asList(
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
        p.parse();


        FirebaseUpper fu = new FirebaseUpper();

        fu.writeBusStops(p.getBusStops());
        fu.writeBusStopsOnlyName(p.getBusStopsOnlyName());
        fu.writeLines(p.getLines());

    }
}
