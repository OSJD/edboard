package com.acc.tools.ed.integration.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acc.tools.ed.integration.dao.AnnouncementDao;
import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.dto.ReferenceData;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaire;
import com.acc.tools.ed.integration.dto.SurveyQuestionnaireOptions;
import com.acc.tools.ed.integration.dto.SurveyResults;
import com.acc.tools.ed.integration.dto.SurveySystem;

@Service("announcementDao")
public class AnnouncementDaoImpl extends AbstractEdbDao implements AnnouncementDao{
	
  private static final Logger log = LoggerFactory.getLogger(AnnouncementDaoImpl.class);

  public void addQuestion(SurveyQuestionnaire questionnaire){
    try {
    	
	      final String questionnaireTable = "insert into EDB_QSTNER(ANCMNT_ID,QSTNER_QUEST,QSTNER_TYP,QSTNER_ANS,QSTNER_CRT_DT) values (?,?,?,?,?)";
	      PreparedStatement preparedStatement = getConnection().prepareStatement(questionnaireTable);
	      preparedStatement.setInt(1, questionnaire.getAnnouncementId());
	      preparedStatement.setString(2, questionnaire.getQuestionDescription());
	      preparedStatement.setString(3, questionnaire.getQuestionType());
    	  preparedStatement.setString(4, questionnaire.getCorrectAnswer());	    	  
	      preparedStatement.setString(5, new LocalDateTime().toString("yyyy-MM-dd"));
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	
	      int questionId = 0;
	      Statement statement = getConnection().createStatement();
	      ResultSet rs = statement.executeQuery("SELECT MAX(QSTNER_ID) FROM EDB_QSTNER");
	      while (rs.next()) {
	        questionId = rs.getInt(1);
	      }
	      statement.close();
	      final String optionTable = "insert into EDB_QSTNER_OPTN(QSTNER_ID,OPTN_VALUE,OPTN_DESC) values (?,?,?)";
		  PreparedStatement optPrepStmt = getConnection().prepareStatement(optionTable);
		  for (SurveyQuestionnaireOptions option : questionnaire.getQuestionOptions()) {
		     optPrepStmt.setInt(1, questionId);
		     optPrepStmt.setInt(2, option.getOptionOrder());
		     optPrepStmt.setString(3, option.getOptionDescription());
		     optPrepStmt.addBatch();
		  }
		  optPrepStmt.executeBatch();
		  optPrepStmt.close();
	      
	
	      log.debug("Question Added Id :{} | {}", Integer.valueOf(questionId), questionnaire.getQuestionDescription());
    } catch (Exception e) {
      log.error("Error Inserting into EDB_QSTNER and EDB_QSTNER_OPTN tables -", e);
    }
  }

	public void addAnnouncement(SurveySystem surveySystem) {
	    try {
			final String announcementTable = "insert into EDB_ANCMNTS(ANCMNT_SUB,ANCMNT_DESC,ANCMNT_CRT_DT,ANCMNT_PUB,ANCMNT_STTM,QUIZ_ST_DTM,STP_WCH) values (?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(announcementTable);
			preparedStatement.setString(1, surveySystem.getAnnouncementSubject());
			preparedStatement.setString(2, surveySystem.getAnnouncementHTMLData());
			preparedStatement.setString(3, new LocalDateTime().toString("yyyy-MM-dd"));
			preparedStatement.setBoolean(4, surveySystem.isAnnouncementPublished());
			preparedStatement.setBoolean(5, surveySystem.isSetTime());
			preparedStatement.setString(6, surveySystem.getQuizStartDateTime());
			preparedStatement.setInt(7, surveySystem.getStopWatch());
			preparedStatement.executeUpdate();
			preparedStatement.close();
	    }
	    catch(Exception e){
	    	log.error("Error Inserting into EDB_ANCMNTS table -", e);
	    }

	}
	
