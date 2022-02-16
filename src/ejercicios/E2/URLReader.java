package src.ejercicios.E2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class URLReader {
    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            throw new UnsupportedOperationException("No se especificó el parámetro de una URL, ejemplo: http://www.google.com/");
        }

        // Especifica la ruta donde se va a guardar el html
        String htmlPageRoute = "src/ejercicios/E2/resources/";
        // Crea el objeto que representa una URL
        String urlArgument = args[0];
        URL url = new URL(urlArgument);
        String filename = "resultado";
        filename = filename.replace(".", "");

        try {
            // Crea el objeto que URLConnection
            URLConnection urlConnection = url.openConnection();
            // Obtiene los campos del encabezado y los almacena en un estructura Map
            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            // Obtiene una vista del mapa como conjunto de pares <K,V> para poder navegarlo
            Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();

            System.out.println("-------headers------");
            // Recorre la lista de campos e imprime los valores
            for (Map.Entry<String, List<String>> entry : entrySet) {
                String headerName = entry.getKey();
                //Si el nombre es nulo, significa que es la linea de estado
                if (headerName != null) {
                    System.out.print(headerName + ":");
                }
                List<String> headerValues = entry.getValue();
                for (String value : headerValues) {
                    System.out.print(value);
                }
                System.out.println("");
            }
            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            String fullFileName = htmlPageRoute + filename + ".html";
            PrintWriter writer = new PrintWriter(fullFileName, "UTF-8");
            while ((inputLine = reader.readLine()) != null) {
                writer.println(inputLine);
            }
            writer.close();

            System.out.println("El path del HTML es: " + fullFileName);

        } catch (IOException x){
            System.err.println(x);
        }
    }
}
