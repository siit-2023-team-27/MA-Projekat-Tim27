package com.example.nomad.dto;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateRange {
    private Date startDate;
    private Date finishDate;

    // Constructor
    public DateRange(Date startDate, Date finishDate) {
        if (startDate == null || finishDate == null || startDate.after(finishDate)) {
            throw new IllegalArgumentException("Invalid date range. Start date must be before the end date.");
        }
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public DateRange(String startDate, String finishDate)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startDate);
            Date finish = dateFormat.parse(finishDate);

            if (start == null || finish == null || start.after(finish)) {
                throw new IllegalArgumentException("Invalid date range. Start date must be before the end date.");
            }
            this.startDate = start;
            this.finishDate = finish;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DateRange() {}

    public boolean overlaps(DateRange comparedDateRange) {
        return !this.finishDate.before(comparedDateRange.getStartDate()) && !this.startDate.after(comparedDateRange.getFinishDate());
    }

    // Getters for each attribute
    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    @Override
    public String toString() {
        return "[ " + startDate.toString() + ", " + finishDate.toString() + " ]";
    }
}
