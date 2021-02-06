package org.zaboev;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightTimeStatistics {
    public static void main(String[] args) {

        List<Long> listOfFlightTime=getListOfFlightTimeFromTickets(args[0]);
        long averageFlightTime=getAverageFlightTime(listOfFlightTime);
        long percentileOfFlightTime=getPercentileFlightTime(90,listOfFlightTime);
        System.out.println("Average flight time = " +
                averageFlightTime+" minutes \n90th percentile of flight time = "+
                percentileOfFlightTime+" minutes");
    }
    private static List<Long> getListOfFlightTimeFromTickets(String fileName){
        List<Long> listOfFlightTime=new ArrayList<>();
        JSONParser jsonParser=new JSONParser();
        try(FileReader fileReader=new FileReader(fileName)) {
            JSONObject tickets=(JSONObject) jsonParser.parse(fileReader);
            JSONArray ticketsList=(JSONArray) tickets.get("tickets");
            for (Object ticket:ticketsList){
                Duration time=getFlightTimeFromTicket((JSONObject) ticket);
                listOfFlightTime.add(time.toMinutes());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listOfFlightTime;
    }
    private static long getAverageFlightTime(List<Long> listOfFlightTime){
        long sumOfFlightTime=listOfFlightTime.stream().mapToLong(t->t).sum();
        return sumOfFlightTime/listOfFlightTime.size();

    }
    private static String checkTimeFormat(String time){
        if (time.length()<5)
            time=0+time;
        return time;
    }
    private static Duration getFlightTimeFromTicket(JSONObject ticket){
        String departure_time= (String) ticket.get("departure_time");
        String arrival_time= (String) ticket.get("arrival_time");
        LocalTime time1=LocalTime.parse(checkTimeFormat(departure_time));
        LocalTime time2=LocalTime.parse(checkTimeFormat(arrival_time));
        return Duration.between(time1,time2);
    }
    private static long getPercentileFlightTime(int percentile,List<Long> listOfFlightTime){
        List<Long> listOfFlightTimeSorted=listOfFlightTime.stream().sorted().collect(Collectors.toList());
        double k=listOfFlightTime.size()*percentile/100;
        return listOfFlightTimeSorted.get((int)Math.ceil(k));
    }


}

