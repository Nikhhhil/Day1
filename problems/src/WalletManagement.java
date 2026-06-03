//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.*;
//
//public class Main{
//    public static void main(String[] args)
//    {
//
//        WalletManagement walletManagement=new WalletManagement("Paytm");
//        walletManagement.onboardUser("Nikhil","7080808080");
//        walletManagement.onboardUser("nickey","8080808080");
//        walletManagement.addMoney("7080808080",PaymentMode.UPI,BigDecimal.valueOf(50));
//        walletManagement.spendMoney("7080808080",BigDecimal.valueOf(10));
//        walletManagement.transferMoney("7080808080","8080808080",BigDecimal.valueOf(50));
//        walletManagement.transferMoney("7080808080","8080808080", BigDecimal.valueOf(30));
//
//        System.out.println("|| ALL TXNS NIKHIL ||");
//        walletManagement.showAllTransaction("7080808080");
//        System.out.println("|| ALL TXNS nickey ||");
//        walletManagement.showAllTransaction("8080808080");
//
//    }
//}
//enum TransactionStatus{
//    // No simulation of transaction statuses so only maintaining SUCCESS txn status
//    SUCCESS
//}
//enum PaymentMode{
//    UPI,NET_BANKING,CREDIT_CARD,DEBIT_CARD,WALLET
//}
//enum TransactionType{
//    CREDIT,DEBIT,TRANSFER_IN,TRANSFER_OUT
//}
//class Transaction{
//    private BigDecimal amount;
//    private PaymentMode paymentMode;
//    private TransactionType transactionType;
//    private TransactionStatus transactionStatus;
//    private LocalDateTime txnTime;
//    public Transaction(BigDecimal amount,PaymentMode paymentMode,TransactionType transactionType,TransactionStatus transactionStatus)
//    {
//        this.amount=amount;
//        this.paymentMode=paymentMode;
//        this.transactionType=transactionType;
//        this.transactionStatus=transactionStatus;
//        this.txnTime=LocalDateTime.now();
//    }
//    public TransactionStatus getTransactionStatus()
//    {
//        return transactionStatus;
//    }
//    public LocalDateTime getTxnTime()
//    {
//        return txnTime;
//    }
//    public BigDecimal getAmount()
//    {
//        return amount;
//    }
//    public PaymentMode getPaymentMode()
//    {
//        return paymentMode;
//    }
//    public TransactionType getTransactionType()
//    {
//        return transactionType;
//    }
//}
//class Wallet{
//    private BigDecimal balance;
//    private List<Transaction> transactions;
//    public Wallet()
//    {
//        this.transactions=new ArrayList<>();
//        this.balance=BigDecimal.ZERO;
//    }
//
//    public BigDecimal getBalance() {
//        return balance;
//    }
//    public void updateBalance(BigDecimal amount)
//    {
//        this.balance=balance.add(amount);
//    }
//    public void addTransactions(Transaction transaction)
//    {
//        this.transactions.add(transaction);
//    }
//    public List<Transaction> getTransactions() {
//        return Collections.unmodifiableList(transactions);
//    }
//}
//class User{
//    private String name;
//    private String phoneNumber;
//    private Wallet wallet;
//    public User(String name,String phoneNumber)
//    {
//        this.name=name;
//        this.phoneNumber=phoneNumber;
//        this.wallet=new Wallet();
//    }
//    public String getName() {
//        return name;
//    }
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//    public Wallet getWallet() {
//        return wallet;
//    }
//}
//
//class NotificationService{
//    public static void notifyUser(User user,String message)
//    {
//        System.out.println("Notification for "+user.getName()+" phone number "+user.getPhoneNumber());
//        System.out.println(message);
//    }
//}
//class WalletManagement {
//    private Map<String, User> userMap;
//    private String name;
//
//    public WalletManagement(String name) {
//        this.name = name;
//        this.userMap = new HashMap<>();
//    }
//
//    public void onboardUser(String name, String phoneNumber) {
//        if (userMap.containsKey(phoneNumber)) {
//            System.out.println("User with phone number " + phoneNumber + " already exists in out system ");
//            return;
//        }
//        User user = new User(name, phoneNumber);
//        this.userMap.put(phoneNumber, user);
//    }
//
//    public void addMoney(String phoneNumber, PaymentMode paymentMode, BigDecimal amount) {
//        if (!userMap.containsKey(phoneNumber)) {
//            System.out.println("User with phone number " + phoneNumber + " do not exists in out system ");
//            return;
//        }
//        User user = userMap.get(phoneNumber);
//        if((user.getWallet().getBalance().add(amount)).compareTo(BigDecimal.valueOf(10000))>0)
//        {
//            NotificationService.notifyUser(user,"Max wallet Limit reached,as amount in wallet can not be more than 10000");
//            return;
//        }
//        Transaction transaction = new Transaction(amount, paymentMode, TransactionType.CREDIT, TransactionStatus.SUCCESS);
//        user.getWallet().addTransactions(transaction);
//        user.getWallet().updateBalance(amount);
//        NotificationService.notifyUser(user, "Amount " + amount + " has been credited to your account via payment mode " + paymentMode);
//    }
//
//    public void spendMoney(String phoneNumber, BigDecimal amount) {
//        if (!userMap.containsKey(phoneNumber)) {
//            System.out.println("User with phone number " + phoneNumber + " do not exists in out system ");
//            return;
//        }
//        User user = userMap.get(phoneNumber);
//        if (user.getWallet().getBalance().compareTo(amount) < 0) {
//            NotificationService.notifyUser(user, "Insufficient balance for Payment");
//            return;
//        }
//        Transaction transaction = new Transaction(amount, PaymentMode.WALLET, TransactionType.DEBIT, TransactionStatus.SUCCESS);
//        user.getWallet().addTransactions(transaction);
//        user.getWallet().updateBalance(amount.multiply(BigDecimal.valueOf(-1)));
//        NotificationService.notifyUser(user, "Amount " + amount + " has been debited to your account ");
//        if(user.getWallet().getBalance().compareTo(BigDecimal.valueOf(100))<0)
//        {
//            NotificationService.notifyUser(user, "Low Balance in your wallet account , please recharge the wallet");
//        }
//    }
//
//    public void transferMoney(String senderPhoneNumber, String receiverPhoneNumber, BigDecimal amount) {
//        if (!userMap.containsKey(senderPhoneNumber)) {
//            System.out.println("User with phone number " + senderPhoneNumber + " do not exists in out system ");
//            return;
//        }
//        if (!userMap.containsKey(receiverPhoneNumber)) {
//            System.out.println("User with phone number " + receiverPhoneNumber + " do not exists in out system ");
//            return;
//        }
//        User sender = userMap.get(senderPhoneNumber);
//        User receiver = userMap.get(receiverPhoneNumber);
//        if (sender.getWallet().getBalance().compareTo(amount) < 0) {
//            NotificationService.notifyUser(sender, "Insufficient balance for Transfer");
//            return;
//        }
//        if((receiver.getWallet().getBalance().add(amount)).compareTo(BigDecimal.valueOf(10000))>0)
//        {
//            NotificationService.notifyUser(receiver,"Max wallet Limit reached,as amount in wallet can not be more than 10000");
//            NotificationService.notifyUser(sender,"Max wallet Limit reached for receiver");
//
//            return;
//        }
//        Transaction transactionReceiver = new Transaction(amount, PaymentMode.WALLET, TransactionType.TRANSFER_IN, TransactionStatus.SUCCESS);
//        Transaction transactionSender = new Transaction(amount, PaymentMode.WALLET, TransactionType.TRANSFER_OUT, TransactionStatus.SUCCESS);
//        sender.getWallet().addTransactions(transactionSender);
//        receiver.getWallet().addTransactions(transactionReceiver);
//        sender.getWallet().updateBalance(BigDecimal.valueOf(-1).multiply(amount));
//        receiver.getWallet().updateBalance(amount);
//        NotificationService.notifyUser(sender, "Amount " + amount + " has been debited to your account via outgoing Transfer");
//        NotificationService.notifyUser(receiver, "Amount " + amount + " has been credited to your account via incoming Transfer");
//        if(sender.getWallet().getBalance().compareTo(BigDecimal.valueOf(100))<0)
//        {
//            NotificationService.notifyUser(sender, "Low Balance in your wallet account , please recharge the wallet");
//        }
//    }
//
//    public void showAllTransaction(String phoneNumber) {
//        if (!userMap.containsKey(phoneNumber)) {
//            System.out.println("User with phone number " + phoneNumber + " do not exists in out system ");
//            return;
//        }
//        User user = userMap.get(phoneNumber);
//        List<Transaction> transactions = user.getWallet().getTransactions();
//        for (Transaction transaction : transactions) {
//            System.out.println("Amount " + transaction.getAmount() + " Transaction Type " + transaction.getTransactionType() + " Mode " + transaction.getPaymentMode()+" transaction Status "+transaction.getTransactionStatus()+" Timestamp "+transaction.getTxnTime());
//        }
//
//    }
//}