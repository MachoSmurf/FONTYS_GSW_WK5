package aex.server;


import javax.xml.ws.Endpoint;

public class Server {

    public static void main(String[] args) {
        //printIPAddresses();
        //new Server();
        Endpoint.publish("http://localhost:8080/Fondsen", new MockEffectenBeurs());
    }
}