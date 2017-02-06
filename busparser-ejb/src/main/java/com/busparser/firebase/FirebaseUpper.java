package com.busparser.firebase;

import com.busparser.bean.BusStop;
import com.busparser.bean.Line;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class FirebaseUpper {

    private DatabaseReference busStopsRef;
    private DatabaseReference linesRef;
    private DatabaseReference busStopsRefOnlyName;
    private static final Logger LOG = Logger.getLogger(FirebaseUpper.class.getName());

    public FirebaseUpper() throws FileNotFoundException {

        FirebaseOptions options = new FirebaseOptions.Builder().setServiceAccount(new FileInputStream(System.getProperty("user.home") + "/busappararas0.json")).
                setDatabaseUrl("https://busappararas0.firebaseio.com").build();

        FirebaseApp.initializeApp(options);

        busStopsRefOnlyName = FirebaseDatabase.getInstance().getReference("busStopsOnlyName");
        busStopsRef = FirebaseDatabase.getInstance().getReference("busStops");
        linesRef = FirebaseDatabase.getInstance().getReference("lines");
    }

    public void writeBusStopsOnlyName(List<BusStop> busStops) {
        LOG.log(Level.INFO, "Writing writeBusStopsOnlyName");
        busStopsRefOnlyName.setValue(busStops);
        LOG.log(Level.INFO, "Done Writing writeBusStopsOnlyName");
    }

    public void writeBusStops(List<BusStop> busStops) {
        LOG.log(Level.INFO, "Writing writeBusStops");
        busStopsRef.setValue(busStops);
        LOG.log(Level.INFO, "Done Writing writeBusStops");
    }

    public void writeLines(Set<Line> lines) {
        LOG.log(Level.INFO, "Writing writeLines");
        linesRef.setValue(new ArrayList<Line>(lines));
        LOG.log(Level.INFO, "Done Writing writeLines");
    }
}
