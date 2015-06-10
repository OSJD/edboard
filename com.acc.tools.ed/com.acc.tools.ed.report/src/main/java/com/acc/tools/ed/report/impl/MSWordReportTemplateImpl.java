package com.acc.tools.ed.report.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;
import org.docx4j.wml.Hdr;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.report.MSWordReportTemplate;
import com.acc.tools.ed.report.dto.WeeklyStatusReportData;

@Service("msWordReportTemplate")
public class MSWordReportTemplateImpl implements MSWordReportTemplate{

	public  OutputStream generateWordWeeklyStatusReport(WeeklyStatusReportData reportData) throws IOException, Docx4JException, URISyntaxException {
		
		String docMarshalled = generateWSRDocumentPartxml(reportData.getProjectName(), reportData.getReleaseName(), reportData.getStartDate(),
				reportData.getEndDate(), reportData.getRevisedEndDate(), reportData.getStatus(), reportData.getMilestoneMitigation(), reportData.getCurrentTasks(),
				reportData.getUpcomingTasks());
		String hdrMarshalled=generateWSRHeaderPartxml(reportData.getProgramName(),reportData.getProjectName(),reportData.getReportingPeriod());
		
		return generateDocx(docMarshalled,hdrMarshalled,"resources/wms_template.docx");
	}

