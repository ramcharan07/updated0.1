package com.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.dao.InsuranceQuoteGenerationDao;
import com.dao.InsuranceQuoteGenerationIMPL;
import com.exception.InsuranceQuoteGenerationException;

public class InsuranceQuoteGenerationServiceIMPL implements InsuranceQuoteGenerationService{

	InsuranceQuoteGenerationDao insuranceQuoteGenerationDao=null;;
	InsuranceQuoteGenerationIMPL insuranceQuoteGenerationIMPL=null;
	
	public String checkUser(String username) throws InsuranceQuoteGenerationException, SQLException, IOException
	{
		String role="";
		insuranceQuoteGenerationIMPL=new InsuranceQuoteGenerationIMPL();
		role=insuranceQuoteGenerationIMPL.checkUser(username);
		return role;
	}
	@Override
	public void accountCreation(CreateAccountBean agentBean) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		insuranceQuoteGenerationDao.accountCreation(agentBean);
	}

	public void validateAgent(CreateAccountBean agentBean) throws InsuranceQuoteGenerationException, SQLException, IOException
	{
		
		List<String> validationErrors=new ArrayList<>();
		if(!(isValidInsuredName(agentBean.getInsuredName())))
		{
			validationErrors.add("\nFirst letter of insured name should be capital!!");
		}
		if(!(isValidInsuredStreet(agentBean.getInsuredStreet())))
		{
			validationErrors.add("\nFirst letter of city name should be capital & should contain min three characters!!");
		}
		if(!(isValidInsuredCity(agentBean.getInsuredCity())))
		{
			validationErrors.add("\nFirst letter of city name should be capital & should contain min three characters!!");
		}
		if(!(isValidInsuredState(agentBean.getInsuredState())))
		{
			validationErrors.add("\nFirst letter of state name should be capital & should contain min three characters!!");
		}
		if(!(isValidInsuredZip(agentBean.getInsuredZip())))
		{
			validationErrors.add("\nZip code should be 6 character long & it should be numbers!!");
		}
		if(!(isValidAccountNumber(agentBean.getAccountNumber())))
		{
			validationErrors.add("\nAccount number should consists 10 digits");
		}
		if(!(isValidAgentName(agentBean.getAgentName())))
		{
			validationErrors.add("\nAgent Name does not exist!!");
		}
		if(!validationErrors.isEmpty())
		{
			try {
				throw new InsuranceQuoteGenerationException(validationErrors+"");
			} catch (InsuranceQuoteGenerationException e) {
				System.err.println(e);
			}
		}
	}

	private boolean isValidAccountNumber(long accountNumber) {
		
		Pattern accountNumberPattern=Pattern.compile("[1-9][0-9]{9}");
		Matcher accountNumberMatcher=accountNumberPattern.matcher(String.valueOf(accountNumber));
		return accountNumberMatcher.matches();
	}

	private boolean isValidAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		insuranceQuoteGenerationIMPL=new InsuranceQuoteGenerationIMPL();
		boolean find=insuranceQuoteGenerationIMPL.findAgentName(agentName);
		return find;
	}

	private boolean isValidInsuredZip(long insuredZip) {
		
		Pattern zipPattern=Pattern.compile("[1-9][0-9]{5}");
		Matcher zipMatcher=zipPattern.matcher(String.valueOf(insuredZip));
		return zipMatcher.matches();
		
	}

	private boolean isValidInsuredState(String insuredState) {
		
		Pattern statePattern=Pattern.compile("[A-Z][a-z]{3,15}");
		Matcher stateMatcher=statePattern.matcher(insuredState);
		return stateMatcher.matches();
	}

	private boolean isValidInsuredCity(String insuredCity) {
		
		Pattern cityPattern=Pattern.compile("[A-Z][a-z]{3,15}");
		Matcher cityMatcher=cityPattern.matcher(insuredCity);
		return cityMatcher.matches();
	}

	private boolean isValidInsuredStreet(String insuredStreet) {
		
		Pattern streetPattern=Pattern.compile("[a-zA-Z0-9]{3,40}");
		Matcher streetMatcher=streetPattern.matcher(insuredStreet);
		return streetMatcher.matches();
	}

	private boolean isValidInsuredName(String insuredName) {
		
		Pattern namePattern=Pattern.compile("[A-Z][a-z]{1,30}");
		Matcher nameMatcher=namePattern.matcher(insuredName);
		return nameMatcher.matches();
	}

	@Override
	public List<AgentViewPolicyBean> getPolicyDetails(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		insuranceQuoteGenerationDao= new InsuranceQuoteGenerationIMPL();
		List<AgentViewPolicyBean> policyList=new ArrayList<>();
		policyList=insuranceQuoteGenerationDao.getPolicyDetails(agentName);
		return policyList;
	}

	@Override
	public ArrayList<QuestionBean> getQuestionAnswer(QuestionBean questionBean) throws InsuranceQuoteGenerationException, IOException {
		
		ArrayList<QuestionBean> al = new ArrayList<>();
		al = insuranceQuoteGenerationDao.getQuestionAnswer(questionBean);
		return al;
	}

	@Override
	public void policyCreation(PolicyCreationBean agentPolicyCreationBean) throws SQLException, IOException, InsuranceQuoteGenerationException {

		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		insuranceQuoteGenerationDao.policyCreation(agentPolicyCreationBean);
		
	}
	@Override
	public void addProfile(ProfileCreation profileCreation) throws InsuranceQuoteGenerationException, SQLException, IOException {

		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		
		insuranceQuoteGenerationDao.addProfile(profileCreation);
		
	}
	@Override
	public List<AgentDetails> viewPolicy() throws SQLException, IOException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		List<AgentDetails> arrayValues=new ArrayList<>();
		arrayValues=insuranceQuoteGenerationDao.viewPolicy();
		return arrayValues;
	}
	@Override
	public List<CustomerDetails> viewCustomers() throws SQLException, IOException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		List<CustomerDetails> arrayOutput=new ArrayList<>();
		arrayOutput=insuranceQuoteGenerationDao.viewCustomers();
		return arrayOutput;
	}
	@Override
	public List<CustomerDetails> customerDetails() throws SQLException, IOException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		List<CustomerDetails> array=new ArrayList<>();
		array=insuranceQuoteGenerationDao.customerDetails();
		return array;
	}
	@Override
	public List<QuestionBean> createPolicy(String segment) {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		List<QuestionBean> list=new ArrayList<>();
		list=insuranceQuoteGenerationDao.createPolicy(segment);
	
	return list;
	}
	@Override
	public int PolicyQuestion(PolicyCreationBean policyCreationBean) {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		int premium=0;
		premium=insuranceQuoteGenerationDao.PolicyQuestion(policyCreationBean);
	return premium;
	}
	@Override
	public void createNewScheme(NewPolicyBean newPolicySchemeBean) {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		insuranceQuoteGenerationDao.createNewScheme(newPolicySchemeBean);
		
	}
	@Override
	public Business CheckAccount(String username,String business) throws InsuranceQuoteGenerationException, SQLException, IOException {
	insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
	Business businesslist=new Business();
	businesslist=insuranceQuoteGenerationDao.CheckAccount(username,business);
	return businesslist;
	}
	@Override
	public long policy_Details(PolicyDetails policyDetails) throws SQLException, IOException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		Long policy_Number=insuranceQuoteGenerationDao.policy_Details(policyDetails);
		return policy_Number;
		
	}
	@Override
	public String validateUser(LoginBean loginBean)
			throws SQLException, IOException, InsuranceQuoteGenerationException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
	String role=	insuranceQuoteGenerationDao.validateUser(loginBean);
		return role;
	}
	@Override
	public boolean findAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException {
		insuranceQuoteGenerationDao=new InsuranceQuoteGenerationIMPL();
		boolean result=insuranceQuoteGenerationDao.findAgentName(agentName);
		return result;
	}

	
}
