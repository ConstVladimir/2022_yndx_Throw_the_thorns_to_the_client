import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    private static final String inputFile = "input.txt";
    private static final String outputFile = "output.txt";

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        int n = Integer.parseInt(reader.readLine());
        TreeMap <Integer, TreeMap> rockets = new TreeMap<>();
        for (int i = 0; i<n; i++){
            String[] event = reader.readLine().split(" ");
            Integer ev_rock = Integer.parseInt(event[3]);
            //Integer mod_date = Integer.parseInt(event[0])*10000 + Integer.parseInt(event[1])*100 + Integer.parseInt(event[2]);
            TreeMap<Event_date,String> trips = rockets.get(ev_rock);
            if ( trips != null){
                trips.put(new Event_date(event), event[4]);
            }
            else {
                TreeMap<Event_date, String > trip= new TreeMap<>();
                trip.put(new Event_date(event), event[4]);
                rockets.put(ev_rock,trip);
            }
        }

        StringBuilder itog = new StringBuilder();

        while (!rockets.isEmpty()){
            Map.Entry<Integer, TreeMap> rocket = rockets.pollFirstEntry();
            TreeMap<Event_date, String> trips = rocket.getValue();
            int a = 0;
            Event_date start = new Event_date(new String[]{"0", "0", "0"});
            Event_date finish;
            while (!trips.isEmpty()){
                Map.Entry<Event_date,String> trip = trips.pollFirstEntry();
                if (trip.getValue().equals("A")){
                    start = trip.getKey();
                }
                if (trip.getValue().equals("C") || trip.getValue().equals("S")){
                    finish = trip.getKey();
                    a+=((finish.day- start.day)*24+(finish.hour- start.hour))*60+ finish.min- start.min;
                }
            }
            itog.append(a+" ");
        }

        FileWriter file = new FileWriter(outputFile);
        file.write(itog.substring(0));
        file.close();
    }


}
class Event_date implements Comparable<Event_date>{
    int day=0;
    int hour=0;
    int min=0;
    Event_date (String[] event){
        this.day = Integer.parseInt(event[0]);
        this.hour = Integer.parseInt(event[1]);
        this.min = Integer.parseInt(event[2]);
    }

    public int compareTo(Event_date p){
        if (this.day < p.day) return -1;
        else if (this.day > p.day) return 1;
        else {
            if (this.hour < p.hour) return -1;
            else if (this.hour > p.hour) return 1;
            else {
                if (this.min < p.min) return -1;
                else if (this.min > p.min) return 1;
                else return 0;
            }
        }
    }
}