	public void editAnnouncement(SurveySystem surveySystem) {
	    try {
			final String announcementTable = "UPDATE EDB_ANCMNTS SET ANCMNT_DESC =?,ANCMNT_CRT_DT=?,ANCMNT_PUB=?,ANCMNT_STTM=?,QUIZ_ST_DTM=?,STP_WCH=? WHERE ANCMNT_ID=?";
			PreparedStatement preparedStatement = getConnection().prepareStatement(announcementTable);
			preparedStatement.setString(1, surveySystem.getAnnouncementHTMLData());
			preparedStatement.setString(2, new LocalDateTime().toString("yyyy-MM-dd"));
			preparedStatement.setBoolean(3, surveySystem.isAnnouncementPublished());
			preparedStatement.setBoolean(4, surveySystem.isSetTime());
			preparedStatement.setString(5, surveySystem.getQuizStartDateTime());
			preparedStatement.setInt(6, surveySystem.getStopWatch());
			preparedStatement.setInt(7, surveySystem.getAnnouncementSubjectId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
	    }
	    catch(Exception e){
	    	log.error("Error Updating into EDB_ANCMNTS table -", e);
	    }

	}
	
	public List<ReferenceData> getAllAnnouncementSubjects(){
		
		final List<ReferenceData> subjects=new ArrayList<ReferenceData>();
		final String query="select * from EDB_ANCMNTS ";
		
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				ReferenceData refData=new ReferenceData();
				refData.setId(""+rs.getInt("ANCMNT_ID"));
				refData.setLabel(rs.getString("ANCMNT_SUB"));
				subjects.add(refData);
			}
			log.debug("No. of available subjects:{}", subjects.size());
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_ANCMNTS table -", e);
		}
		
