package com.acc.tools.ed.report.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

	

}
