package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;
import java.time.LocalDateTime;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing.showStartTime);
    }

    private double getDiscount(int showSequence, LocalDateTime showStartTime) {
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }
        
        double timeDiscount = 0;
        String string1 = "11:00:00";
    	Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
    	String string2 = "16:00:00";
    	Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
		if((showStartTime >= time1) && (showStartTime <= time2){
			timeDiscount = ticketPrice * 0.25;	
			// 25% discount for showtimes between 11:00 AM and 4:00 PM
		}
		
        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {

            sequenceDiscount = 2; // $2 discount for 2nd show
        }
        else if (showSequence == 7) {

            sequenceDiscount = 1; // $2 discount for 2nd show
        }
//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        // biggest discount wins
        if (specialDiscount > timeDiscount && specialDiscount > sequenceDiscount){
        return specialDiscount;
        }
        else if (timeDiscount > sequenceDiscount){
        return timeDiscount;
        }
        else{
        return sequenceDiscount;
        }
        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}