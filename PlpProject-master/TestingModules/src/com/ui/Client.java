package com.ui;

import java.io.BufferedReader;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import com.bean.CreateAccountBean;
import com.bean.AgentDetails;
import com.bean.QuestionBean;

import com.bean.AgentViewPolicyBean;
import com.bean.Business;
import com.bean.CustomerDetails;
import com.bean.LoginBean;
import com.bean.NewPolicyBean;
import com.bean.PolicyDetails;
import com.bean.ProfileCreation;
import com.bean.PolicyCreationBean;
import com.exception.InsuranceQuoteGenerationException;
import com.service.InsuranceQuoteGenerationService;
import com.service.InsuranceQuoteGenerationServiceIMPL;


public class Client {

	static InsuranceQuoteGenerationService insuranceQuoteGenerationService=null;
	
	static QuestionBean questionBean=new QuestionBean();
	static PolicyCreationBean policyCreationBean=new PolicyCreationBean();
	static Scanner scanner=new Scanner(System.in);
	
	public static void main(String[] args) {
	
		LoginBean loginBean=new LoginBean();
		CreateAccountBean createAccountBean = null;
		System.out.println("Welcome to Insurance Quote Generation Application");
		System.out.println("___________________________________________________");
		System.out.println("Enter the Username:");
		String username=scanner.next();
		loginBean.setUserName(username);
		System.out.println("Enter the Password");
		loginBean.setPassword(scanner.next());
		String role="";
		insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
				try {
					
					role=insuranceQuoteGenerationService.validateUser(loginBean);
				} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
					// TODO Auto-generated catch block
					System.out.println(insuranceQuoteGenerationException);
				} catch (SQLException e3) {
					
					e3.printStackTrace();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	
		if(role.equals("Admin") || role.equals("admin"))
		{
			while(true)
			{
				System.out.println("______________________");
				System.out.println("Welcome Underwriter");
				System.out.println("_______________________");
				System.out.println("1. New Profile Creation");
				System.out.println("2. Account Creation");
				System.out.println("3. Policy Creation");
				System.out.println("4. View Policy");
				System.out.println("5. Report Generation");
				System.out.println("6. Exit");
				System.out.println("Enter your choice");
				int adminChoice = scanner.nextInt();
				switch (adminChoice) {
				case 1:
					System.out.println("___________________________________");
					System.out.println("Enter Details for Creating profile");
					System.out.println("____________________________________");
					ProfileCreation profileCreation = null;

					while (profileCreation == null) {
						try {
							profileCreation = populateProfileCreation();
						} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
							System.out.println(insuranceQuoteGenerationException);
						}

					}

					try {

						insuranceQuoteGenerationService = new InsuranceQuoteGenerationServiceIMPL();
						insuranceQuoteGenerationService.addProfile(profileCreation);
						System.out.println("___________________________________________");
						System.out.println("Profile created successfully completed!!");
						System.out.println("___________________________________________");

					} catch (Exception e) {

						e.printStackTrace();
					}
					break;
				case 2:
					
						
					try {
						createAccountBean=populateAgentBean(username);
					} catch (InsuranceQuoteGenerationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
								
							
					break;
				
				case 3:
					System.out.println("Welcome Admin");
					System.out.println("New Scheme Creation Page");
					NewPolicyBean newPolicySchemeBean=new NewPolicyBean();
						while(newPolicySchemeBean==null) {
						newPolicySchemeBean=policyCreation();
						}
					insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
					insuranceQuoteGenerationService.createNewScheme(newPolicySchemeBean);
					System.out.println("New Policy Scheme Created Successfully");

					break;
				
				case 4:
					System.out.println("In view Policy");
					System.out.println("1.Check Your Customers");
					System.out.println("2. Check agent details");
					System.out.println("3. Check customer details");

						int option = 0;
						option = scanner.nextInt();

						switch (option) {
						case 1:
							List<AgentViewPolicyBean> policyDetails=new ArrayList<>();
							insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
							try {
								policyDetails=insuranceQuoteGenerationService.getPolicyDetails(username);
								for(AgentViewPolicyBean agentViewPolicyBean:policyDetails) {
									System.out.println(agentViewPolicyBean);
								}
								
							} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
								// TODO Auto-generated catch block
								System.out.println(insuranceQuoteGenerationException);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
                                    break;
						case 2:

							insuranceQuoteGenerationService = new InsuranceQuoteGenerationServiceIMPL();

							List<AgentDetails> arrayList = new ArrayList<>();
							try {
								arrayList = insuranceQuoteGenerationService.viewPolicy();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							int number = 0;

							for (AgentDetails agentDetails : arrayList) {

								number++;
								System.out.println(number + " " + agentDetails);
							}
							AgentDetails agent1 = new AgentDetails();
							System.out.println("enter the choice");
							int value = scanner.nextInt();
							List<CustomerDetails> customerArray = new ArrayList<>();
							for (int i = 0; i < arrayList.size(); i++) {
								if (i == (value - 1)) {
									System.out.println(arrayList.get(i));
									
									agent1 = arrayList.get(i);
									System.out.println(agent1.getAgentName());
									try {
										customerArray = insuranceQuoteGenerationService.viewCustomers();
										System.out.println(customerArray);
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}
						
							break;
						case 3:
							insuranceQuoteGenerationService = new InsuranceQuoteGenerationServiceIMPL();

							
							List<CustomerDetails> arrayList1 = new ArrayList<CustomerDetails>();
							try {
								arrayList1 = insuranceQuoteGenerationService.customerDetails();
								for (CustomerDetails customerdetails : arrayList1) {

									System.out.println(" " + customerdetails);
								}
							} catch (SQLException e) {
								
							} catch (IOException e) {
								
							}
                                    
							
						}
					break;
				case 5:
					System.out.println("Report Generation");
					
					break;
				case 6:
					System.exit(0);
					break;
				default:
					System.out.println("Enter correct Choice!!");
				}
			}
		}
		else if(role.equals("Agent") || role.equals("agent"))
		{
			while(true)
			{
				System.out.println("______________________");
				System.out.println("Welcome Agent");
				System.out.println("______________________");
				System.out.println("1. Account Creation.");
				System.out.println("2. Policy Creation.");
				System.out.println("3. View policy.");
				System.out.println("4. Exit.");
				System.out.println("Enter your choice:");
				try
				{
					int agentChoice=scanner.nextInt();
					switch(agentChoice)
					{
					case 1:
						while(createAccountBean==null)
						{
							try {
								try {
							
									createAccountBean=populateAgentBean(username);
								
									insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
									insuranceQuoteGenerationService.accountCreation(createAccountBean);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
								}
							} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
								// TODO Auto-generated catch block
								System.out.println(insuranceQuoteGenerationException);
							}
						}
						
						break;
					case 2:
						System.out.println("Welcome Agent");
						System.out.println("New Scheme Creation Page");
						NewPolicyBean newPolicySchemeBean=new NewPolicyBean();
							while(newPolicySchemeBean==null) {
							newPolicySchemeBean=policyCreation();
							}
						insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
						insuranceQuoteGenerationService.createNewScheme(newPolicySchemeBean);
						System.out.println("New Policy Scheme Created Successfully");

					
						break;
					case 3:
						 
						List<AgentViewPolicyBean> policyDetails=new ArrayList<>();
						insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
						try {
							policyDetails=insuranceQuoteGenerationService.getPolicyDetails(username);
							for(AgentViewPolicyBean agentViewPolicyBean:policyDetails) {
								System.out.println(agentViewPolicyBean);
							}
							
						} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
							// TODO Auto-generated catch block
							System.out.println(insuranceQuoteGenerationException);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
	

						break;
					case 4:
						System.out.println("Have a good Day!!");
						System.exit(0);
						break;
						default:
							System.out.println("____________________________________");
							System.out.println("You have entered a wrong choice!!");
							System.out.println("Try Again!!");
							System.out.println("_____________________________________");
							break;
					}
					
					}
					catch(InputMismatchException e)
					{
						System.out.println("___________________________________________");
						System.out.println("Please enter a numeric value, Try Again!!");
						System.out.println("____________________________________________");
						break;
					}
				}
		}
		else if(role.equals("User") || role.equals("user"))
		{
			System.out.println("User Choice");
			System.out.println("1.Account Creation");
			System.out.println("2.View Policy");
			System.out.println("Enter the chocie ");
			int choice=scanner.nextInt();
			switch(choice) {
			case 1:
				try {
					populateAgentBean(username);
				} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
					// TODO Auto-generated catch block
					System.out.println(insuranceQuoteGenerationException);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				break;
			case 2:
				List<AgentViewPolicyBean> policyDetails=new ArrayList<>();
				insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
				try {
					policyDetails=insuranceQuoteGenerationService.getPolicyDetails(username);
					for(AgentViewPolicyBean agentViewPolicyBean:policyDetails) {
						System.out.println(agentViewPolicyBean);
					}
					
				} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
					// TODO Auto-generated catch block
					System.out.println(insuranceQuoteGenerationException);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}

				
				
				break;
			}
			
		}
		else
		{
			System.out.println("________________________________________________");
			System.out.println("You have entered wrong username and password!!");
			System.out.println("_________________________________________________");
		}
		
			
		
	}
	private static ProfileCreation populateProfileCreation() throws InsuranceQuoteGenerationException {

		ProfileCreation profileCreation = new ProfileCreation();

		System.out.println("____________________________________");
		System.out.println("Create a new profile");
		System.out.println("_____________________________________");
		System.out.println("Create username: ");
		String validName=scanner.next();
		profileCreation.setUserName(validName);
       insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
       try {
		boolean validation=insuranceQuoteGenerationService.findAgentName(validName);
		if(validation) {
		System.out.println("Create password: ");
		profileCreation.setPassword(scanner.next());
	
		System.out.println("Enter RoleCode: ");
		profileCreation.setRoleCode(scanner.next());
		}
		else {
			System.out.println("entered user is already exist");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		
		return profileCreation;
	}
	
	private static CreateAccountBean populateAgentBean(String username) throws InsuranceQuoteGenerationException, SQLException, IOException {
		
		insuranceQuoteGenerationService =new InsuranceQuoteGenerationServiceIMPL();
		CreateAccountBean createAccountBean=new CreateAccountBean();
		Business business=new Business();
		
		System.out.println("Choose Your Business Segment:");
		System.out.println("1. Business Auto.");
		System.out.println("2. Restaurant.");
		System.out.println("3. Apartment.");
		System.out.println("4. General Merchant.");
		System.out.println("Enter your choice:");
		int businessSegmentChoice=scanner.nextInt();            
			List<QuestionBean> al = new ArrayList<>();
			switch (businessSegmentChoice) {
			case 1:
				business.setBusiness_Segment("Business Auto");
				al = insuranceQuoteGenerationService.createPolicy("Business_auto");
				
				
				 getDetails(al, username );
				break;
			case 2:business.setBusiness_Segment("Restaurant");
				al = insuranceQuoteGenerationService.createPolicy("Restaurant");
				getDetails(al, username);
				break;
			case 3:
				business.setBusiness_Segment("Apartment");
				al = insuranceQuoteGenerationService.createPolicy("Apartment");
				getDetails(al, username);
				break;
			case 4:
				business.setBusiness_Segment("General Merchant");
				al = insuranceQuoteGenerationService.createPolicy("general_merchant");
				getDetails(al, username);
				break;
			default:
				System.out.println("Please enter correct number");
				
				
			}
			return createAccountBean;
			
		
		
	}
	
	public static void  getDetails(List<QuestionBean> al, String username) throws IOException, InsuranceQuoteGenerationException, SQLException {
		Business businessVal=new Business();
		insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
		businessVal= insuranceQuoteGenerationService.CheckAccount(username,al.get(1).getBusinessSegment());
		if(businessVal.getBusiness_Segment().equals(al.get(1).getBusinessSegment())) {
			System.out.println("You have already taken policy on the particular business Segemnt");
			populateAgentBean(username);
		}
		else {
		PolicyCreationBean agentBean=new PolicyCreationBean();
		
		int premiumCal=0;
		for (QuestionBean questionBean : al) {
			System.out.println(questionBean.getQuestion());
			System.out.println(
					"1." + questionBean.getAnswer1() + "\t" + "2." + questionBean.getAnswer2() + "\t" + "3." + questionBean.getAnswer3());
			System.out.println("enter the option");
			int option = scanner.nextInt();
			
			switch (option) {
			case 1:
				agentBean.setAnswer(questionBean.getAnswer1());
				agentBean.setWeightage(questionBean.getWeightage1());
				 premiumCal += questionBean.getWeightage1();

				break;
			case 2:
				agentBean.setAnswer(questionBean.getAnswer2());
				agentBean.setWeightage(questionBean.getWeightage2());
				 premiumCal += questionBean.getWeightage2();
				break;
			case 3:
				agentBean.setAnswer(questionBean.getAnswer3());
				agentBean.setWeightage(questionBean.getWeightage3());
				 premiumCal += questionBean.getWeightage3();

			}
			agentBean.setQuestion(questionBean.getQuestion());
			agentBean.setBusinessSegment(questionBean.getBusinessSegment());
		
			agentBean.setUsername(username);
	

		}
		//al.get(1).getBusinessSegment();
		System.out.println("Your premium is:"+premiumCal);
        System.out.println("choose option to continue if premium is okay ");
        System.out.println("1.To continue");
        System.out.println("0.Return Back");
        int chocie=scanner.nextInt();
        switch(chocie) {
        case 1:
            if(businessVal.getUsername().equals(username)) {
            	insuranceQuoteGenerationService.policyCreation(agentBean);
            }
            else {
        	CreateAccountBean agentBea=new CreateAccountBean();
        	System.out.println("Enter the insured Name");
        	String insuredName = scanner.next();
        	agentBea.setInsuredName(insuredName);
        	System.out.println("Enter the insured street");
        	String insuredStreet=scanner.next();
        	agentBea.setInsuredStreet(insuredStreet);
        	System.out.println("Enter the insured city");
        	String insuredCity = scanner.next();
        	agentBea.setInsuredCity(insuredCity);
        	System.out.println("Enter the insured state");
        	String insuredState=scanner.next();
        	agentBea.setInsuredState(insuredState);
        	System.out.println("Enter the insured Zip");
        	Long insuredZip=scanner.nextLong();
        	agentBea.setInsuredZip(insuredZip);
        	System.out.println("Enter the Business Segment");
        	agentBea.setBusinessSegment(al.get(1).getBusinessSegment());
        	
        	System.out.println("Enter the Account number");
        	Long accountNumber=scanner.nextLong();
        	agentBea.setAccountNumber(accountNumber);
        	insuranceQuoteGenerationService=new InsuranceQuoteGenerationServiceIMPL();
        	String roleCode=insuranceQuoteGenerationService.checkUser(username);
        	
        	if (roleCode.equals("agent")||roleCode.equals("admin")) {
        		System.out.println(" Enter the unique username ");
        		String name=scanner.next();
        	      agentBea.setUsername(name);
        		  agentBea.setAgentName(username);
        		
        	} else if(roleCode.equals("insured")) {
        		
        		System.out.println(" enter the unique agentname/adminname is" );
        		String agentName=scanner.next();
        		agentBea.setAgentName(agentName);
        	    agentBea.setUsername(username);
        	}

    		
    		PolicyDetails policyDetails=new PolicyDetails();
    		
    		try {
    			insuranceQuoteGenerationService.accountCreation(agentBea);
				policyDetails.setAccountNumber(accountNumber);
				policyDetails.setPremium(premiumCal);
				policyDetails.setPolicyNumber(generatePolicy());
				Long number=insuranceQuoteGenerationService.policy_Details(policyDetails);
				System.out.println("Your Policy Number is"+number);
				insuranceQuoteGenerationService.policyCreation(agentBean);
			} catch (InsuranceQuoteGenerationException e1) {
				
				e1.printStackTrace();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
       
            }
    		break;
        case 2:
			try {
				populateAgentBean(username);
			} catch (InsuranceQuoteGenerationException insuranceQuoteGenerationException) {
				// TODO Auto-generated catch block
				System.out.println(insuranceQuoteGenerationException);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
       	 break;
       	 
        }
		}
		

	}
	public static Long generatePolicy() throws IOException {
		FileReader fr = new FileReader("Policy_Number.txt");
		BufferedReader br = new BufferedReader(fr);
		int rd = Integer.parseInt(br.readLine());
		int inc = rd + 1;
		FileOutputStream fw = new FileOutputStream("Policy_Number.txt");
		fw.write(Integer.toString(inc).getBytes());
		fw.close();
		String date = "" + java.time.LocalDate.now();
		String yy = date.substring(2, 4);
		String mm = date.substring(5, 7);
		FileReader fr1 = new FileReader("Policy_Number.txt");
		BufferedReader br1 = new BufferedReader(fr1);
		int b = Integer.parseInt(br1.readLine());

		String s1 = null;
 
		if (b < 10)
			s1 = "000" + b;
		if (b > 10 && b < 100)
			s1 = "00" + b;
		if (b >= 100)
			s1 = "0" + b;  
		String id = yy + mm + s1;
		br1.close();
		fr1.close();
		br.close();
		fr.close();
        return Long.parseLong(id);
		
	}
	public static NewPolicyBean policyCreation() {
		NewPolicyBean newPolicySchemeBean=new NewPolicyBean();
		System.out.println("Enter New Policy Business Segment ID");
		newPolicySchemeBean.setBus_seg_id(scanner.nextInt());
		System.out.println("Enter New Policy Business Segment Name");
		newPolicySchemeBean.setBus_seg_name(scanner.next());
		System.out.println("Enter New Policy Question ID");
		newPolicySchemeBean.setPol_ques_id(scanner.nextInt());
		System.out.println("Enter New Question ");
		newPolicySchemeBean.setPol_ques_desc(scanner.next());
		System.out.println("Enter Answer-1 For Entered Question:");
		newPolicySchemeBean.setPol_ques_ans1(scanner.next());
		System.out.println("Enter Weightage for Answer-1 :");
		newPolicySchemeBean.setPol_ques_ans1_weightage(scanner.nextInt());
		System.out.println("Enter Answer-2 For Entered Question:");
		newPolicySchemeBean.setPol_ques_ans2(scanner.next());
		System.out.println("Enter Weightage for Answer-2 :");
		newPolicySchemeBean.setPol_ques_ans2_weightage(scanner.nextInt());
		System.out.println("Enter Answer-3 For Entered Question:");
		newPolicySchemeBean.setPol_ques_ans3(scanner.next());
		System.out.println("Enter Weightage for Answer-3 :");
		newPolicySchemeBean.setPol_ques_ans3_weightage(scanner.nextInt());
                 return newPolicySchemeBean;
	}

}
