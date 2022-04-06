package BankMngtApp;

public class AccountHolder {
	private int accno;
	private int pin ;
	private int balance ;
	private int phone;
	private String name;
	private String addr;
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AccountHolder() {
		super();
	}
	
	public AccountHolder(int accno, int pin, int balance) {
		super();
		this.accno = accno;
		this.pin = pin;
		this.balance = balance;
	}
	
	public AccountHolder(int accno, int pin, int balance, int phone, String name ,String addr) {
		super();
		this.accno = accno;
		this.pin = pin;
		this.balance = balance;
		this.phone = phone;
		this.name = name;
		this.addr= addr;
	}
	
	
	
}