		return subjects;
		
	}

	public SurveySystem getAnnouncement(Integer announcementId) {
		final SurveySystem surveySystem=new SurveySystem();
		String query="select * from EDB_ANCMNTS WHERE ANCMNT_ID="+announcementId;
		log.debug("Announcements by Id query :{}",query);
		
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				surveySystem.setAnnouncementHTMLData(rs.getString("ANCMNT_DESC"));
				surveySystem.setAnnouncementCreateDate(rs.getString("ANCMNT_CRT_DT"));
				surveySystem.setAnnouncementPublished(rs.getBoolean("ANCMNT_PUB"));
				surveySystem.setSetTime(rs.getBoolean("ANCMNT_STTM"));
				surveySystem.setQuizStartDateTime(rs.getString("QUIZ_ST_DTM"));
				surveySystem.setStopWatch(rs.getInt("STP_WCH"));
			}
			log.debug("Announcement details:{}|{}|{}|{}|{}|{}|{}",new Object[]{announcementId,surveySystem.getAnnouncementHTMLData(),surveySystem.getAnnouncementCreateDate(),surveySystem.isAnnouncementPublished(),surveySystem.isSetTime(),surveySystem.getQuizStartDateTime(),surveySystem.getStopWatch()});
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_ANCMNTS table -", e);
		}
		return surveySystem;
		
	}
	
	public SurveySystem getPublishedAnnouncement() {
		SurveySystem surveySystem=null;
		String query="select * from EDB_ANCMNTS WHERE ANCMNT_PUB=true";
		log.debug("Announcements query :{}",query);
		
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				surveySystem=new SurveySystem();
				surveySystem.setAnnouncementSubjectId(rs.getInt("ANCMNT_ID"));
				surveySystem.setAnnouncementHTMLData(rs.getString("ANCMNT_DESC"));
				surveySystem.setAnnouncementCreateDate(rs.getString("ANCMNT_CRT_DT"));
				surveySystem.setAnnouncementPublished(rs.getBoolean("ANCMNT_PUB"));
				surveySystem.setSetTime(rs.getBoolean("ANCMNT_STTM"));
				surveySystem.setQuizStartDateTime(rs.getString("QUIZ_ST_DTM"));
				surveySystem.setStopWatch(rs.getInt("STP_WCH"));
			}
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_ANCMNTS table -", e);
		}
		return surveySystem;
		
	}
	


	public List<SurveyQuestionnaire> getQuestionnaire(Integer announcementId) {
		
		final String query="SELECT Q.*, O.* FROM EDB_QSTNER Q INNER JOIN EDB_QSTNER_OPTN O ON Q.QSTNER_ID = O.QSTNER_ID WHERE Q.ANCMNT_ID="+announcementId;
		
		log.debug(query);
		
		Map<Integer,SurveyQuestionnaire> questionMap=new HashMap<Integer, SurveyQuestionnaire>();
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				final Integer questionId=rs.getInt("QSTNER_ID");
				if(questionMap.containsKey(questionId)){
					final SurveyQuestionnaire question=questionMap.get(questionId);
					mapSurveyQuestionnaireOptions(rs,question);
				} else {
					SurveyQuestionnaire question=new SurveyQuestionnaire();
					question.setQuestionId(questionId);
					question.setAnnouncementId(announcementId);
					question.setQuestionDescription(rs.getString("QSTNER_QUEST"));
					question.setQuestionType(rs.getString("QSTNER_TYP"));
					question.setCorrectAnswer(rs.getString("QSTNER_ANS"));
					if(question.getQuestionOptions()==null){
						question.setQuestionOptions(new ArrayList<SurveyQuestionnaireOptions>());
					}
					mapSurveyQuestionnaireOptions(rs,question);
					questionMap.put(question.getQuestionId(), question);
				}
			}
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_QSTNER and EDB_QSTNER_OPTN tables -", e);
		}
		return new ArrayList<SurveyQuestionnaire>(questionMap.values());
		
	}
	
	public boolean isQuizAttempted(int announcementId,String empId){
		
		final String query="SELECT count(*) as ATTEMPT FROM EDB_SURVY_RSLTS WHERE ANCMNT_ID="+announcementId+" AND EMP_ID="+empId;
		boolean isQuizAttempted=false;
		log.debug(query);
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getInt("ATTEMPT")>0){
					isQuizAttempted=true;
				}
			}
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_QSTNER and EDB_QSTNER_OPTN tables -", e);
		}
		return isQuizAttempted;
		
	}
	
	private void mapSurveyQuestionnaireOptions(ResultSet rs,SurveyQuestionnaire question) throws SQLException{
		SurveyQuestionnaireOptions option=new SurveyQuestionnaireOptions();
		option.setOptionValue(rs.getInt("OPTN_VALUE"));
		option.setOptionDescription(rs.getString("OPTN_DESC"));
		question.getQuestionOptions().add(option);
	}

	public void saveSurveyResponse(SurveyQuestionnaire question, EDBUser user) {
	    try {
			final String survyEvlTable = "insert into EDB_SURVY_EVLUTN(EMP_ID,EMP_NM,ANCMNT_ID,QSTNER_ID,EMP_RES) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(survyEvlTable);
			preparedStatement.setString(1, user.getEmployeeId());
			preparedStatement.setString(2, user.getEnterpriseId());
			preparedStatement.setInt(3, question.getAnnouncementId());
			preparedStatement.setInt(4, question.getQuestionId());
			preparedStatement.setInt(5, question.getIsAnswerCorrect());
			preparedStatement.executeUpdate();
			preparedStatement.close();

	    }
	    catch(Exception e){
	    	log.error("Error Inserting into EDB_SURVY_EVLUTN table -", e);
	    }
		
	}
	
	public void saveSurveyResults(int announcementId ,int score,long timeTaken, EDBUser user) {
	    try {
			
			final String survyResltTable = "insert into EDB_SURVY_RSLTS(EMP_ID,EMP_NM,ANCMNT_ID,SCORE,TM_TKN) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = getConnection().prepareStatement(survyResltTable);
			preparedStatement.setString(1, user.getEmployeeId());
			preparedStatement.setString(2, user.getEnterpriseId());
			preparedStatement.setInt(3, announcementId);
			preparedStatement.setInt(4, score);
			preparedStatement.setInt(5, (int)timeTaken);
			preparedStatement.executeUpdate();
			preparedStatement.close();
	    }
	    catch(Exception e){
	    	log.error("Error Inserting into EDB_SURVY_RSLTS table -", e);
	    }
		
	}
	
	public List<SurveyResults> getSurveyResults(String announcementId){
		List<SurveyResults> results=new LinkedList<SurveyResults>();
		final String query="SELECT * FROM EDB_SURVY_RSLTS WHERE ANCMNT_ID="+announcementId;
		log.debug(query);
		try {
			Statement stmt=getConnection().createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				SurveyResults result=new SurveyResults();
				result.setEmpName(rs.getString("EMP_NM"));
				result.setScore(rs.getInt("SCORE"));
				result.setTimeTaken(rs.getInt("TM_TKN"));
				results.add(result);
			}
			
		} catch (Exception e) {
			log.error("Error fetching rows from EDB_QSTNER and EDB_QSTNER_OPTN tables -", e);
		}
		return results;
	}
}