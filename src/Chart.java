
		import javax.swing.JFrame;






import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.Year;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;



public class Chart extends ApplicationFrame  {

  private static final long serialVersionUID = 1L;
  private Date startDate;
  private Date endDate;

  
  public Chart(final String title,String start,String end) throws ParseException {
        super(title);
        
        
        startDate = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH).parse(start);
        endDate = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH).parse(end);
        
        IntervalXYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1500, 800));
        setContentPane(chartPanel);

    }

    private  IntervalXYDataset createDataset() throws ParseException {
    	final TimeSeries series = new TimeSeries("date/time");
    	 
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(endDate); 
    	c.add(Calendar.DATE, 1);
    	endDate = c.getTime();
    	 
    	List<Data> dataList = new ArrayList<Data>();
    	 
    	  try
    	  {
    		  FileInputStream fstream = new FileInputStream("access.log");
    		  DataInputStream in = new DataInputStream(fstream);
    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		  String dateStr;
    		  String strLine;
    		  String strTemp;

    		  while ((strLine = br.readLine()) != null)   {//get by line and process
    				StringTokenizer st = new StringTokenizer(strLine);
    				
    				Data data = new Data();
    				data.setIp((String)st.nextElement());

    				strTemp = (String)st.nextElement();//these items are not needed
    				strTemp = (String)st.nextElement();
    				dateStr = (String)st.nextElement();//date is conceived

    				dateStr = dateStr.replace('[',' '); 
    				dateStr = dateStr.replaceFirst(":", " ");
    				
    				Date date = new SimpleDateFormat("dd/MMM/y H",Locale.ENGLISH).parse(dateStr);
    				
    				data.setDate(date);
    				if (((data.getDate().compareTo(startDate)>0) && (data.getDate().compareTo(endDate)<0)))
    					dataList.add(data);
    		  }
    		  in.close();
    		    }catch (Exception e){
    		    	
    		    	System.err.println("Error: " + e.getMessage());
    		  
    		  }
    	  
    	  
    	Collections.sort(dataList, new CustomComparator());
    	List<Data> dataListForChart = new ArrayList<Data>();
    	
    	//dataList is now ordered by Date and by ip lexicographically
    	for (Data data : dataList)//for each row in ArrayList
    	{
    		if (dataListForChart.contains(data))//if the dataListForChart contains the specific row
    		{
    			int index = dataListForChart.indexOf(data); // find where is the row
    			if (data.getIp().equalsIgnoreCase(dataListForChart.get(index).getIp())) //if it is the same with the previous one do nothing
    				continue;
    			else //if it is different this means the loop has moved to the next ip address so you must count++
    			{
	    			int count = dataListForChart.get(index).getCount();
	    			count++;
	    			dataListForChart.get(index).setCount(count);
	    			dataListForChart.get(index).setIp(data.getIp());
    			}
    			
    		}
    		else //if the execution is here this means that another date has reached from the loop
    		{
    			Data newData = new Data();
    			newData.setCount(0);
    			newData.setIp(data.getIp());
    			newData.setDate(data.getDate());;
    			dataListForChart.add(newData);
    		}
    		
    	}
    	

    	
    	
    	
    	
    	int index = 0;
    	System.out.println("Exported data are:");
    	while (index < dataListForChart.size())
    	{
    		
    		System.out.println(dataListForChart.get(index).getDate()+" " + dataListForChart.get(index).getCount());
    		index++;
    	}

    	index = 0;
	    while (index < dataListForChart.size())
	    {
	    	series.add(new TimeSeriesDataItem(new Hour(dataListForChart.get(index).getDate()),dataListForChart.get(index).getCount()));
	    	index++;
	    }
         final TimeSeriesCollection dataset = new TimeSeriesCollection();
         dataset.addSeries(series);
         return dataset;
    }
    
    

 private JFreeChart createChart(final IntervalXYDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createXYBarChart(
            "#ip - date/time",      
            "date",                     
            true,
            "#ip",                      
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false

        );

        chart.setBackgroundPaint(Color.white);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
        
    }
} 