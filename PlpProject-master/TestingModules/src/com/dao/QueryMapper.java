package com.dao;

public interface QueryMapper {
	
	public String INSERT_QUERY = "INSERT INTO agent_create_account VALUES(?,?,?,?,?,?,?,?,?)";
	public String FIND_AGENT_NAME = "Select username from profiledetails where username=?";
	public String VIEW_POLICY_DETAILS_QUERY = "Select insuredName,policyNumber,PremimumAmount,accountNumber from viewPolicy where agentName=?";
	public String SEARCH_POLICY = "Select question,answer1,answer2,answer3 from question_bank where business_Segment=?";
	public String CREATE_POLICY = "Insert into policy_creation values(?,?,?,?,?)";
	public String FIND_ROLE = "Select role from profiledetails where username=?";
	public String CREATE_PROFILE = "Insert into profileDetails values(?,?,?)";
    public String VALIDATE_USER="Select role from profiledetails where username=? and password=?";
}
