package com.acc.tools.ed.sonar;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.ManualMeasure;
import org.sonar.wsclient.services.ManualMeasureCreateQuery;
import org.sonar.wsclient.services.ManualMeasureQuery;


public class SonarWebServiceClient {

	public static void main(String[] args) {
		String url = "http://localhost:9000";
		String login = "admin";
		String password = "admin";

		Sonar sonar = new Sonar(new HttpClient4Connector(new Host(url, login,
				password)));

		// Connector con=sonar.getConnector();
		// System.out.println(con);

		String projectKey = "com.acc.tools.ed:com.acc.tools.ed";

		String manualMetricKey = "burned_budget";

		sonar.create(ManualMeasureCreateQuery.create(projectKey,
				manualMetricKey).setValue(50.0));
		
		
		for (ManualMeasure manualMeasure : sonar.findAll(ManualMeasureQuery
				.create(projectKey))) {

			System.out.println("Manual measures on project: " + manualMeasure);

		}
	}

}

