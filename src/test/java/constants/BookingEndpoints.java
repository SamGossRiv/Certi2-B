package constants;

public class BookingEndpoints {
    public static final String BASE_URL = "https://restful-booker.herokuapp.com";
    public static final String GET_BOOKING_BY_ID = BASE_URL + "/booking/{id}";
    public static final String CREATE_BOOKING = BASE_URL + "/booking";
    public static final String UPDATE_BOOKING = BASE_URL + "/booking/{id}";
    public static final String DELETE_BOOKING = BASE_URL + "/booking/{id}";
}
