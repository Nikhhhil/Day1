//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.*;
//
//public class Main{
//    public static void main(String[] args)
//    {
//
//        ServiceManagementSystem netflix=new ServiceManagementSystem("NETFLIX");
//        netflix.createUser("Nikhil","7007008080");
//        netflix.subscribePlan(PlanType.SIX_MONTH,ServiceType.PREMIUM,"7007008080");
//        netflix.showPlanForUser("7007008080");
//        netflix.showPaymentForUser("7007008080");
//        netflix.pausePlan("7007008080",30);
//        netflix.showPlanForUser("7007008080");
//    }
//}
//
//enum ServiceType{
//    PRO(BigDecimal.TEN),PREMIUM(BigDecimal.valueOf(100)),PRIME(BigDecimal.valueOf(1000));
//    private final BigDecimal costPerMonth;
//    ServiceType(BigDecimal costPerMonth)
//    {
//        this.costPerMonth=costPerMonth;
//    }
//
//    public BigDecimal getCostPerMonth() {
//        return costPerMonth;
//    }
//}
//enum PaymentStatus{
//    SUCCESS
//}
//enum PlanType{
//    ONE_MONTH(1),
//    THREE_MONTH(3),
//    SIX_MONTH(6),
//    TWELVE_MONTH(12);
//    private final int months;
//    PlanType(int months)
//    {
//        this.months=months;
//    }
//    public int getMonths()
//    {
//        return months;
//    }
//}
//
//class User{
//    private final String name;
//    private final String phoneNumber;
//    public User(String name,String phoneNumber)
//    {
//        this.name=name;
//        this.phoneNumber=phoneNumber;
//    }
//    public String getName()
//    {
//        return name;
//    }
//    public String getPhoneNumber()
//    {
//        return phoneNumber;
//    }
//    @Override
//    public int hashCode()
//    {
//        return Objects.hash(phoneNumber);
//    }
//    @Override
//    public boolean equals(Object user)
//    {
//        if(this==user) return true;
//        if(!(user instanceof User)) return false;
//        return  ((User) user).phoneNumber.equals(phoneNumber);
//    }
//}
//
//class Payment{
//    private final BigDecimal amount;
//    private final PaymentStatus paymentStatus;
//    private final LocalDateTime paymentTime;
//    public Payment(BigDecimal amount,PaymentStatus paymentStatus)
//    {
//        this.amount=amount;
//        this.paymentStatus=paymentStatus;
//        this.paymentTime=LocalDateTime.now();
//    }
//    public BigDecimal getAmount()
//    {
//        return amount;
//    }
//
//    public PaymentStatus getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public LocalDateTime getPaymentTime() {
//        return paymentTime;
//    }
//}
//
//class Plan{
//    private PlanType planType;
//    private ServiceType serviceType;
//    private LocalDateTime startTime;
//    private LocalDateTime pausedUntil;
//    private LocalDateTime endTime;
//    public Plan(PlanType planType,ServiceType serviceType,LocalDateTime startTime,LocalDateTime endTime)
//    {
//        this.planType=planType;
//        this.serviceType=serviceType;
//        this.startTime=startTime;
//        this.endTime=endTime;
//        this.pausedUntil=null;
//    }
//    public PlanType getPlanType()
//    {
//        return  planType;
//    }
//    public ServiceType getServiceType()
//    {
//        return serviceType;
//    }
//    public LocalDateTime getStartTime()
//    {
//        return  startTime;
//    }
//    public LocalDateTime getEndTime()
//    {
//        return endTime;
//    }
//    public void setPlanType(PlanType planType)
//    {
//        this.planType=planType;
//    }
//    public void setServiceType(ServiceType serviceType)
//    {
//        this.serviceType=serviceType;
//    }
//    public void setEndTime(LocalDateTime endTime)
//    {
//        this.endTime=endTime;
//    }
//    public void setPausedUntil(LocalDateTime pausedUntil)
//    {
//        this.pausedUntil=pausedUntil;
//    }
//    public boolean isServiceActive()
//    {
//        return LocalDateTime.now().isBefore(endTime)&&LocalDateTime.now().isAfter(startTime)&&(pausedUntil==null||LocalDateTime.now().isAfter(pausedUntil));
//    }
//}
//
//class NotificationService{
//    public static void NotifyUser(User user,String message)
//    {
//        System.out.println("Notification to user "+user.getName()+" on phone number "+user.getPhoneNumber());
//        System.out.println(message);
//    }
//}
//class ServiceManagementSystem{
//    private String name;
//    private Map<String,User> userMap;
//    private Map<User,List<Payment>> userPayment;
//    private Map<User,Plan> userPlan;
//    public ServiceManagementSystem(String name)
//    {
//        this.name=name;
//        this.userMap=new HashMap<>();
//        this.userPayment=new HashMap<>();
//        this.userPlan=new HashMap<>();
//    }
//    public void createUser(String name,String phoneNumber)
//    {
//        if(userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" already exists in our system ");
//            return;
//        }
//        User user=new User(name,phoneNumber);
//        this.userMap.put(phoneNumber,user);
//        this.userPayment.put(user,new ArrayList<>());
//    }
//    public void subscribePlan(PlanType planType,ServiceType serviceType,String phoneNumber)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        if(userPlan.containsKey(user)&&userPlan.get(user).isServiceActive())
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You already have an active plan , you can update your plan but can not purchase multiple plan");
//            return;
//        }
//        if(userPlan.containsKey(user))
//        {
//            this.userPlan.remove(user);
//        }
//        BigDecimal amount=serviceType.getCostPerMonth().multiply(BigDecimal.valueOf(planType.getMonths()));
//        NotificationService.NotifyUser(user,"Hi "+user.getName()+" please pay amount "+amount+  " to complete the subscription");
//        // SIMULATION of real payment
//        Payment payment=new Payment(amount,PaymentStatus.SUCCESS);
//        Plan plan=new Plan(planType,serviceType,LocalDateTime.now(),LocalDateTime.now().plusMonths(planType.getMonths()));
//        NotificationService.NotifyUser(user,"Hi "+user.getName()+" Your plan is activated , Enjoy !");
//        this.userPayment.get(user).add(payment);
//        this.userPlan.put(user,plan);
//    }
//    public void unsubscribePlan(String phoneNumber)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        if(!userPlan.containsKey(user))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not unsubscribe");
//            return;
//        }
//        if(!(userPlan.get(user).isServiceActive()))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not unsubscribe");
//            userPlan.remove(user);
//            return;
//        }
//        Plan plan=userPlan.get(user);
//        BigDecimal amountToBeRefunded= plan.getServiceType().getCostPerMonth().divideToIntegralValue(BigDecimal.valueOf(30)).multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDateTime.now(),plan.getEndTime())));
//        NotificationService.NotifyUser(user,"Hi "+user.getName()+" Successfully unsubscribed amount refunded "+amountToBeRefunded);
//        userPlan.remove(user);
//    }
//    public void pausePlan(String phoneNumber,int numberOfDays)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        if(!userPlan.containsKey(user))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not Pause the plan");
//            return;
//        }
//        if(!(userPlan.get(user).isServiceActive()))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not Pause the plan");
//            userPlan.remove(user);
//            return;
//        }
//        Plan plan=userPlan.get(user);
//        plan.setPausedUntil(LocalDateTime.now().plusDays(numberOfDays));
//        plan.setEndTime(plan.getEndTime().plusDays(numberOfDays));
//        NotificationService.NotifyUser(user,"Hi "+user.getName()+" Your plan has been paused till "+plan.getEndTime());
//    }
//    public void changeServiceOfPlan(ServiceType serviceType,String phoneNumber)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        if(!userPlan.containsKey(user))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not Change the service of plan");
//            return;
//        }
//        if(!(userPlan.get(user).isServiceActive()))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not Change the service of plan");
//            userPlan.remove(user);
//            return;
//        }
//        Plan plan=userPlan.get(user);
//        BigDecimal amountDiff=(plan.getServiceType().getCostPerMonth().subtract(serviceType.getCostPerMonth())).divideToIntegralValue(BigDecimal.valueOf(30)).multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDateTime.now(),plan.getEndTime())));
//        if(amountDiff.compareTo(BigDecimal.ZERO)<0)
//        {
//            // User have to pay because update in serivce type
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" Please pay amount "+amountDiff.abs()+" to upgrade your services");
//            Payment payment=new Payment(amountDiff.abs(),PaymentStatus.SUCCESS);
//            this.userPayment.get(user).add(payment);
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" Payment is successful you service is upgraded to "+serviceType);
//        }else{
//            // refund because downgrade in service type
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" amount " +amountDiff+" is refunded to your account and your service is changed to "+serviceType);
//        }
//        plan.setServiceType(serviceType);
//    }
//    public void showPlanForUser(String phoneNumber)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        if(!userPlan.containsKey(user))
//        {
//            NotificationService.NotifyUser(user,"Hi "+user.getName()+" You have no active plan so you can not Change the service of plan");
//            return;
//        }
//        System.out.println("|| Plan for user "+user.getName()+" ||");
//        Plan plan=userPlan.get(user);
//        System.out.println("Plan Type "+plan.getPlanType()+" Service Type "+plan.getServiceType()+" Start time "+plan.getStartTime()+ " End Time "+plan.getEndTime());
//    }
//    public void showPaymentForUser(String phoneNumber)
//    {
//        if(!userMap.containsKey(phoneNumber))
//        {
//            System.out.println("User with phone number "+phoneNumber+" do not exists in our system ");
//            return;
//        }
//        User user=userMap.get(phoneNumber);
//        System.out.println("|| Payments for user "+user.getName()+" ||");
//        for(Payment payment:userPayment.get(user))
//        {
//            System.out.println("AMOUNT "+payment.getAmount()+" Status "+payment.getPaymentStatus()+ " Payment time "+payment.getPaymentTime());
//        }
//    }
//}