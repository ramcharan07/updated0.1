package com.dao;

import java.io.IOException;
import java.sql.SQLException;
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

public interface InsuranceQuoteGenerationDao {

	public void accountCreation(CreateAccountBean createAccountBean) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public List<AgentViewPolicyBean> getPolicyDetails(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException ;
	public ArrayList<QuestionBean> getQuestionAnswer(QuestionBean questionBean) throws InsuranceQuoteGenerationException, IOException;
	public void policyCreation(PolicyCreationBean PolicyCreationBean) throws SQLException, IOException, InsuranceQuoteGenerationException;
	public void  addProfile(ProfileCreation profileCreation) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public List<AgentDetails> viewPolicy() throws SQLException, IOException;
	public List<CustomerDetails> viewCustomers() throws SQLException, IOException;
	public List<CustomerDetails> customerDetails() throws SQLException, IOException;
	public List<QuestionBean> createPolicy(String segment);
	public int PolicyQuestion(PolicyCreationBean policyCreationBean);
	public void createNewScheme(NewPolicyBean newPolicySchemeBean);
	public Business CheckAccount(String username, String business) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public Long policy_Details(PolicyDetails policyDetails) throws SQLException, IOException;
	public String checkUser(String username) throws InsuranceQuoteGenerationException, SQLException, IOException;
	public String validateUser(LoginBean loginBean) throws SQLException, IOException, InsuranceQuoteGenerationException;
	public boolean findAgentName(String agentName) throws InsuranceQuoteGenerationException, SQLException, IOException;

}
