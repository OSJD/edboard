package com.acc.tools.ed.web.controller.management;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.acc.tools.ed.integration.dao.AnnouncementDao;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaire;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaireOptions;
import com.acc.tools.ed.integration.dto.SurveySystem;

@Controller
@SessionAttributes({ "edbUser" }) 
public class AnnouncementController {
	
  private static final Logger LOG = LoggerFactory.getLogger(AnnouncementController.class);

  @Autowired
  private AnnouncementDao announcementDao;

  @RequestMapping(value="/announcements.do")
  public String announcements(		 
		  @ModelAttribute("edbUser")EDBUser edbUser,
		  Model model) {
	  
	  SurveySystem surveySystem=announcementDao.getPublishedAnnouncement();
	  
	  if(surveySystem!=null){
		  boolean isQuizAttempted=announcementDao.isQuizAttempted(surveySystem.getAnnouncementSubjectId(), edbUser.getEmployeeId());
		  LOG.debug("Is Quiz Attempted?:{}",isQuizAttempted);
		  surveySystem.setQuizAttempted(isQuizAttempted);
		  if(!isQuizAttempted){
			  surveySystem.setQuestionnaire(announcementDao.getQuestionnaire(surveySystem.getAnnouncementSubjectId()));
		  }
	  } else {
		  LOG.error("No Questions Configured");
		  surveySystem=new SurveySystem();
		  surveySystem.setAnnouncementHTMLData("<p style=\"text-align: center;color: red;font-size: large;\">No Announcements are Published.Please watch for details soon!</p>");
	  }
	  
	  model.addAttribute("surveySystemForm", surveySystem);
	  model.addAttribute("questionnaireForm", new SurveyQuestionnaire());
    return "/projectwork/announcements";
  }

  @RequestMapping({"/calendar.do"})
  public String calendar(Model model)
  {
    return "/projectwork/calendar";
  }

  @RequestMapping(value="/settings.do")
  public String settings(Model model)
  {

		SurveySystem surveySystem=new SurveySystem();
		List<ReferenceData> subjectList=announcementDao.getAllAnnouncementSubjects();
		model.addAttribute("surveySystemForm", surveySystem);
		model.addAttribute("announcementSubjectList", subjectList);

    return "/projectwork/settings";
  }
  
  @RequestMapping(value="/getAllAnnounceSubjects.do")
  public @ResponseBody List<ReferenceData> getAllAnnounceSubjects(Model model)
  {
	  List<ReferenceData> subjectList=announcementDao.getAllAnnouncementSubjects();
	  return subjectList;
  }
  
  
  @RequestMapping(value="/getAnnouncementDetailsBySubject.do")
  public @ResponseBody SurveySystem getAnnouncementDetailsBySubject(@RequestParam("announcementId") Integer announcementId,Model model)
  {

		final SurveySystem surveySystem=announcementDao.getAnnouncement(announcementId);
		if(surveySystem!=null){
		  surveySystem.setQuestionnaire(announcementDao.getQuestionnaire(announcementId));
		} else {
		  LOG.error("No Questions Configured");
		}

    return surveySystem;
  }

  @RequestMapping(value="/addquestion.do")
  public String addQuestion(@RequestParam("announcementId") Integer announcementId,Model model)
  {

	SurveyQuestionnaire sq = new SurveyQuestionnaire();
	sq.setAnnouncementId(announcementId);
	List<SurveyQuestionnaireOptions> options = new LinkedList<SurveyQuestionnaireOptions>();
	SurveyQuestionnaireOptions option1 = new SurveyQuestionnaireOptions();
	option1.setOptionOrder(1);
	SurveyQuestionnaireOptions option2 = new SurveyQuestionnaireOptions();
	option2.setOptionOrder(2);
	SurveyQuestionnaireOptions option3 = new SurveyQuestionnaireOptions();
	option3.setOptionOrder(3);
	SurveyQuestionnaireOptions option4 = new SurveyQuestionnaireOptions();
	option4.setOptionOrder(4);
	options.add(option1);
	options.add(option2);
	options.add(option3);
	options.add(option4);
	sq.setQuestionOptions(options);
	model.addAttribute("questionnaireForm", sq);

    return "/projectwork/addQuestion";
  }
  
  @RequestMapping(value="/savequestion.do")
  public @ResponseBody SurveyQuestionnaire saveQuestion(@ModelAttribute("questionnaireForm") SurveyQuestionnaire questionnaireForm,Model model)
  {
    LOG.debug("Question:{} | Answer:{} | Type:{}", new Object[]{questionnaireForm.getQuestionDescription(),questionnaireForm.getCorrectAnswer(),questionnaireForm.getQuestionType()});
    if(questionnaireForm.getQuestionType().equalsIgnoreCase("4")){
	    for (SurveyQuestionnaireOptions option : questionnaireForm.getQuestionOptions()) {
	      option.setOptionDescription(questionnaireForm.getCorrectAnswer());
	    }
    }
    this.announcementDao.addQuestion(questionnaireForm);
    model.addAttribute("questionnaireForm", questionnaireForm);
	  
    return questionnaireForm;
  }
  
  @RequestMapping(value="/addEditAnnouncement.do")
  public @ResponseBody String addAnnouncement(
		  @ModelAttribute("surveySystemForm") SurveySystem surveySystemForm,
		  Model model) {
	LOG.debug("Stop Watch:{}",surveySystemForm.getStopWatch());
    if(surveySystemForm.getAnnouncementSubjectId()==0){
    	announcementDao.addAnnouncement(surveySystemForm);
    } else {
    	announcementDao.editAnnouncement(surveySystemForm);
    }

    return "/projectwork/settings";
  }
  
  @RequestMapping(value="/surveyResponse.do")
  public @ResponseBody String surveyResponse(
		  @ModelAttribute("surveySystemForm") SurveySystem surveySystemForm,
		  @ModelAttribute("edbUser")EDBUser edbUser,
		  Model model) {
	  int totalScore=0;
	  int announcementId=0;
	  DateTime currentDate=new DateTime(System.currentTimeMillis());
	  DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
	  DateTime quizeDateTime = formatter.parseDateTime(surveySystemForm.getQuizStartDateTime());
	  long timeTaken=(currentDate.getMillis()-quizeDateTime.getMillis())/(1000*60);
	  for(SurveyQuestionnaire question:surveySystemForm.getQuestionnaire()){
		  announcementId=question.getAnnouncementId();
		  String correctAnswer=question.getCorrectAnswer().replace(",","");
		  if(question.getResponse()!=null){
			  String empResponse=question.getResponse().replace(",","");
			  if(correctAnswer.equalsIgnoreCase(empResponse)){
				  question.setIsAnswerCorrect(1);
				  totalScore++;
			  }
		  }
		  LOG.debug("Question :{} | Answer:{} | Emp Response:{} | isAnswerCorrect:{}",new Object[]{question.getQuestionDescription(),question.getCorrectAnswer(),question.getResponse(),question.getIsAnswerCorrect()});
		  announcementDao.saveSurveyResponse(question,edbUser);
	  }
	  announcementDao.saveSurveyResults(announcementId, totalScore, timeTaken, edbUser);

  return "{\"status\":\"success\"}";

  }
  
  @RequestMapping(value="/viewEvaluation.do")
  public String viewEvaluation(@RequestParam("announcementSubjectId") String announcementSubjectId,
		  Model model){
	  model.addAttribute("viewEvaluation", announcementDao.getSurveyResults(announcementSubjectId));
	  return "/projectwork/viewEvaluation";
  }
  
  
}