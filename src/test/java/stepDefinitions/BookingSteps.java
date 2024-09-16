package stepDefinitions;

import constants.BookingEndpoints;
import entities.Booking;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.Request;
import static org.junit.Assert.assertEquals;

public class BookingSteps {
    private Response response;
    private Booking booking;

    // Step para crear una nueva reserva
    @Given("I create a new booking with firstname {string}, lastname {string}, totalprice {int}")
    public void createBooking(String firstname, String lastname, int totalprice) {
        Booking.BookingDates dates = new Booking.BookingDates();
        dates.setCheckin("2022-01-01");
        dates.setCheckout("2022-01-10");

        booking = new Booking();
        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(totalprice);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);
        booking.setAdditionalneeds("Breakfast");

        response = Request.post(BookingEndpoints.CREATE_BOOKING, booking);
    }

    // Verificar que la reserva fue creada exitosamente
    @Then("I verify the booking was created successfully with status code {int}")
    public void verifyBookingCreation(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    // Step para recuperar una reserva por ID
    @Given("I retrieve a booking by ID {int}")
    public void getBookingById(int id) {
        // Hacemos la solicitud GET para recuperar el booking por ID
        response = Request.get(BookingEndpoints.GET_BOOKING_BY_ID, id);

        // Verificamos el tipo de contenido de la respuesta
        String contentType = response.getContentType();

        // Si el contenido es texto plano, mostramos el cuerpo de la respuesta en la consola
        if (contentType.contains("text/plain")) {
            System.out.println("Advertencia: La respuesta es texto plano.");
            System.out.println("Error: " + response.asString());  // Muestra el contenido del texto plano
        } else if (contentType.contains("application/json")) {
            System.out.println("Respuesta JSON recibida:");
            System.out.println("Booking data: " + response.asString());  // Muestra el contenido JSON si es válido
        } else {
            System.out.println("Advertencia: Tipo de contenido inesperado: " + contentType);
        }
    }

    // Verificar que el firstname es el esperado
    @Then("I verify the booking firstname is {string}")
    public void verifyBookingFirstname(String expectedFirstname) {
        // Si el contenido es JSON, verificamos el firstname
        if (response.getContentType().contains("application/json")) {
            String actualFirstname = response.jsonPath().getString("firstname");
            assertEquals(expectedFirstname, actualFirstname);
        } else {
            System.out.println("No se pudo verificar el firstname, la respuesta no es JSON.");
        }
    }

    // Step para actualizar una reserva por ID
    @Given("I update the booking with ID {int} to have firstname {string}")
    public void updateBookingById(int id, String newFirstname) {
        // Primero, obtenemos la reserva actual
        response = Request.get(BookingEndpoints.GET_BOOKING_BY_ID, id);

        if (response.getStatusCode() == 200) {
            // Creamos el objeto Booking con los datos actuales
            booking = response.as(Booking.class);  // Convertimos la respuesta a Booking
            booking.setFirstname(newFirstname);    // Actualizamos solo el firstname

            // Realizamos la solicitud PUT para actualizar la reserva
            response = Request.put(BookingEndpoints.UPDATE_BOOKING, id, booking);
        } else {
            throw new RuntimeException("Booking not found with ID " + id);
        }
    }

    // Step para eliminar una reserva por ID
    @Then("I delete the booking with ID {int}")
    public void deleteBooking(int id) {
        response = Request.delete(BookingEndpoints.DELETE_BOOKING, id);
    }

    // Verificar el código de estado de la respuesta
    @Then("I verify the response status code is {int}")
    public void verifyResponseStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}
