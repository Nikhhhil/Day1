//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Main{
//    public static void main(String[] args)
//    {
//
//        StockTradingSystem stockTradingSystem=new StockTradingSystem("ABC");
//        int userId1=stockTradingSystem.createUser("Nikhil");
//        int userId2=stockTradingSystem.createUser("Nickey");
//        stockTradingSystem.createOrder(1,null,OrderType.MARKET,OrderActionType.BUY,userId1,"MRF");
//        stockTradingSystem.createOrder(1,null,OrderType.MARKET,OrderActionType.SELL,userId2,"MRF");
//        stockTradingSystem.createOrder(3,null,OrderType.MARKET,OrderActionType.BUY,userId1,"MRF");
//
//        System.out.println("|| TRADES ||");
//        for(Trade it:stockTradingSystem.getTradeByUser(userId2))
//        {
//            System.out.println(it.getBuyer().getName()+" Buyer "+ it.getSeller().getName()+" Seller "+it.getPrice()+" Price "+ it.getQuantity()+" Quantity");
//        }
//        System.out.println("|| Orders ||");
//        for(Order it:stockTradingSystem.getOrdersByUser(userId1))
//        {
//
//            System.out.println(it.getStockSymbol()+" STOCK "+ it.getPrice()+" Price "+it.getQuantity()+" Quantity "+it.getOrderStatus()+" Status ");
//        }
//    }
//}
//
//enum OrderType{
//    MARKET,LIMIT
//}
//enum OrderStatus{
//    PENDING,SUCCESS
//}
//enum OrderActionType{
//    BUY,SELL
//}
//class User{
//    private static int cnt=1;
//    private int id;
//    private String name;
//    public User(String name)
//    {
//        this.name=name;
//        this.id=cnt++;
//    }
//    public String getName()
//    {
//        return name;
//    }
//    public int getId()
//    {
//        return id;
//    }
//}
//
//class Order{
//    private int quantity;
//    private Double price;
//    private String stockSymbol;
//    private OrderType orderType;
//    private OrderActionType orderActionType;
//    private OrderStatus orderStatus;
//    private int remainingQuantity;
//    private int userId;
//    public Order(int quantity,Double price,OrderType orderType,OrderActionType orderActionType,int userId,String stockSymbol)
//    {
//        this.quantity=quantity;
//        this.price=price;
//        this.orderType=orderType;
//        this.orderActionType=orderActionType;
//        this.orderStatus=OrderStatus.PENDING;
//        this.remainingQuantity=quantity;
//        this.userId=userId;
//        this.stockSymbol=stockSymbol;
//    }
//    public OrderStatus getOrderStatus()
//    {
//        return orderStatus;
//    }
//    public String getStockSymbol()
//    {
//        return stockSymbol;
//    }
//    public int getRemainingQuantity()
//    {
//        return remainingQuantity;
//    }
//    public void updateRemainingQuantity(int remainingQuantity)
//    {
//        this.remainingQuantity=remainingQuantity;
//    }
//    public int getUserId()
//    {
//        return userId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//    public Double getPrice() {
//        return price;
//    }
//    public OrderType getOrderType()
//    {
//        return orderType;
//    }
//    public OrderActionType getOrderActionType()
//    {
//        return orderActionType;
//    }
//    public void updateOrderStatus(OrderStatus orderStatus)
//    {
//        this.orderStatus=orderStatus;
//    }
//}
//
//class Trade{
//    private Double price;
//    private int quantity;
//    private User seller;
//    private User buyer;
//    public Trade(Double price,int quantity,User seller,User buyer)
//    {
//        this.price=price==null?0d:price;
//        this.quantity=quantity;
//        this.seller=seller;
//        this.buyer=buyer;
//    }
//    public Double getPrice()
//    {
//        return price;
//    }
//    public int getQuantity()
//    {
//        return quantity;
//    }
//    public User getSeller()
//    {
//        return seller;
//    }
//    public User getBuyer()
//    {
//        return buyer;
//    }
//}
//
//class StockTradingSystem{
//    private String name;
//    private Map<Integer,User> userMap;
//    private Map<Integer,List<Order>> userOrders;
//    private Map<Integer,List<Trade>> userTrades;
//    private Map<String,List<Order>> buyOrders;
//    private Map<String,List<Order>> sellOrders;
//    public StockTradingSystem(String name)
//    {
//        this.name=name;
//        this.userMap=new HashMap<>();
//        this.userTrades=new HashMap<>();
//        this.userOrders=new HashMap<>();
//        this.buyOrders=new HashMap<>();
//        this.sellOrders=new HashMap<>();
//    }
//    public int createUser(String name)
//    {
//        User user=new User(name);
//        this.userMap.put(user.getId(), user);
//        this.userOrders.put(user.getId(),new ArrayList<>());
//        this.userTrades.put(user.getId(),new ArrayList<>());
//        return user.getId();
//    }
//    public void createOrder(int quantity,Double price,OrderType orderType,OrderActionType orderActionType,int userId,String stockSymbol)
//    {
//        if(orderType.equals(OrderType.MARKET)&& price!=null)
//        {
//            System.out.println("Market order can not contain the price");
//            return;
//        }
//        if(orderType.equals(OrderType.LIMIT)&&price==null)
//        {
//            System.out.println("LIMIT order need the price");
//            return;
//        }
//        Order order=new Order(quantity,price,orderType,orderActionType,userId,stockSymbol);
//        if(orderActionType.equals(OrderActionType.BUY))
//        {
//            this.buyOrders.computeIfAbsent(stockSymbol,k->new ArrayList<>()).add(order);
//        }else {
//            this.sellOrders.computeIfAbsent(stockSymbol,k->new ArrayList<>()).add(order);
//        }
//        this.userOrders.get(userId).add(order);
//        processOrder(stockSymbol);
//    }
//
//    private void processOrder(String stockSymbol)
//    {
//        if(!buyOrders.containsKey(stockSymbol))
//        {
//            System.out.println("Currently No Buy Order. your request is queued");
//            return ;
//        }
//        List<Order> buys=buyOrders.get(stockSymbol);
//        List<Order> removeOrders=new ArrayList<>();
//        for(Order order:buys)
//        {
//            if(processBuy(order))
//            {
//                order.updateOrderStatus(OrderStatus.SUCCESS);
//                removeOrders.add(order);
//            }
//        }
//        buys.removeAll(removeOrders);
//    }
//    private boolean processBuy(Order order)
//    {
//        OrderType orderType=order.getOrderType();
//        int quantity= order.getRemainingQuantity();
//        if(!sellOrders.containsKey(order.getStockSymbol()))
//        {
//            System.out.println("Currently No Sell Order. your request is queued");
//            return false;
//        }
//        List<Order> sells=sellOrders.get(order.getStockSymbol());
//        List<Order> removeOrders=new ArrayList<>();
//        for(Order sell:sells)
//        {
//            if(orderType.equals(OrderType.MARKET)||sell.getPrice()<=order.getPrice())
//            {
//                int sellQuant=sell.getRemainingQuantity();
//                int tradedQuantity;
//                if(quantity>=sellQuant)
//                {
//                    quantity-=sellQuant;
//                    tradedQuantity=sellQuant;
//                    sell.updateOrderStatus(OrderStatus.SUCCESS);
//                    sell.updateRemainingQuantity(0);
//                    removeOrders.add(sell);
//                }else{
//                    tradedQuantity=quantity;
//                    sell.updateRemainingQuantity(sellQuant-quantity);
//                    quantity=0;
//                }
//                Trade trade=new Trade(sell.getPrice(),tradedQuantity,userMap.get(sell.getUserId()),userMap.get(order.getUserId()));
//
//                this.userTrades.get(sell.getUserId()).add(trade);
//                this.userTrades.get(order.getUserId()).add(trade);
//
//            }
//            if(quantity==0) break;
//        }
//        sells.removeAll(removeOrders);
//        order.updateRemainingQuantity(quantity);
//        return quantity==0;
//    }
//
//    public List<Trade> getTradeByUser(int userId)
//    {
//        return userTrades.get(userId);
//    }
//    public List<Order> getOrdersByUser(int userId)
//    {
//        return userOrders.get(userId);
//    }
//}