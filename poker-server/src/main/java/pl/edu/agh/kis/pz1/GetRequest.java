package pl.edu.agh.kis.pz1;

public class GetRequest extends Request {
    final private int[] params;
    final private String type;
    final private String action;
    final private int clientId;

    public GetRequest(String request) {
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
