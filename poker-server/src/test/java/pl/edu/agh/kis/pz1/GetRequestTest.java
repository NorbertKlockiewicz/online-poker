package pl.edu.agh.kis.pz1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {
    public static void main(String[] args) {
        GetRequest request = new GetRequest("GET PLAYER 0");
        System.out.println(request);
    }

    @Test
    public void testGetRequestType() {
        GetRequest request = new GetRequest("GET PLAYER 0");
        assertEquals("GET", request.getType());
    }

    @Test
    public void testGetRequestAction() {
        GetRequest request = new GetRequest("GET GET_ROOMS 0");
        assertEquals("GET_ROOMS", request.getAction());
    }

    @Test
    public void testGetRequestClientId() {
        GetRequest request = new GetRequest("GET GET_ROOMS 0");
        assertEquals(0, request.getClientId());
    }

    @Test
    public void testGetRequestParams() {
        GetRequest request = new GetRequest("GET GET_ROOMS 0 1 2 3");
        assertEquals(1, request.getParams()[0]);
        assertEquals(2, request.getParams()[1]);
        assertEquals(3, request.getParams()[2]);
    }
}
