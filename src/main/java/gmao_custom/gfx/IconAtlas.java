package gmao_custom.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconAtlas {

	private static final HashMap<String, BufferedImage> ATLAS = new HashMap<>();

	public static final void registerAllIcons() {
		registerIcon("edit");
		registerIcon("add");
	}

	private static final void registerIcon(String key) {
		try {
			BufferedImage img = ImageIO.read(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("gmao_custom/icons/" + key + ".png"));
			if (img != null) {
				ATLAS.put(key, img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final ImageIcon getImageIcon(String key, int size) {
		return createImageIcon(ATLAS.get(key), size, size);
	}

	public static final ImageIcon createImageIcon(BufferedImage img, int width, int height) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		result.getGraphics().drawImage(img, 0, 0, width, height, null);
		return new ImageIcon(result);
	}

}