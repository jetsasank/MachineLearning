package com.jet.ml.impl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

import com.jet.ml.model.BusinessInfo;
import com.jet.ml.model.SearchCriteria;

/**
 * Title: HelperClass.java<br>
 * Description: <br>
 * Created: 06-Dec-2015<br>
 * Copyright: Copyright (c) 2015<br>
 * @author Teja Sasank Gorthi (jet.sasank@gmail.com)
 */
public class HelperClass {

    private static final String CSV_SEPARATOR = ",";
    private static final String FORMAT = ".csv";
    private static final int ZERO = 0; 

    public void generateCSVdataForBusiness(List<BusinessInfo> businessess, SearchCriteria criteria) {
        StringBuffer oneLine = new StringBuffer();
        for (BusinessInfo business : businessess)
        {
                //Business Info
                oneLine.append(business.isBusinessOpen() ? "1" : "0");
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.isWeekdaysOpen() ? "1" : "0");
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.isWeekendsOpen() ? "1" : "0");
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getStars());
                oneLine.append(CSV_SEPARATOR);

                //Business Location Info
                oneLine.append(business.getNeighbour_hood_count());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getDistance());
                oneLine.append(CSV_SEPARATOR);

                //CheckIn Information
                oneLine.append(business.getNumberOfCheckIns());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumberOfCheckInsOnWeekday());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumberOfCheckInsOnWeekend());
                oneLine.append(CSV_SEPARATOR);

                //Review Count Info
                oneLine.append(business.getReview_count());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumber_elite_user_review_count());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumber_of_reviews_last_twelve_months());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumber_of_reviews_twelve_months_before_the_end_of_last_twelve_months());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(business.getNumber_of_reviews__until_two_year_before());
                oneLine.append(CSV_SEPARATOR);

                //Review Ratings
                if (business.getNumber_of_reviews() != 0) {
                oneLine.append(Math.round(business.getTotal_review_length()/business.getNumber_of_reviews()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(new DecimalFormat("#.##").format(business.getTotal_stars_review()/business.getNumber_of_reviews()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_useful_review()/business.getNumber_of_reviews()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_cool_review()/business.getNumber_of_reviews()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_funny_review()/business.getNumber_of_reviews()));
                oneLine.append(CSV_SEPARATOR);
                } else {
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                }

                //User Ratings
                if (business.getNumber_of_users() != 0) {
                oneLine.append(new DecimalFormat("#.##").format(business.getTotal_star_user()/business.getNumber_of_users()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_useful_user()/business.getNumber_of_users()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_cool_user()/business.getNumber_of_users()));
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(Math.round(business.getTotal_funny_user()/business.getNumber_of_users()));
                } else {
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(ZERO);
                    oneLine.append(CSV_SEPARATOR);
                }

                oneLine.append("\n");
        }
        writeToCSV(oneLine.toString(), criteria.getState());
        System.out.println("Generated a CSV for Businesses in the State of : " + criteria.getState());
        System.out.println("------------------------------------------------------------------------------------");
    }

    private void writeToCSV( String data, String name) {
        String file_name = name + FORMAT;
        try
         {
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file_name), "UTF-8"));
                 bw.write(data);
                 bw.newLine();
             bw.flush();
             bw.close();
         }
         catch (UnsupportedEncodingException e) {
           System.out.println("Houston, we've had a problem here !");
           e.printStackTrace();
         }
         catch (FileNotFoundException e) {
           System.out.println("Houston, we've had a problem here !");
           e.printStackTrace();
         }
         catch (IOException e) {
          System.out.println("Houston, we've had a problem here !");
          e.printStackTrace();
         }
    }

    public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
          dist = dist * 1.609344;
        } else if (unit == 'N') {
          dist = dist * 0.8684;
          }
        return (dist);
      }

      private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
}
