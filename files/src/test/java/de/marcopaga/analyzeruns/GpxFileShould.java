package de.marcopaga.analyzeruns;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import de.marcopaga.gpx.GpxType;
import de.marcopaga.gpx.TrkType;
import de.marcopaga.gpx.TrksegType;
import de.marcopaga.gpx.WptType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.bind.JAXB;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class GpxFileShould {

    private GpxType gpxRoot;

    @Before
    public void setUp() throws Exception {
        final InputStream sampleResourceAsStream = getClass().getClassLoader().getResourceAsStream("sample.gpx");
        gpxRoot = JAXB.unmarshal(sampleResourceAsStream, GpxType.class);
    }

    @Test
    public void be_loaded_successfully() {
        assertThat(gpxRoot.getCreator(), is("iSmoothRun - http://www.ismoothrun.com"));
    }

    @Test
    public void contain_correct_number_of_tracks() {
        assertThat(gpxRoot.getTrk().size(), is(1));
    }

    public class FirstTrackShould {

        private TrkType firstTrack;

        @Before
        public void setUp() throws Exception {
            firstTrack = gpxRoot.getTrk().get(0);
        }

        @Test
        public void contain_the_correct_meta_data() throws Exception {
            assertThat(firstTrack.getName(), is("Freies Training"));
            assertThat(firstTrack.getDesc(), is("Beschreibung"));
        }

        @Test
        public void contain_the_correct_number_of_Track_Segments() throws Exception {
            assertThat(firstTrack.getTrkseg().size(), is(1));
        }


        @Test
        public void correct_number_of_items() throws Exception {
            assertThat(firstTrack.getTrkseg().size(), is(1));
        }

        public class FirstTrackSegmentShould{

            TrksegType trackSegment;
            List<WptType> waypoints;

            @Before
            public void setUp(){
                trackSegment = firstTrack.getTrkseg().get(0);
                waypoints = trackSegment.getTrkpt();
            }

            @Test
            public void contain_correct_number_of_waypoints() throws Exception {
                assertThat(waypoints.size(),is(924));
            }

            public class FirstWaypointShould{

                private WptType firstWaypoint;

                @Before
                public void setUp(){
                    firstWaypoint = waypoints.get(0);
                }

                @Test
                public void contain_the_correct_data() throws Exception {
                 assertThat(firstWaypoint.getLat(), is(equalTo(BigDecimal.valueOf(51.241608))));
                 assertThat(firstWaypoint.getLon(), is(equalTo(BigDecimal.valueOf(6.759647))));
                 assertThat(firstWaypoint.getEle(), is(equalTo(BigDecimal.valueOf(32.777943))));
                 assertThat(firstWaypoint.getTime().toString(), is(equalTo("2016-07-04T17:34:58Z")));
                }

                @Test
                public void contain_the_correct_extension_data() throws Exception {
                    assertThat(firstWaypoint.getExtensions().getAny().size(), is(1));
                }

            }
        }
    }

}
