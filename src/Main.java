import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI address = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(address).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        System.out.println("\u001b[1mTop 10 Filmes\n");
        for (int  i = 0; i < 10; i++) {
            Map<String,String> filme = listaDeFilmes.get(i) ;
            System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mUrl da imagem:\u001b[m " + filme.get("image"));
            double classification = Double.parseDouble(filme.get("imDbRating"));
            int starsNumbers = (int) classification;

            System.out.print("\u001b[1mAvaliação: ");
            for (int n = 1; n <= starsNumbers; n++) {
                System.out.print("⭐");
            }
            System.out.println("\n");
        }
    }
}