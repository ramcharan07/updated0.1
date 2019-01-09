package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.bean.CreateAccountBean;
import com.bean.AgentDetails;
import com.bean.PolicyCreationBean;
import com.bean.QuestionBean;
import com.bean.AgentViewPolicyBean;
import com.bean.Business;
import com.bean.CustomerDetails;

import com.bean.LoginBean;
import com.bean.NewPolicyBean;
import com.bean.PolicyDetails;
import com.bean.ProfileCreation;
import com.exception.InsuranceQuoteGenerationException;
import com.util.DBConnection;

public class InsuranceQuoteGenerationIMPL implements InsuranceQuoteGenerationDao {

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultset = null;
	CreateAccountBean agentBean = null;

	// Agent account creation

	@Override
	public void accountCreation(CreateAccountBean createAccountBean) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.INSERT_QUERY);
			preparedStatement.setString(1, createAccountBean.getAgentName());
			preparedStatement.setString(2, createAccountBean.getUsername());
			preparedStatement.setString(3, createAccountBean.getInsuredName());
			preparedStatement.setString(4, createAccountBean.getInsuredStreet());
			preparedStatement.setString(5, createAccountBean.getInsuredCity());
			preparedStatement.setString(6, createAccountBean.getInsuredState());
			preparedStatement.setLong(7, createAccountBean.getInsuredZip());
			preparedStatement.setLong(8, createAccountBean.getAccountNumber());
			preparedStatement.setString(9, createAccountBean.getBusinessSegment());
			preparedStatement.executeQuery();

		} catch (SQLException e) {
			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}

	}
	
	public Business CheckAccount(String username,String business) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		ResultSet rs=null;
		Business busines=new Business();
		try {
			preparedStatement = connection.prepareStatement("select businesssegment,accountnumber,username from account where username=? and businesssegemnt=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, business);
			rs=preparedStatement.executeQuery();
			
			while(rs.next()) {
			
			busines.setBusiness_Segment(rs.getString(1));
				busines.setAccountNumber(rs.getLong(2));
				busines.setUsername(rs.getString(3));
			}

		} catch (SQLException e) {
			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}
		return busines;

	}
	
	public boolean findAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {
		connection = DBConnection.getConnection();
		String validation;
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.FIND_AGENT_NAME);
			preparedStatement.setString(1, agentName);
			resultset = preparedStatement.executeQuery();
			resultset.next();
			validation = resultset.getString(1);
			if (agentName.equals(validation) ) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Tehnical problem occured. Refer log");
		}

	}

	@Override
	public List<AgentViewPolicyBean> getPolicyDetails(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {

		connection = DBConnection.getConnection();
		AgentViewPolicyBean agentViewPolicyBean = null;
		List<AgentViewPolicyBean> policyList=new ArrayList<>();

						preparedStatement = connection.prepareStatement(QueryMapper.VIEW_POLICY_DETAILS_QUERY);
			preparedStatement.setString(1, agentName);
			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				agentViewPolicyBean = new AgentViewPolicyBean();
				agentViewPolicyBean.setInsuredName(resultset.getString(1));
				agentViewPolicyBean.setPolicyNumber(resultset.getLong(2));
				agentViewPolicyBean.setPolicyPremium(resultset.getDouble(3));
				agentViewPolicyBean.setAccountNumber(resultset.getLong(4));
                     policyList.add(agentViewPolicyBean);
			}

	return policyList;

	}

	@Override
	public ArrayList<QuestionBean> getQuestionAnswer(QuestionBean questionBean) throws InsuranceQuoteGenerationException, IOException {

		PolicyCreationBean agentPolicyCreationBean = new PolicyCreationBean();
		ArrayList<QuestionBean> al = new ArrayList<>();
		try {
			connection = DBConnection.getConnection();
			preparedStatement = connection.prepareStatement(QueryMapper.SEARCH_POLICY);
			preparedStatement.setString(1, questionBean.getBusinessSegment());
			preparedStatement.executeQuery();

			while (resultset.next()) {

				questionBean.setQuestion(resultset.getString(1));
				questionBean.setAnswer1(resultset.getString(2));
				questionBean.setAnswer2(resultset.getString(3));
				questionBean.setAnswer3(resultset.getString(4));

			}
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in Policy Creation");

		}

		return al;
	}

	@Override
	public void policyCreation(PolicyCreationBean policyCreationBean)
			throws SQLException, IOException, InsuranceQuoteGenerationException {

		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.CREATE_POLICY);
			preparedStatement.setString(1, policyCreationBean.getBusinessSegment());
			preparedStatement.setString(2, policyCreationBean.getQuestion());
			preparedStatement.setString(3, policyCreationBean.getAnswer());
			preparedStatement.setInt(4, policyCreationBean.getWeightage());
			preparedStatement.setString(5, policyCreationBean.getUsername());
			preparedStatement.executeQuery();

		} catch (SQLException e) {
			throw new InsuranceQuoteGenerationException("Error in policy creation");
		}
	}

	public String checkUser(String username) throws InsuranceQuoteGenerationException, SQLException, IOException {

		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.FIND_ROLE);
			preparedStatement.setString(1, username);
			resultset = preparedStatement.executeQuery();
			String role="";
			if(resultset.next())
			 role = resultset.getString(1);
			return role;
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in check user");
		}
	}

	@Override
	public void addProfile(ProfileCreation profileCreation) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		connection = DBConnection.getConnection();	
		try
		{		
			preparedStatement=connection.prepareStatement(QueryMapper.CREATE_PROFILE);

			preparedStatement.setString(1,profileCreation.getUserName());			
			preparedStatement.setString(2,profileCreation.getPassword());
			preparedStatement.setString(3,profileCreation.getRoleCode());
			preparedStatement.executeUpdate();
		}
		catch(SQLException sqlException)
		{
			throw new InsuranceQuoteGenerationException("Error in policy creation");
				
		}
	}

	@Override
	public List<AgentDetails> viewPolicy() throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		int donorCount = 0;
		
		PreparedStatement ps=null;
		ResultSet resultset = null;
		
		List<AgentDetails> al=new ArrayList<AgentDetails>();
		try
		{
			ps=con.prepareStatement("select * from agent");
			resultset=ps.executeQuery();
			
			while(resultset.next())
			{	
				AgentDetails agent=new AgentDetails();
				//agent.setAgentId(resultset.getString(1));
				agent.setAgentName(resultset.getString(1));
				agent.setNoOfCustomers(resultset.getInt(2));
				al.add(agent);
				
				
			
			}			
			
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return al;

	}

	@Override
	public List<CustomerDetails> viewCustomers() throws SQLException, IOException {
		// TODO Auto-generated method stub
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		
		List<CustomerDetails> arrayList=new ArrayList<>();
		try
		{
			ps=con.prepareStatement("select c.insuredname,c.agentname,c.businesssegment from agent_create_account c,agent a where c.agentname=a.agentname and a.agentname=?");
		
			//ps.setString(1,agentId); 
				 

			resultset=ps.executeQuery();
			
			while(resultset.next())
			{	
				CustomerDetails customer=new CustomerDetails();
				customer.setInsuredName(resultset.getString(1));
				customer.setAgentName(resultset.getString(2));
				customer.setBusinessSegment(resultset.getString(3));
				arrayList.add(customer);
			}		
			
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return arrayList;
	}

	@Override
	public List<CustomerDetails> customerDetails() throws SQLException, IOException {
		// TODO Auto-generated method stub
		Connection con=DBConnection.getConnection();
		//PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		List<CustomerDetails> a3=new ArrayList<CustomerDetails>();
		try
		{
			st=con.createStatement();
			resultset=st.executeQuery("select agentname,insuredname,accountnumber,businesssegment,policynumber,premiumamount from agent_create_account");
		
			
			
			while(resultset.next())
			{	
				CustomerDetails customerdetails=new CustomerDetails();
				
				
				
				customerdetails.setAgentName(resultset.getString(1));
				
				customerdetails.setInsuredName(resultset.getString(2));
				customerdetails.setAccountNumber(resultset.getLong(3));
				customerdetails.setBusinessSegment(resultset.getString(4));
				customerdetails.setPolicyNumber(resultset.getLong(5));
				customerdetails.setPremiumAmount(resultset.getLong(6));
				
				
				a3.add(customerdetails);
				
			}		
			
		} catch (SQLException e) {
		System.out.println("enter");
		}
		return a3;
	}
	public List<QuestionBean> createPolicy(String businessSegment) {
		ArrayList<QuestionBean> al = new ArrayList<>();
		try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet resultset = null;
			ps=con.prepareStatement("select * from policycreation where business_segment=?");
			ps.setString(1, businessSegment);
		    resultset=ps.executeQuery();
			
			
			while(resultset.next()) {
			QuestionBean policyBean=new QuestionBean();
				policyBean.setBusinessSegment(resultset.getString(1));
				policyBean.setQuestion(resultset.getString(2));
				policyBean.setAnswer1(resultset.getString(3));
				policyBean.setWeightage1(resultset.getInt(4));
				policyBean.setAnswer2(resultset.getString(5));
				policyBean.setWeightage2(resultset.getInt(6));
				policyBean.setAnswer3(resultset.getString(7));
				policyBean.setWeightage3(resultset.getInt(8));
				al.add(policyBean);
			}
			
		}
		catch(Exception e) {
			System.out.println("here");
		}
		
		return al;
		}

	@Override
	public int PolicyQuestion(PolicyCreationBean questionBean) {
		int premium=0;
		PreparedStatement ps1=null;
		try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet resultset = null;
			Statement st=null;
			  ps=con.prepareStatement("insert into QA values (?,?,?,?,?)");
			  
			 ps.setString(1, questionBean.getQuestion());
			 ps.setString(2, questionBean.getAnswer());
			 ps.setInt(3, questionBean.getWeightage());
			 ps.setString(4,questionBean.getBusinessSegment());
			 ps.setString(5, questionBean.getUsername());
			 int i=ps.executeUpdate();
           st=con.createStatement();
           ps1=con.prepareStatement("select sum(weightage) from qa where businesssegment=? and username=?");
           ps1.setString(1,questionBean.getBusinessSegment() );
           ps1.setString(2, questionBean.getUsername());
           resultset=ps1.executeQuery();
           if(resultset.next()) {
        	   premium=resultset.getInt(1);
        	 
           }
			
			  
		}
		catch(Exception e)
		{
			System.out.println("Exception while Insertion");
		}
		return premium;
	}

	@Override
	public void createNewScheme(NewPolicyBean newPolicySchemeBean) {
		//insert the values into the businessSegment table 
		
	}

	@Override
	public Long policy_Details(PolicyDetails policyDetails) throws SQLException, IOException {
		Connection con=DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet resultset = null;
		Statement st=null;
		long policyNumber=0;
		ps=con.prepareStatement("insert into policyDetails values(?,?,?)");
		ps.setLong(1, policyDetails.getPolicyNumber());
		ps.setInt(2, policyDetails.getPremium());
		ps.setLong(3, policyDetails.getAccountNumber());
		ps.executeUpdate();
		st=con.createStatement();
		resultset=st.executeQuery("select policynumber from policyDetails where accountnumber=' "+policyDetails.getAccountNumber()+"'");
		if(resultset.next())
			policyNumber=resultset.getLong(1);
		return policyNumber;
	}

	@Override
	public String validateUser(LoginBean loginBean) throws SQLException, IOException, InsuranceQuoteGenerationException {
		connection = DBConnection.getConnection();
		try {
			preparedStatement = connection.prepareStatement(QueryMapper.FIND_ROLE);
			preparedStatement.setString(1, loginBean.getUserName());
			preparedStatement.setString(2, loginBean.getPassword());
			resultset = preparedStatement.executeQuery();
			String role="";
			if(resultset.next())
			 role = resultset.getString(1);
			return role;
		} catch (SQLException e) {

			throw new InsuranceQuoteGenerationException("Error in check user");
		}

	}


	}

