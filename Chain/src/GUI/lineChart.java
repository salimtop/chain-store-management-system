package GUI;


import chainmanagement.Store;
import chainmanagement.TimeDifference;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import GUI.ceoSupervisorPanel.*;
import chainmanagement.ParseString;
import java.awt.Image;
import java.time.LocalDate;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ACK
 */
public class lineChart {
    
    private static final long serialVersionUID = 1L;

    public static Store fill = new Store();
    public static int id;

  public lineChart(JPanel j){
     fill = (Store)fill.get_item(id);
    // Create dataset
    final DefaultCategoryDataset dataset = createDataset();
    // Create chart
    final JFreeChart chart = ChartFactory.createLineChart(
        "Endorsement", // Chart title
        "Date", // X-Axis Label
        "Daily Endorsement", // Y-Axis Label
        dataset
        );
    
   CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(new Color(0xffffe0));
    
    plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);
   
    CategoryAxis domainAxis = plot.getDomainAxis();
domainAxis.setCategoryLabelPositions(
    CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));
    
    ChartPanel panel = new ChartPanel(chart);
    
    panel.setMouseZoomable(true);
    
    j.setLayout(new BorderLayout());
    j.add(panel);
  
    
    
      
  }

  private DefaultCategoryDataset createDataset() {

    String series1 = "Endorsement";
 

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
   /* Calendar current = Calendar.getInstance();
    Date cr = Calendar.getInstance().getTime();
    current.setTime(cr);*/
      System.out.println("checked");
    YearMonth yearMonthObject = YearMonth.of(Calendar.YEAR, LocalDate.now().getMonthValue()-1);
    int daysInMonth = yearMonthObject.lengthOfMonth();
      System.out.println(daysInMonth);
    int dayOf = LocalDate.now().getDayOfMonth(); 
    int dayAgo = daysInMonth-2;
    int index = daysInMonth;
      for (int i = daysInMonth; i != daysInMonth-1 ; i = (i+1)%daysInMonth) {
          dataset.addValue(fill.getEndor()[(dayOf)%daysInMonth+(31-daysInMonth)], series1, TimeDifference.dayAgo(dayAgo--));
          dayOf++;
          //index = (index+1)%daysInMonth;
      }
    
   /* dataset.addValue(200, series1, "2016-12-01");
    dataset.addValue(200, series1, "2016-12-02");
    dataset.addValue(200, series1, "2016-12-03");
    dataset.addValue(200, series1, "2016-12-04");
    dataset.addValue(200, series1, "2016-12-05");
    dataset.addValue(200, series1, "2016-12-06");
    dataset.addValue(200, series1, "2016-12-07");
    dataset.addValue(200, series1, "2016-12-08");
    dataset.addValue(150, series1, "2016-12-09");
    dataset.addValue(100, series1, "2016-12-10");
    dataset.addValue(210, series1, "2016-12-11");
    dataset.addValue(240, series1, "2016-12-12");
    dataset.addValue(200, series1, "2016-12-14");
    dataset.addValue(150, series1, "2016-12-15");
    dataset.addValue(100, series1, "2016-12-16");
    dataset.addValue(210, series1, "2016-12-17");
    dataset.addValue(240, series1, "2016-12-18");
    dataset.addValue(200, series1, "2016-12-19");
    dataset.addValue(150, series1, "2016-12-20");
    dataset.addValue(100, series1, "2016-12-21");
    dataset.addValue(210, series1, "2016-12-22");
    dataset.addValue(2400, series1,"2016-12-23");
    dataset.addValue(-195, series1, "2016-12-24");
    dataset.addValue(245, series1, "2016-12-25");
    dataset.addValue(-200, series1, "2016-12-26");
    dataset.addValue(-150, series1, "2016-12-27");
    dataset.addValue(100, series1, "2016-12-28");
    dataset.addValue(210, series1, "2016-12-29");
    dataset.addValue(240, series1, "2016-12-30");
    dataset.addValue(195, series1, "2016-12-31");*/
   
  
    return dataset;
  }

  
    
    
}
