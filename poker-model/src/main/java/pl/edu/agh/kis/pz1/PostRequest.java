package pl.edu.agh.kis.pz1;

public class PostRequest extends Request {
    private int[] params;
    private String type;
    private String action;
    private int clientId;

    public PostRequest(String request) {
        String[] requestParts = request.split(" ");
        type = requestParts[0];
        action = requestParts[1];
        clientId = Integer.parseInt(requestParts[2]);
        params = new int[requestParts.length - 3];
        for (int i = 3; i < requestParts.length; i++) {
            params[i - 3] = Integer.parseInt(requestParts[i]);
        }
    }

    public int[] getParams() {
        return params;
    }

    public String getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public int getClientId() {
        return clientId;
    }
}
