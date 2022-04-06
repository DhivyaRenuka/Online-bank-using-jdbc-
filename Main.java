package BankMngtApp;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONObject;

public class Main {
	
// create account
	public static void  createAccount()
	{

		Scanner sc=new Scanner(System.in);
		Scanner ss=new Scanner(System.in);
		Random rnd = new Random();
		int accno = rnd. nextInt(999999);
		System.out.println("Your account number is "+accno);
		System.out.println("Enter the name of account holder");
		String name=ss.nextLine();
		if(name.isEmpty())
		{
			System.out.println("enter the valid name");
			name=ss.nextLine();
		}
		System.out.println("Enter your secret pin number (4 digit)");
		String pin =ss.nextLine();
		if(pin.isEmpty() || (Integer.parseInt(pin)>0))
		{
			System.out.println("pin is set");
		}
		else
		{
			System.out.println("reset your pin  ");
			pin =ss.nextLine();
			
			
		}
		System.out.println("Enter phone number");
		String phno= ss.nextLine();
		if(phno.isEmpty()){
			System.out.println("Enter valid phone number");
			phno= ss.nextLine();
		}
	
		
		System.out.println("Enter address ");
		String addr= ss.nextLine();
		if(addr.isEmpty())
		{
			addr="";
		}
		System.out.println("Enter amount you can deposit (min 500)");
		int bal=sc.nextInt();
		
		AccountHolder ah= new AccountHolder(accno,Integer.parseInt(pin),bal,Integer.parseInt(phno),name,addr);
		
		
		int ans =BankDao.insertAccountHolder(ah);
		if(ans>0)
		{
			System.out.println("Your account created successfully");
			System.out.println("your account number is "+accno);
			
		}
		
			
		
	}
// login
	public static void login() throws SQLException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter your account number");
		int accno= sc.nextInt();
		System.out.println("Enter your secret pin");
		int pin= sc.nextInt();
		BankDao.CheckLogin(accno,pin);
		transaction(accno,pin);
		
	}

// transfer
	public static void transaction(int accno, int pin) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to bank transaction");
		System.out.println("Enter your choice: \n 1.Deposit \n 2.Withdraw ");
		int ch = sc.nextInt();

		// deposit
		if (ch == 1) {
			System.out.println("Enter the amount to Deposit");
			int bal = sc.nextInt();

			BankDao.deposit(accno, bal);

		}
		// withdraw
		else if (ch == 2) {
			System.out.println("Enter the amount to withdraw");
			int bal = sc.nextInt();
			System.out.println("Enter whether you want cash press 0 or cridit to other account press 1");
			int opt = sc.nextInt();
			if (opt == 0) {
				BankDao.withdraw(bal, accno);
			} else {
				System.out.println("Enter account number to cridit");
				int accno1 = sc.nextInt();
				BankDao.TransferAcc(bal, accno, accno1);
			}

		} else {
			System.out.println("choose between 1 or 2");
		}

	}
//loan
	public static void loan() throws SQLException
	{
		Scanner sc= new Scanner(System.in);
		Scanner ss= new Scanner(System.in);
		System.out.println("Enter the account number :");
		int accno=ss.nextInt();
		System.out.println("Enter your secret pin");
		int pin= ss.nextInt();
		System.out.println("Type of loan required \n"
				+ "1. Education Loan\n"
				+ "2. Farming Loan \n"
				+ "3. jewel Loan \n");
		int ch=sc.nextInt();
		
			switch(ch)
			{
			case 1:
			{
				getEduLoan(accno);
				break;
				
			}
			case 2:
			{
				Scanner sss= new Scanner(System.in);
				System.out.println("Farmer Loan amount RS 100000 \n "
						+ " Interest 5%"
						+ "\n 5% interest 0.05*100000 =5000"
						+ "\n Amount : Rs 105000 \n can be emi Rs 5000 per month for 10 months ");
				
				System.out.println("print (ok) for continuing futher");
				String ok=sss.nextLine();
				if(ok.equals( "ok"))
				{
					Random rnd = new Random();
					int loanno = rnd. nextInt(999999);
					BankDao.giveLoan(105000,5000,accno,loanno);
				}
				
					break;
			
				
			}
			case 3:
			{
				int grm=0;
				Scanner sss= new Scanner(System.in);
				System.out.println("Enter the gram :");
				grm=sc.nextInt();
				System.out.println("Jewel Loan amount "+grm*2000
						+ " interest 5% "
						+ "\n 5% interest 0.05*grm " + 0.05*grm*2000
						+ "\n Amount : Rs"+(grm*2000+ grm*0.05)+" \n can be emi Rs 5000 per month for 10 months ");
				
				System.out.println("print (ok) for continuing futher");
				String ok=sss.nextLine();
				if(ok.equals( "ok"))
				{
					Random rnd = new Random();
					int loanno = rnd. nextInt(999999);
					BankDao.giveLoan(105000,5000,accno,loanno);
				}
				break;
			}
			default:
				System.out.println("Enter the choice");
				
			}
					
	}
	
