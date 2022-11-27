package pl.edu.agh.kis.pz1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostRequestTest {
    @Test
    public void testPostRequestType() {
        PostRequest request = new PostRequest("POST CHECK 0");
        assertEquals("POST", request.getType());
    }

    @Test
    public void testPostRequestAction() {
        PostRequest request = new PostRequest("POST CHECK 0");
        assertEquals("CHECK", request.getAction());
    }

    @Test
    public void testPostRequestClientId() {
        PostRequest request = new PostRequest("POST CHECK 0");
        assertEquals(0, request.getClientId());
    }

    @Test
    public void testPostRequestParams() {
        PostRequest request = new PostRequest("POST CHECK 0 1 2 3");
        assertEquals(1, request.getParams()[0]);
        assertEquals(2, request.getParams()[1]);
        assertEquals(3, request.getParams()[2]);
    }
}
