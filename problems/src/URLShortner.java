//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import static java.lang.Math.abs;
//
//public class Main{
//    public static void main(String[] args)
//    {
//        UrlShortner urlShortner=new UrlShortner("https://short.ly/");
//        EncodingStrategy encodingStrategy= new RandomEncodingStrategy();
//        urlShortner.addEncodingStrategy(encodingStrategy);;
//        String url=urlShortner.shortUrl("http:/google.com",EncodingType.RANDOM,LocalDateTime.now().plusDays(1));
//        System.out.println("Short url "+url);
//        String fetchedLongUlr=urlShortner.getUrl(url);
//        System.out.println("Long url "+fetchedLongUlr);
//        urlShortner.updateExpiryOfActiveShortUrl(url,LocalDateTime.now().minusDays(5));
//        urlShortner.getUrl(url);
//
//
//    }
//}
//enum EncodingType{RANDOM}
//
//interface EncodingStrategy{
//    EncodingType getEncodingType();
//    String encode(String url);
//}
//class RandomEncodingStrategy implements EncodingStrategy{
//    private Random random=new Random();
//    private String randomString="kjdbfinsafioueAJHHJVJHVBDJWHB32iu4y2i3y42hnniun3iundiun2lfnwjeb2i3y2893hrd9239dh2923iuh";
//    @Override
//    public EncodingType getEncodingType()
//    {
//        return  EncodingType.RANDOM;
//    }
//    @Override
//    public String encode(String url)
//    {
//        StringBuilder encodedString= new StringBuilder();
//        for(int i=0;i<6;i++)
//        {
//            int randomIndex=abs(random.nextInt(randomString.length()));
//            encodedString.append(randomString.charAt(randomIndex));
//        }
//        return encodedString.toString();
//    }
//}
//class Url{
//    private String url;
//    private String shortUrl;
//    private int accessCount;
//    private LocalDateTime expiry;
//    private EncodingType encodingType;
//    public Url(String url,String shortUrl,EncodingType encodingType,LocalDateTime expiry)
//    {
//        this.url=url;
//        this.shortUrl=shortUrl;
//        this.encodingType=encodingType;
//        this.accessCount=0;
//        this.expiry=expiry;
//    }
//
//    public String getUrl()
//    {
//        return url;
//    }
//
//    public void incrementAccessCount()
//    {
//        this.accessCount++;
//        System.out.println("Url "+shortUrl+" is accessed "+ accessCount+" times");
//    }
//    public LocalDateTime getExpiry()
//    {
//        return expiry;
//    }
//    public void setExpiry(LocalDateTime localDateTime)
//    {
//        this.expiry=localDateTime;
//    }
//    public String getShortUrl()
//    {
//        return shortUrl;
//    }
//    public int getAccessCount()
//    {
//        return accessCount;
//    }
//}
//
//class UrlShortner{
//    private Map<String,Url> urlMap;
//    private Map<String,String> longUrlSortUrlMap;
//    private Map<EncodingType,EncodingStrategy> encodingTypeEncodingStrategyMap;
//    private String baseUrl;
//    public UrlShortner(String baseUrl)
//    {
//        this.baseUrl=baseUrl;
//        this.urlMap=new HashMap<>();
//        this.encodingTypeEncodingStrategyMap=new HashMap<>();
//        this.longUrlSortUrlMap=new HashMap<>();
//    }
//    public void addEncodingStrategy(EncodingStrategy encodingStrategy)
//    {
//        this.encodingTypeEncodingStrategyMap.put(encodingStrategy.getEncodingType(),encodingStrategy);
//    }
//    public String shortUrl(String url,EncodingType encodingType,LocalDateTime expiry)
//    {
//        if(!encodingTypeEncodingStrategyMap.containsKey(encodingType))
//        {
//            System.out.println("No Encoding strategy exists for this encoding type "+encodingType);
//            return null;
//        }
//
//        if(longUrlSortUrlMap.containsKey(url)) {
//            Url existingUrl = urlMap.get(longUrlSortUrlMap.get(url));
//            if(existingUrl.getExpiry().isAfter(LocalDateTime.now()))
//            {
//                return existingUrl.getShortUrl();
//            }
//            this.longUrlSortUrlMap.remove(url);
//            this.urlMap.remove(existingUrl.getShortUrl());
//        }
//
//
//        String shortUrl;
//        do{
//            shortUrl=encodingTypeEncodingStrategyMap.get(encodingType).encode(url);
//        }while(urlMap.containsKey(baseUrl+shortUrl));
//
//        shortUrl=baseUrl+shortUrl;
//        Url urlObject= new Url(url,shortUrl,encodingType,expiry);
//        this.urlMap.put(shortUrl,urlObject);
//        this.longUrlSortUrlMap.put(url,shortUrl);
//        return shortUrl;
//    }
//    public String getUrl(String shortUrl)
//    {
//        if(!urlMap.containsKey(shortUrl))
//        {
//            System.out.println("No such Url "+shortUrl+" Exists in our system");
//            return null;
//        }
//        Url url=urlMap.get(shortUrl);
//        if(LocalDateTime.now().isAfter(url.getExpiry()))
//        {
//            System.out.println("Url "+shortUrl+" is Expired");
//            this.longUrlSortUrlMap.remove(url.getUrl());
//            this.urlMap.remove(shortUrl);
//            return null;
//        }
//        url.incrementAccessCount();
//        return url.getUrl();
//    }
//    public void updateExpiryOfActiveShortUrl(String shortUrl,LocalDateTime expiry)
//    {
//        if(!urlMap.containsKey(shortUrl))
//        {
//            System.out.println("Url "+shortUrl+" do not exists in the system");
//            return;
//        }
//        if(LocalDateTime.now().isAfter(urlMap.get(shortUrl).getExpiry()))
//        {
//            System.out.println("Url "+shortUrl+" is expired and do not exists in the system now ");
//            this.longUrlSortUrlMap.remove(urlMap.get(shortUrl).getUrl());
//            this.urlMap.remove(shortUrl);
//            return;
//        }
//        urlMap.get(shortUrl).setExpiry(expiry);
//    }
//    public void deleteUrl(String shortUrl)
//    {
//        if(urlMap.containsKey(shortUrl))
//        {
//            this.longUrlSortUrlMap.remove(urlMap.get(shortUrl).getUrl());
//            this.urlMap.remove(shortUrl);
//            System.out.println("Url "+shortUrl+" is Deleted successfully");
//            return;
//        }
//        System.out.println("Url "+shortUrl+" do not exists in the system");
//    }
//}