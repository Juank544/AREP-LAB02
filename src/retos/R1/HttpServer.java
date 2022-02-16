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

public class HttpServer {
    private static final HttpServer _instance = new HttpServer();
    private static final HashMap<String,String> contentType = new HashMap<String,String>();

    private HttpServer() {
    }

    public static void main(String[] args) throws IOException {
        HttpServer.getInstance().starServer();
    }

    public static HttpServer getInstance(){
        contentType.put("html","text/html");
        contentType.put("css","text/css");
        contentType.put("js","text/javascript");

        contentType.put("jpg","image/jpg");
        contentType.put("jpeg","image/jpeg");
        contentType.put("png","image/png");
        return _instance;
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
            request.add(inputLine);
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
            return computeImageResponse(uri, outStream);
        }else {
            return computeContentResponse(uri);
        }
    }

    public String computeImageResponse(String uriImgType, OutputStream outStream){
        uriImgType=uriImgType.replace("/img","");

        String extensionUri = uriImgType.substring(uriImgType.lastIndexOf(".") + 1);

        String content = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: "+ contentType.get(extensionUri) + "\r\n"
                + "\r\n";
        System.out.println("uriImgType " + uriImgType);
        File file = new File("src/retos/R1/resources/img/"+uriImgType);
        System.out.println("file "+file);
        try {
            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            DataOutputStream dataOutputStream= new DataOutputStream(outStream);
            ImageIO.write(bi, extensionUri, byteArrayOutputStream);
            dataOutputStream.writeBytes(content);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String computeContentResponse(String uriContentType){
        String extensionUri = uriContentType.substring(uriContentType.lastIndexOf(".") + 1);
        String content = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: "+ contentType.get(extensionUri) + "\r\n"
                + "\r\n";
        File file = new File("src/retos/R1/resources/"+uriContentType);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line =  br.readLine()) != null) content += line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
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
