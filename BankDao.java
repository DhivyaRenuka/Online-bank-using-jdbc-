package BankMngtApp;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

public class BankDao {

	public static int insertAccountHolder(AccountHolder ah) {
		int ans=0 ,ans1=0,ans2=0;
		
		// insert query
		String SQL ="insert into acc_holder_detail(accno,pin,name,phone,address,balance)"
				+ " values(?,?,?,?,?,?) ";
		String SQL1="insert into login(accno,pin)"
				+ " values(?,?) ";
		String SQL2= "insert into bank(accno,acch_name,pin,deposit,balance) "
				+ "values(?,?,?,?,?) ";
		try(Connection conn= connectionProvider.connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
					

					// set the values of parameter
					pstmt.setInt(1,ah.getAccno());
					pstmt.setInt(2,ah.getPin());
					pstmt.setString(3,ah.getName() );
					pstmt.setInt(4,ah.getPhone() );
					pstmt.setString(5,ah.getAddr());
					pstmt.setInt(6,ah.getBalance());
					// execute query using executeUpdate

					ans= pstmt.executeUpdate();
					

				} catch (SQLException ex) {
					System.out.println("Error in insertion on account holder detail relation ");
					System.out.println(ex.getMessage());
					
				}
		try(Connection conn= connectionProvider.connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS)) {
					

					// set the values of parameter
			         pstmt.setInt(1,ah.getAccno());
			         pstmt.setInt(2,ah.getPin());
					
					// execute query using executeUpdate

					ans1= pstmt.executeUpdate();
					

				} catch (SQLException ex) {
					System.out.println("Error in insertion on login relation ");
					System.out.println(ex.getMessage());
					
				}
		try(Connection conn= connectionProvider.connect();
				PreparedStatement pstmt = conn.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS)) {
					

					// set the values of parameter
					pstmt.setInt(1,ah.getAccno());
					pstmt.setString(2,ah.getName() );
					pstmt.setInt(3,ah.getPin());
					pstmt.setInt(4,ah.getBalance());
					pstmt.setInt(5,ah.getBalance());
					// execute query using executeUpdate

					ans2= pstmt.executeUpdate();
					

				} catch (SQLException ex) {
					System.out.println("Error in insertion on account holder detail relation ");
					System.out.println(ex.getMessage());
					
				}
		
		
		if(ans==ans1 && ans1==ans2)
		{
			return ans;
		}
		return ans;
		
	}

	// check login detail are matching or not (Validation)
	
	public static void CheckLogin(int accno , int pin) {
		String sql="select * from login";
		try (Connection conn = connectionProvider.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
		
			while (rs.next()) {
				
				if(accno == rs.getInt("accno") && pin== rs.getInt("pin"))
				{
					System.out.println("Login to Account Successfully");
				}
						
			}
			

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		
	}

	public static void deposit(int accno, int bal) throws SQLException {
		Connection conn = connectionProvider.connect();
		try {
			
			conn.setAutoCommit(false);
			Statement st=conn.createStatement();
			Statement stt=conn.createStatement();
			String sql="update acc_holder_detail set balance = balance+"+bal+" where accno="+accno;
			String sql1="update bank set balance=balance+"+bal+",deposit="+bal+"where accno="+accno;
			conn.setSavepoint();
			if(st.executeUpdate(sql)==1 && stt.executeUpdate(sql1)==1)
			{
				System.out.println("Amount Credited Successfully!");
			}
			
			conn.commit();
		}catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			conn.rollback();
		}
		finally
		{
			conn.close();
			
		}
		
	}

	public static void TransferAcc(int bal, int accno, int accno1) throws SQLException {
		Connection conn = connectionProvider.connect();
		String sqll = "select * from bank where accno=" + accno;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqll);
		while (rs.next()) {
			if (rs.getInt("balance") < bal || rs.getInt("balance") < 500) {
				System.out.println("Insufficient Balance");
			}
			else
			{
				try {

					conn.setAutoCommit(false);
					Statement st1 = conn.createStatement();
					Statement stt = conn.createStatement();
					Statement st2=conn.createStatement();
					Statement stt2=conn.createStatement();
					String sql2="update acc_holder_detail set balance = balance+"+bal+" where accno="+accno1;
					String sql12="update bank set balance=balance+"+bal+",deposit="+bal+"where accno="+accno1;
					String sql = "update acc_holder_detail set balance = balance-" + bal + " where accno=" + accno;
					String sql1 = "update bank set balance=balance-" + bal + ",withdraw=" + bal + "where accno=" + accno;
					conn.setSavepoint();
					if (st1.executeUpdate(sql) == 1 && stt.executeUpdate(sql1) == 1 && st2.executeUpdate(sql2) == 1 && stt2.executeUpdate(sql12) == 1) {
						System.out.println("Amount Debited Successfully from "+ accno);
						System.out.println("Amount Credited Successfully from "+ accno1);
						
					}

					conn.commit();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					conn.rollback();
				} finally {
					conn.close();

				}
			}
		}
		

	}

	public static void withdraw(int bal, int accno) throws SQLException {
		Connection conn = connectionProvider.connect();
		String sqll = "select * from bank where accno=" + accno;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqll);
		while (rs.next()) {
			if (rs.getInt("balance") < bal || rs.getInt("balance") < 500) {
				System.out.println("Insufficient Balance");
			}
			else
			{
				try {

					conn.setAutoCommit(false);
					Statement st1 = conn.createStatement();
					Statement stt = conn.createStatement();
					String sql = "update acc_holder_detail set balance = balance-" + bal + " where accno=" + accno;
					String sql1 = "update bank set balance=balance-" + bal + ",withdraw=" + bal + "where accno=" + accno;
					conn.setSavepoint();
					if (st1.executeUpdate(sql) == 1 && stt.executeUpdate(sql1) == 1) {
						System.out.println("Amount Debited Successfully!");
					}

					conn.commit();
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
					conn.rollback();
				} finally {
					conn.close();

				}
			}
		}
		
	}

	public static void getBalance(int accno) {
		String sql="select * from bank where accno="+accno;
		try
		{
			Connection conn = connectionProvider.connect();
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			System.out.println("-------------------------");
			 System.out.printf("%12s %10s %10s\n",
                     "Account No", "Name",
                     "Balance");
			 while(rs.next())
			 {
				 System.out.printf("%12d %10s %10d.00\n",
						 rs.getInt("accno"),
						 rs.getString("acch_name"),
				 rs.getInt("balance") );
			 }
			 System.out.println("-------------------------");
			
		}catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
	}

	public static int changePin(int pin, int accno) throws SQLException {
		int a=0;
		String sql="update bank set pin="+pin+"where accno="+accno;
		String sql1="update login set pin="+pin+"where accno="+accno;
		String sql2="update acc_holder_detail set pin="+pin+"where accno="+accno;
	
		Connection conn = null;
		try {
			
			 conn = connectionProvider.connect();
			Statement st=conn.createStatement();
			Statement st1=conn.createStatement();
			Statement st2=conn.createStatement();
		
			a +=st.executeUpdate(sql);
			a += st1.executeUpdate(sql1);
			a += st2.executeUpdate(sql2);
		
			
		}catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			conn.close();
		}
		return a;
		
		
	}

	
	public static void giveLoan(int amt, int interest,int accno, int loanno) throws SQLException {
		String sql="insert into loanAcc(loan_no,accno,amount,interestAmt) values(?,?,?,?)";
		String sql1="update acc_holder_detail set balance= balance+"+amt+"where accno="+accno;
		
		Connection conn = null;
		try {
			
			 conn = connectionProvider.connect();
			 conn.setAutoCommit(false);
			Statement st=conn.createStatement(); 
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ps.setInt(1, loanno);
			ps.setInt(2, accno);
			ps.setInt(3, amt);
			ps.setInt(4, interest);
			conn.setSavepoint();
			if(ps.executeUpdate()==1 && st.executeUpdate(sql1)==1)
			{
				System.out.println("Loan Alloted !");
				conn.commit();
			}
		}catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			conn.rollback();
		}
		finally
		{
			conn.close();
		}
		
	}

	public static void payLoan(int accno, int loanno, int amt) throws SQLException {
		String sql="update loanAcc set amount=amount- ?  where loan_no= ? and amount > 0";
		String sql1="update acc_holder_detail set balance= balance-"+amt+"where accno="+accno;
		String sql2="update bank set balance= balance-"+amt+"where accno="+accno;
		String s="select * from loanAcc";
		Connection conn = null;
		try {
			
			 conn = connectionProvider.connect();
			 conn.setAutoCommit(false);
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, amt);
			ps.setInt(2, loanno);
			
			Statement st=conn.createStatement(); 
			Statement st1=conn.createStatement(); 
			Statement st2=conn.createStatement();
			ResultSet rs=st2.executeQuery(s);
			while(rs.next())
			{
				conn.setSavepoint();
				if(ps.executeUpdate()==1 && st.executeUpdate(sql2)==1 && st1.executeUpdate(sql2)==1)
				{
					System.out.println("Loan EMI paid for the month ");
					System.out.println("Remaining amount Rs :"+ rs.getInt("amount"));
					conn.commit();
				}
				else
				{
					if(rs.getInt("amount")<0)
					{
					conn.rollback();
					System.out.println("Remaining amount RS :"+ rs.getInt("amount"));
					System.out.println("Successfully paid the loan amount");
					}
				}
				
			}
					
			
		}catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			conn.close();
		}
		
	}

	public static void createJsonFile() throws SQLException {
		
		String sql="select * from bank";
		Connection conn=null;
		
	    
	    try {
	    	JSONObject transactionObject = new JSONObject();
	    	conn = connectionProvider.connect();
			Statement st =conn.createStatement();
			ResultSet rs= st.executeQuery(sql);
			while(rs.next())
			{
				transactionObject.put("Account number",rs.getInt("accno"));
				transactionObject.put("Account holder name",rs.getString("acch_name"));
				transactionObject.put("Account pin",rs.getInt("pin"));
				transactionObject.put("Account deposit",rs.getInt("deposit"));
				transactionObject.put("Account withdrawn",rs.getInt("withdraw"));
				transactionObject.put("Account balance",rs.getInt("balance"));
				
			}
	         FileWriter file = new FileWriter("C:\\Users\\dhivya-pt5151\\eclipse-workspace\\BankManagementApp\\src\\BankMngtApp\\output.json");
	         file.write(transactionObject.toString());
	         file.close();
	      } catch (Exception e) {
	         
	         e.printStackTrace();
	      }
	    finally 
	    {
	    	conn.close();
	    }
		
		
	}

	



}
