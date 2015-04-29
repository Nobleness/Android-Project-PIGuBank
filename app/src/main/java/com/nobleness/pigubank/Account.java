package com.nobleness.pigubank;

public class Account {
	
	//private variables
	int _id;
	String _name;
	String _balance;
	String _main;
	String _credit;
	String _creditlimit;
	
	//empty constructor
	public Account() {
		//empty
	}
	
	// constructor
	public Account(int id, String  name, String balance, String main, String credit, String creditlimit){
		this._id = id;
		this._name = name;
		this._balance = balance;
		this._main = main;
		this._credit = credit;
		this._creditlimit = creditlimit;
	}
	
	// constructor
		public Account(String  name, String balance, String main, String credit, String creditlimit){
			this._name = name;
			this._balance = balance;
			this._main = main;
			this._credit = credit;
			this._creditlimit = creditlimit;
		}
	
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	//getting account name
	public String getAccountName(){
		return this._name;
	}
	
	//setting account name
	public void setAccountName(String name){
		this._name = name;
	}
	
	//getting account balance
	public String getAccountBalance(){
		return this._balance;
	}
	
	//setting account balance
	public void setAccountBalance(String balance){
		this._balance = balance;
	}
	
	//getting account main boolean
	public String getAccountMain(){
		return this._main;
	}
	
	//setting account as main boolean
	public void setAccountMain(String main){
		this._main = main;
	}
	
	//getting account credit boolean
	public String getAccountCredit(){
		return this._credit;
	}
	
	//setting account as credit boolean
	public void setAccountCredit(String credit){
		this._credit = credit;
	}
	
	//getting account credit limit
	public String getAccountCreditLimit(){
		return this._creditlimit;
	}
	
	//setting account credit limit
	public void setAccountCreditLimit(String creditlimit){
		this._creditlimit = creditlimit;
	}
}
