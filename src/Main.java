import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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

        var directory = new File("figure/");
        directory.mkdir();

        // exibir e manipular os dados
        System.out.println("\u001b[1mTop 10 Filmes\n");
        var generating = new StickerGenerator();
        for (int i = 0; i < 10; i++) {
            Map<String, String> filme = listaDeFilmes.get(i);

            String urlImage = filme.get("image");
            String title = filme.get("title");

            InputStream inputStream = new URL(urlImage).openStream();
            String fileName = directory + title + ".png";

            generating.create(inputStream, fileName);

            System.out.println("\u001b[1mTítulo:\u001b[m " + title);

            double classification = Double.parseDouble(filme.get("imDbRating"));
            int starsNumbers = (int) classification;

            System.out.print("\u001b[1mAvaliação: ");
            for (int n = 1; n <= starsNumbers; n++) {
                System.out.print("⭐");

            }
            System.out.print("\n");
            if (starsNumbers >= 8) {
                System.out.print("\u001b[1mRecomendação:\u001b[m Muito bom");
            }

            if (starsNumbers >= 5 && starsNumbers <= 7) {
                System.out.print("\u001b[1mRecomendação:\u001b[m Bom");
            }

            if (starsNumbers <= 4) {
                System.out.print("\u001b[1mRecomendação:\u001b[m Ruim");
            }
            System.out.println("\n");
        }
    }
}
