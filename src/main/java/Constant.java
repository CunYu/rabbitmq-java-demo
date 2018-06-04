public class Constant {

    public static final String QUEUE_ONE = "queueOne";
    public static final String QUEUE_TWO = "queueTwo";
    public static final String QUEUE_THREE = "queueThree";
    public static final String QUEUE_FOUR = "queueFour";
    public static final String QUEUE_FIVE = "queueFive";
    public static final String QUEUE_SIX = "queueSix";
    public static final String QUEUE_SEVEN = "queueSeven";
    public static final String QUEUE_EIGHT = "queueEight";
    public static final String QUEUE_NIGHT = "queueNight";

    public static final String EXCHANGE_ONE = "exchangeOne";
    public static final String EXCHANGE_TWO = "exchangeTwo";
    public static final String EXCHANGE_THREE = "exchangeThree";
    public static final String EXCHANGE_FOUR = "exchangeFour";
    public static final String EXCHAGE_FIVE = "exchangeFive";

    public static final String MESSAGE = "Hello World!";

    public static final String X_MATCH = "x-match";

    // 交换路由类型
    public enum ExchangeType {

        FANOUT("fanout"), DIRECT("direct"), TOPIC("topic"), HEADERS("headers");

        private String value;

        ExchangeType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}