alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN NAME varchar(20);

alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN ID integer;

alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN STARTDATE date;

alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN PROJ_REV_ET_DT date nullable;

select * from ENGAGEMENT.EDB_PROJECT;

alter table ENGAGEMENT.EDB_PROJECT 
drop COLUMN ENDDATE;

PROJ_ID
CLNT_ID
PROJ_ST_DT
PROJ_ET_DT
PROJ_REV_ET_DT
PROJ_DESC

private String id;
	private String projectName;
	private String projectLead;
	private DateTime edate;
	private DateTime sdate;
	private List<String> phases;
	private String projectDescription;
	private List<String> resources;
	public String getId() {

	
alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN ID int;

alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN PROJ_NAME varchar(20);

alter table ENGAGEMENT.EDB_PROJECT 
add COLUMN PROJ_LEAD varchar(20);

insert into ENGAGEMENT.CLNT_ENGMNT (CLNT_ID, CLNT_NM, CLNT_TYP, CLNT_ST_DT)
VALUES (1, 'HCSC', 'HEALTH CARE', '2014-01-01');
select * from ENGAGEMENT.EDB_PROJECT;

insert into ENGAGEMENT.EDB_PROJECT (CLNT_ID, PROJ_DESC, PROJ_ID, PROJ_ST_DT, PROJ_ET_DT)
VALUES (1, 'SCALABILITY', 2, '2014-01-01', '2014-02-01');