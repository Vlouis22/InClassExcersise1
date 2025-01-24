package org.example;

class BankAccount{
    private final String owner;
    private double balance;

    public BankAccount(String owner, double balance){
        this.owner = owner;
        this.balance = balance;
    }

    public String getOwner(){
        return this.owner;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double newbalance){
        this.balance = newbalance;
    }

    public String deposit(double amount){
        this.balance += amount;
        return "Hi " + getOwner() + ", your new balance is: $" + getBalance();
    }

    public String withdrawal(double amount){
        if (amount < getBalance()){
            this.balance -= amount;
            return "Your new balance is: $"+getBalance();
        } else {
            return "insufficient balance";
        }
    }
}

class Check{
    boolean isSigned;
    double checkAmount;
    String checkOwner;
    public Check(double checkAmount, boolean isSigned, String checkOwner){
        this.checkAmount = checkAmount;
        this.isSigned = isSigned;
        this.checkOwner = checkOwner;
    }
    public double getCheckAmount(){
        return this.checkAmount;
    }

    public String signCheck(){
        if (isSigned){
            return "check is already signed by a person.";
        } else {
            this.isSigned = true;
            return "Check has been signed and ready to be used.";
        }
    }

    public String getCheckOwner(){
        return this.checkOwner;
    }
}

class CheckingAccount extends BankAccount{
    final double insufficientFundsFee = 25.0;

    public CheckingAccount(String owner, double balance){
        super(owner, balance);
        System.out.println("created a checking account for "+owner+ " with an initial balance of $" + balance);
    }
    
    public String withdrawal(double amount){
        double newBalance = this.getBalance() - amount;

        if (amount < getBalance()){
            setBalance(newBalance);
            return "Your new balance is: $"+getBalance();
        } else {
            newBalance -= insufficientFundsFee;
            setBalance(newBalance);
            return "You have been charged an additional fee of $" + this.insufficientFundsFee +
                    ". Your new balance is: $"+getBalance();
        }
    }

    public String processCheck(Check checkToProcess){
        double amount = checkToProcess.getCheckAmount();
        if(checkToProcess.isSigned && getOwner().equals(checkToProcess.getCheckOwner())){
            double newBalance = getBalance() + amount;
            setBalance(newBalance);
            return "check has been processed successfully";
        }
        else if(checkToProcess.isSigned && !getOwner().equals(checkToProcess.getCheckOwner())) {
            return "Only the owner can cash out this check.";
        }
        else{
            return "Unable to process because the check is not signed by the owner";
        }
    }
}

class SavingsAccount extends BankAccount{
    final double annualInterestRate = 0.03;

    public SavingsAccount(String owner, double balance){
        super(owner, balance);
        System.out.println("created a savings account for "+owner+ " with an initial balance of $" + balance);
    }

    public String withdrawal(double amount){
        double newBalance = this.getBalance() - amount;
        if (amount < getBalance()){
            setBalance(newBalance);
            return "Your new balance is: $"+getBalance();
        } else {
            return "Insufficient balance";
        }
    }

    public void depositMonthlyInterest(){
        double interestAmount = (getBalance() * annualInterestRate) / 12;
        double newBalance = getBalance() + interestAmount;
        setBalance(newBalance);
    }
}

public class Main {
    public static void main(String[] args) {
        CheckingAccount louisCheckingAccount = new CheckingAccount("Louis", 200);
        System.out.println("Your balance is: $"+louisCheckingAccount.getBalance());

        louisCheckingAccount.withdrawal(35.0);
        Check louisCheck1001 = new Check(3000, false, "Louis");
        louisCheck1001.signCheck();
        louisCheckingAccount.processCheck(louisCheck1001);
        System.out.println("Your new balance is: $" + louisCheckingAccount.getBalance());

        SavingsAccount louisSavingsAccount = new SavingsAccount("Louis", 25000);
        System.out.println("Your balance is: $"+louisSavingsAccount.getBalance());
        louisSavingsAccount.depositMonthlyInterest();
        louisSavingsAccount.depositMonthlyInterest();
        System.out.println("Your new balance is: $" + Math.round(louisSavingsAccount.getBalance()*100.0)/100.0);
    }
}