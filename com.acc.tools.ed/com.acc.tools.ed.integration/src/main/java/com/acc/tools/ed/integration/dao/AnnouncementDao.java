package com.acc.tools.ed.integration.dao;

import java.util.List;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaire;
import com.acc.tools.ed.integration.dto.SurveyResults;
import com.acc.tools.ed.integration.dto.SurveySystem;

public abstract interface AnnouncementDao{
	public abstract void addAnnouncement(SurveySystem surveySystem);
	public abstract void editAnnouncement(SurveySystem surveySystem);
	public abstract void addQuestion(SurveyQuestionnaire surveyQuestionnaire);
	public abstract SurveySystem getAnnouncement(Integer announcementId);
	public SurveySystem getPublishedAnnouncement();
	public abstract List<SurveyQuestionnaire> getQuestionnaire(Integer announcementId);
	public List<ReferenceData> getAllAnnouncementSubjects();
	public void saveSurveyResponse(SurveyQuestionnaire question,EDBUser user);
	public void saveSurveyResults(int announcementId ,int score,long timeTaken, EDBUser user);
	public boolean isQuizAttempted(int announcementId,String empId);
	public List<SurveyResults> getSurveyResults(String announcementId);
}