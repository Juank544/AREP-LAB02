package src.retos.R1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class WebServer {
    private static final HashMap<String,String> contentType =new HashMap<String,String>();
    public static void main(String[] args) throws IOException {
//        out.close();
//        in.close();
//         clientSocket.close();

    }

    public void starServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean on = true;
        while (on){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        serverSocket.close();
    }

    public void serverConection(Socket clientSocket) throws IOException, URISyntaxException {
        OutputStream outStream = clientSocket.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;

        ArrayList<String> request = new ArrayList<>();
        String sv="";

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }

        String uriContentType="";
        String uri="";
        try {

            uriContentType=request.get(0).split(" ")[1];

            URI resource = new URI(uriContentType);

            uri=resource.getPath().split("/")[1];
        }catch(Exception e){
            System.out.println(e);
        }
        outputLine = getResource( uri, outStream);
        out.println(outputLine);
        out.close();
        in.close();
        clientSocket.close();
    }

    public String getResource(String uri, OutputStream outStream) throws URISyntaxException{
        if (uri.contains("jpg") || uri.contains("jpeg")){
            return null;
        }else {
            return null;
        }
    }

    private String defaultResponse(){
        String outputLine = "HTTP/1.1 200 OK\r\n"
                +"Content-Type: text/html\r\n"
                +"\r\n"
                +"<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n"
                + "</head>"
                + "<body>"
                + "My Web Site"
                + "</body>"
                + "</html>";
        return outputLine;
    }
}
