package pl.edu.agh.kis.pz1;

abstract class Request {
    private int[] params;
    private String type;
    private String action;
    private int clientId;
    public abstract int[] getParams();
    public abstract String getType();
    public abstract String getAction();
    public abstract int getClientId();
}
