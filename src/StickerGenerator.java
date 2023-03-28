import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.FontMetrics;
import java.awt.font.TextLayout;

import javax.imageio.ImageIO;
public class StickerGenerator {

    public <Shap> void create(InputStream inputStream, String fileName) throws Exception {
        // InputStream inputStream = new FileInputStream(new File("entrada/movie.jpg"));
        // InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_9.jpg").openStream();
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra novo imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // configurar a fonte
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 100);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // escrever uma frase na nova imagem
        String text = "Filme:";
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rectangle = fontMetrics.getStringBounds(text, graphics);
        int textWidth = (int) rectangle.getWidth();
        int positionTextX = (largura - textWidth) / 2;
        int positionTextY = novaAltura - 100;
        graphics.drawString(text, positionTextX, positionTextY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(text, fonte, fontRenderContext);
        Shap outline = (Shap) textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(positionTextX, positionTextY);
        graphics.setTransform(transform);
        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw((Shape) outline);
        graphics.setClip((Shape) outline);


        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File("figure", fileName));
    }
}
