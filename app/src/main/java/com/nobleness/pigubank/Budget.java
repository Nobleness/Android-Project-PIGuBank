package com.nobleness.pigubank;

public class Budget {
	
	//pprivate variables
	int _id;
	String _name;
	String _limit;
	String _amountspent;
	String _freq;
	String _freqtype;
	String _freqweekday;
    String _freqmonthdatebuttonbole;
    String _freqmonthdaybuttonbole;
	String _freqmonthdate;
	String _freqmonthdatebeforeafter;
	String _freqmonthweek;
	String _freqmonthweekday;
	String _freqyearmonthdate;
	String _freqyearmonth;
	
	//empty constructor
	public Budget() {
		//empty
	}
	
	// constructor
	public Budget(int id,
                  String  name,
                  String limit,
                  String amountspent,
                  String freq,
                  String freqtype,
                  String freqweekday,
                  String freqmonthdatebuttonbole,
                  String freqmonthdaybuttonbole,
                  String freqmonthdate,
                  String freqmonthdatebeforeafter,
			      String freqmonthweek,
                  String freqmonthweekday,
                  String freqyearmonthdate,
                  String freqyearmonth){
		this._id = id;
		this._name = name;
		this._limit = limit;
		this._amountspent = amountspent;
		this._freq = freq;
		this._freqtype = freqtype;
		this._freqweekday = freqweekday;
        this._freqmonthdatebuttonbole = freqmonthdatebuttonbole;
        this._freqmonthdaybuttonbole = freqmonthdaybuttonbole;
		this._freqmonthdate = freqmonthdate;
		this._freqmonthdatebeforeafter = freqmonthdatebeforeafter;
		this._freqmonthweek = freqmonthweek;
		this._freqmonthweekday = freqmonthweekday;
		this._freqyearmonthdate = freqyearmonthdate;
		this._freqyearmonth = freqyearmonth;
	}
	
	// constructor
	public Budget(
            String  name,
            String limit,
            String amountspent,
			String freq,
            String freqtype,
            String freqweekday,
            String freqmonthdatebuttonbole,
            String freqmonthdaybuttonbole,
            String freqmonthdate,
            String freqmonthdatebeforeafter,
			String freqmonthweek,
            String freqmonthweekday,
            String freqyearmonthdate,
            String freqyearmonth){
		this._name = name;
		this._limit = limit;
		this._amountspent = amountspent;
		this._freq = freq;
		this._freqtype = freqtype;
        this._freqweekday = freqweekday;
        this._freqmonthdatebuttonbole = freqmonthdatebuttonbole;
        this._freqmonthdaybuttonbole = freqmonthdaybuttonbole;
		this._freqmonthdate = freqmonthdate;
		this._freqmonthdatebeforeafter = freqmonthdatebeforeafter;
		this._freqmonthweek = freqmonthweek;
		this._freqmonthweekday = freqmonthweekday;
		this._freqyearmonthdate = freqyearmonthdate;
		this._freqyearmonth = freqyearmonth;
	}
	
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	//getting budget name
	public String getBudgetName(){
		return this._name;
	}
	
	//setting budget name
	public void setBudgetName(String name){
		this._name = name;
	}
	
	//getting budget limit
	public String getBudgetLimit(){
		return this._limit;
	}
	
	//setting budget limit
	public void setBudgetLimit(String limit){
		this._limit = limit;
	}
	
	//get budget amountspent
	public String getBudgetAmountSpent(){
		return  this._amountspent;
	}
	
	//set budget amountspent
	public void setBudgetAmountSpent(String amountspent){
		this._amountspent = amountspent;
	}
	
	//getting budget freq
	public String getBudgetFreq(){
		return this._freq;
	}
	
	//setting budget freq
	public void setBudgetFreq(String freq){
		this._freq = freq;
	}
	
	//getting budget freqtype
	public String getBudgetFreqType(){
		return this._freqtype;
	}
	
	//setting budget freqtype
	public void setBudgetFreqType(String freqtype){
		this._freqtype = freqtype;
	}
	
	//getting budget frequency by weekday
	public String getBudgetFreqWeekDay(){
		return this._freqweekday;
	}
	
	//setting budget frequency by weekday
	public void setBudgetFreqWeekDay(String freqweekday){
		this._freqweekday = freqweekday;
	}

    //getting budget freq button boolean of month by date
    public String getBudgetFreqMonthDateButtonBole(){
        return this._freqmonthdatebuttonbole;
    }
    //setting budget freq button boolean of month by date
    public void setBudgetFreqMonthDateButtonBole(String freqmonthdatebuttonbole){
        this._freqmonthdatebuttonbole = freqmonthdatebuttonbole;
    }

    //getting budget freq button boolean of month by day
    public String getBudgetFreqMonthDayButtonBole(){
        return this._freqmonthdaybuttonbole;
    }

    //setting budget freq button boolean of month by day
    public void setBudgetFreqMonthDayButtonBole(String freqmonthdaybuttonbole){
        this._freqmonthdaybuttonbole = freqmonthdaybuttonbole;
    }
	
	//getting budget freq by date of month
	public String getBudgetFreqMonthDate(){
		return this._freqmonthdate;
	}
	
	//setting budget freq by date of month
	public void setBudgetFreqMonthDate(
            String freqmonthdate){
		this._freqmonthdate = freqmonthdate;
	}
	
	//getting budget freq by weekday before or after date
	public String getBudgetFreqMonthDateBeforeAfter() {
		return this._freqmonthdatebeforeafter;
	}
	
	//setting budget freq by weekday before or after date
	public void setBudgetFreqMonthDateBeforeAfter(String freqmonthdatebeforeafter) {
		this._freqmonthdatebeforeafter = freqmonthdatebeforeafter;
	}
	
	//getting budget frequency by week of month
	public String getBudgetFreqMonthWeek(){
		return this._freqmonthweek;
	}
	
	//setting budget frequency by week of month
	public void setBudgetFreqMonthWeek(
            String freqmonthweek){
		this._freqmonthweek = freqmonthweek;
	}
	
	//getting budget frequency by day of month
	public String getBudgetFreqMonthWeekDay(){
		return this._freqmonthweekday;
	}
	
	//setting budget frequency by day of month
	public void setBudgetFreqMonthWeekDay(String freqmonthweekday){
		this._freqmonthweekday = freqmonthweekday;
	}
	
	// getting budget freq by month date of year
	public String getBudgetFreqYearMonthDate(){
		return this._freqyearmonthdate;
	}
	
	// setting budget freq by month date of year
	public void setBudgetFreqYearMonthDate(String freqyearmonthdate) {
		this._freqyearmonthdate = freqyearmonthdate;
	}

	//getting budget frequency by month of year
	public String getBudgetFreqYearMonth(){
		return this._freqyearmonth;
	}
	
	//setting budget frequency by month of year
	public void setBudgetFreqYearMonth(
            String freqyearmonth){
		this._freqyearmonth = freqyearmonth;
	}
}