	/*
	 * generates xml in string format from header of template.
	 * Replaces expressions like **projectName**, **programName** in template's header 
	 * with our values.
	 * @returns HeaderPart's JaxBElement xml in String.
	 */
	private String generateWSRHeaderPartxml(String programName,String projectName,
			String reportingPeriod) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub
		String headerPartMarshalled=readFile("resources/hdr.txt",
				Charset.defaultCharset());
		StringUtils.replaceEach(headerPartMarshalled, new String[]{"**ProgramName**","**ProjectName**","**Period**"}, 
				new String[]{programName,projectName,reportingPeriod});

		
		return headerPartMarshalled;
	}

	/*
	 * generates docx file using xml String of MainDocumentPart and HeaderPart.
	 */
	private OutputStream generateDocx(String content,String hdr,String template) {

		ByteArrayOutputStream byteOutputStream=new ByteArrayOutputStream();

		try {

			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.
					load(MSWordReportTemplateImpl.class.getClassLoader().getResourceAsStream(template));
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl = (Document) XmlUtils
					.unmarshalString(content);
			// Create main document part content
			documentPart.setJaxbElement(wmlDocumentEl);
			
			org.docx4j.wml.Hdr header=(Hdr)XmlUtils.unmarshalString(hdr);
			
			
			List<SectionWrapper> sectionWrappers = wordMLPackage
					.getDocumentModel().getSections();

			for (SectionWrapper sw : sectionWrappers) {
				HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
				
				
				//System.out.println("Headers:");
				 if (hfp.getFirstHeader()!=null) 
				 {
					// System.out.println("-first");
					 HeaderPart headerPart=hfp.getFirstHeader();
					 headerPart.setJaxbElement(header);
					 
				 }
				
				
			}

			
			
			wordMLPackage.save(byteOutputStream);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return byteOutputStream;
	}
	/*
	 * generates xml in string format for MainDocumentPart.
	 * 
	 * @returns Document Part's JaxBElement xml in String.
	 */
	private String generateWSRDocumentPartxml(String projectName,
			String releaseName, String sDate, String tDate,
			String revisedTDate, String status, String milestoneMitigation,
			List<String> currentTasks, List<String> upcomingTasks)
			throws IOException, Docx4JException, URISyntaxException {

		String documentPartMarshalled = readFile("resources/1.txt",Charset.defaultCharset());
		documentPartMarshalled += projectName;
		documentPartMarshalled += readFile("resources/2.txt", Charset.defaultCharset());
		documentPartMarshalled += releaseName;
		documentPartMarshalled += readFile("resources/3.txt", Charset.defaultCharset());
		documentPartMarshalled += sDate;
		documentPartMarshalled += readFile("resources/4.txt", Charset.defaultCharset());
		documentPartMarshalled += tDate;
		documentPartMarshalled += readFile("resources/5.txt", Charset.defaultCharset());
		documentPartMarshalled += revisedTDate;
		documentPartMarshalled += readFile("resources/6.txt", Charset.defaultCharset());
		documentPartMarshalled += status;
		documentPartMarshalled += readFile("resources/7.txt", Charset.defaultCharset());
		documentPartMarshalled += milestoneMitigation;
		documentPartMarshalled += readFile("resources/8.txt", Charset.defaultCharset());
		int index = currentTasks.size();
		int i = 1;
		String accomplishmentFormat = readFile("resources/9.txt",
				Charset.defaultCharset());
		while (index >= i) {

			String accomplishment = currentTasks.get(i - 1);
			String tempAccomplishment = accomplishmentFormat.replace(
					"****sampleTask****", accomplishment);
			documentPartMarshalled += tempAccomplishment;
			i++;

		}

		documentPartMarshalled += readFile("resources/10.txt", Charset.defaultCharset());
		index = upcomingTasks.size();
		i = 1;
		String upcomingFormat = readFile("resources/11.txt",
				Charset.defaultCharset());
		while (index >= i) {

			String upcomingTask = upcomingTasks.get(i - 1);
			String tempUpcomingTask = accomplishmentFormat.replace(
					"****UpcomingTasks****", upcomingTask);
			documentPartMarshalled += tempUpcomingTask;
			i++;

		}

		documentPartMarshalled += readFile("resources/12.txt", Charset.defaultCharset());

		//System.out.println(documentPartMarshalled);

		return documentPartMarshalled;
	}

	/*
	 * reads .txt files
	 * @returns string with all the file contents.
	 */
	private String readFile(String path, Charset encoding) throws IOException, URISyntaxException {
		final InputStream inputStrm= MSWordReportTemplateImpl.class.getClassLoader().getResourceAsStream(path);
		byte[] encoded=IOUtils.toByteArray(inputStrm);
		return new String(encoded, encoding);
	}
	
	public OutputStream generateWordWeeklyStatusReportPDF(WeeklyStatusReportData reportData) throws IOException, Docx4JException, URISyntaxException {
		
		ByteArrayOutputStream outputPDF = null;
		
		StringBuilder xmlFormat = createHeaderXML();
		
		xmlFormat.append("<EDB>");
		xmlFormat.append("<releaseName>").append(reportData.getReleaseName()).append("</releaseName>");
		xmlFormat.append("<projectName>").append(reportData.getProjectName()).append("</projectName>");
		xmlFormat.append("<startDate>").append(reportData.getStartDate()).append("</startDate>");
		xmlFormat.append("<endDate>").append(reportData.getEndDate()).append("</endDate>");
		xmlFormat.append("<status>").append(reportData.getStatus()).append("</status>");
		xmlFormat.append("</EDB>");
		xmlFormat.append("</root>");
		System.out.println(xmlFormat.toString());
		
		outputPDF = GeneratestatusReportPDF(xmlFormat);
		
		
		// TODO Auto-generated method stub
		return outputPDF;
	}

	private ByteArrayOutputStream GeneratestatusReportPDF(StringBuilder xmlFormat) {
		ByteArrayOutputStream output = null;
		try{
			Map<String, Object> params = new HashMap<String, Object>();
			org.w3c.dom.Document document;
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	    	InputStream stream = new ByteArrayInputStream(xmlFormat.toString().getBytes(StandardCharsets.UTF_8));
	
	    	document = documentBuilder.parse(stream);
	    	
	    	params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
	        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
	        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
	        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
	        params.put(JRParameter.REPORT_LOCALE, Locale.US);
	       
	        
	        JasperReport report =  JasperCompileManager.compileReport(MSWordReportTemplateImpl.class.getClassLoader().getResourceAsStream("resources/JRXML/PROJ_STTS_RPT.jrxml"));
	        JasperPrint print = JasperFillManager.fillReport(report,params);
	        
	        byte[] bytes= JasperExportManager.exportReportToPdf(print);
	        output = new ByteArrayOutputStream(bytes.length);
	        output.write(bytes, 0, bytes.length);
		}catch(Exception e){
			
		}
		return output;
	}

	private StringBuilder createHeaderXML() {
		// TODO Auto-generated method stub
		StringBuilder BaseDocument = new StringBuilder();
		   BaseDocument.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		   BaseDocument.append("<root>");
		   BaseDocument.append("<Header>");
		   BaseDocument.append("<reportTitle>").append("Status Report").append("</reportTitle>");
		   BaseDocument.append("</Header>");
		   return (BaseDocument);
	}

	

}
