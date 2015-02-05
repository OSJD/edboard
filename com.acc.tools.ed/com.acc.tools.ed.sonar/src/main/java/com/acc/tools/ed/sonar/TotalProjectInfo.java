package com.acc.tools.ed.sonar;


import java.util.List;

import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.ResourceQuery;


public class TotalProjectInfo {

	static String resourceKey = "com.acc.tools.ed:com.acc.tools.ed";

    //Measures which will be returned

    static String[] MEASURES_TO_GET = new String[]{"file_complexity", "class_complexity","function_complexity","lcom4","complexity","ncloc","functions","files","classes","packages"};
    public static Sonar localSonar;

  public static void main(String[] args) 
  {
        try {

        //  To Create Connection Using Resource Key which is Project name , user Name and Password

      localSonar = Sonar.create("http://localhost:9000", "admin", "admin");

            ResourceQuery query = ResourceQuery.createForMetrics(resourceKey, MEASURES_TO_GET);
            query.setIncludeTrends(true);
            Object resource =  localSonar.find(query);
            //To get measures               

        List<Measure> allMeasures = ((org.sonar.wsclient.services.Resource) resource).getMeasures();


            for (Measure measure : allMeasures) {
                System.out.println("Statements : " + measure.getMetricKey()
                        + " === " + measure.getFormattedValue());
                }
            System.out.println("lcom4 means Lack of Cohesion of Functions");
            System.out.println("ncloc means Non Commenting Lines of Code");



            } catch(Exception e){
                e.printStackTrace();
                }


    }


}