private static void getEduLoan(int accno) throws SQLException {

	System.out.println("Select the type of course\n"
			+ "1. Master degree(3 years)\n"
			+ "2. Master degree (2 years)\n"
			+ "3. Bacehlor Degree(3 years)\n "
			+ "4. Bachelor Degree(4 years)");
	Scanner sc= new Scanner(System.in);
	int opt=sc.nextInt();
	while(true)
	{ Scanner sss= new Scanner(System.in);

		
		if(opt ==1)
		{
			System.out.println("For 3 years master degree you can be allocated amount RS 30000"
					+ "Per Semester .\n Loan Should be paid at the end of 6th Semester with interest 5%"
					+ "\n 30000*6 =1,80,000 total principal amount"
					+ "\n 5% interest 0.05*180000 =9000"
					+ "\n Amount : Rs 189000 \n can be emi Rs 5000 per month for 37 month");
			
			System.out.println("print (ok) for continuing futher");
			String ok=sss.nextLine();
			if(ok.equals( "ok"))
			{
				Random rnd = new Random();
				int loanno = rnd. nextInt(999999);
				BankDao.giveLoan(189000,9000,accno,loanno);
				break;
			}
		}
		else if(opt ==2)
		{
			System.out.println("For 2 years master degree you can be allocated amount RS 30000"
					+ "Per Semester .\n Loan Should be paid at the end of 6th Semester with interest 5%"
					+ "\n 30000*4 =1,20,000 total principal amount"
					+ "\n 5% interest 0.05*120000 =6000"
					+ "\n Amount : Rs 126000 \n can be emi Rs 3000 per month for 38 month");
			
			System.out.println("print (ok) for continuing futher");
			String ok=sss.nextLine();
			if(ok.equals( "ok"))
			{
				Random rnd = new Random();
				int loanno = rnd. nextInt(999999);
				BankDao.giveLoan(126000,6000,accno,loanno);
				break;
			}
			
		}
		else if(opt ==3)
		{
			System.out.println("For 3 years Bachelor degree you can be allocated amount RS 30000"
					+ "Per Semester .\n Loan Should be paid at the end of 6th Semester with interest 5%"
					+ "\n 30000*6 =1,80,000 total principal amount"
					+ "\n 5% interest 0.05*180000 =9000"
					+ "\n Amount : Rs 189000 \n can be emi Rs 7000 per month for 32 month");
			
			System.out.println("print (ok) for continuing futher");			
			String ok=sss.nextLine();
			if(ok.equals( "ok"))
			{
				Random rnd = new Random();
				int loanno = rnd. nextInt(999999);
				BankDao.giveLoan(189000,9000,accno,loanno);
				break;
			}
			
		}
		else if(opt == 4)
		{
			System.out.println("For 4 years Bachelor degree you can be allocated amount RS 30000"
					+ "Per Semester .\n Loan Should be paid at the end of 6th Semester with interest 5%"
					+ "\n 30000*8 =2,40,000 total principal amount"
					+ "\n 5% interest 0.05*180000 =12000"
					+ "\n Amount : Rs 252000 \n can be emi Rs 10000 per month for 35 month");
			
			System.out.println("print (ok) for continuing futher\n");
			String ok=sss.nextLine();
			if(ok.equals( "ok"))
			{
				Random rnd = new Random();
				int loanno = rnd. nextInt(999999);
				BankDao.giveLoan(252000,12000,accno,loanno);
				break;
			}
			
		}
		else
		{
			break;
		}
		
	}

	
}
// get bank transaction history
	public static void bankDetails() throws SQLException
	{
		// create  json file to read all data from table bank
		BankDao.createJsonFile();
		
		
	}
	


	public static void main(String[] args) throws SQLException 
	{


		System.out.println("------ Welcome To Bank ------");
		Scanner s= new Scanner(System.in);
		while(true)
		{
			int ch;
			System.out.println("Enter choice choice");
			System.out.println("  1. Create Account \n "
					+ " 2. Login to existing Account "
					+"\n  3. Get Loan \n"
					+ "  4. get balance Statement "
					+ " \n  5. Get bank transaction history "
					+ "\n  6. Change pin"
					+ "\n  7. Pay Loan"
					+ "\n  8. Press any number to quit");
			ch=s.nextInt();
			if(ch==1)
			{
				createAccount();
			}
			
			else if(ch==2)
			{
				login();	
				
			}
			else if(ch == 3)
			{
				loan();
			}
			else if(ch ==4)
			{
				Scanner sc= new Scanner(System.in);
				System.out.println("Enter the account number :");
				int accno=sc.nextInt();
				BankDao.getBalance(accno);
			}
			else if(ch==5)
			{
				bankDetails();
				
			}
			else if(ch==6)
			{
				//  change pin of login account
				Scanner sc= new Scanner(System.in);
				System.out.println("Enter the account number :");
				int accno=sc.nextInt();
				System.out.println("Enter your new secret pin");
				int pin= sc.nextInt();
				int a=BankDao.changePin(accno,pin);
				if(a==3)
				{
					System.out.println("Your Pin Change updated successfully");
				}
			}
			else if(ch == 7)
			{
				int accno,loanno,amt;
				Scanner sc= new Scanner(System.in);
				System.out.println("Enter the account number :");
			    accno=sc.nextInt();
			    System.out.println("Enter the loan number :");
			    loanno=sc.nextInt();
			    System.out.println("Enter the amount to pay :");
				amt = sc.nextInt();
				BankDao.payLoan(accno,loanno,amt);
			}
			else {
				break;
			}
			
		}		
	
	}
	
}
