package Labyrinth;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import javax.imageio.ImageIO;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.InputFile;

public class ReaderFromResoucre {
	private String originalName;

	public ReaderFromResoucre(String name) {
		this.originalName = name;
		System.out.println("[READING] " + this.originalName);
	}

	private InputStream getInputStream() throws NoSuchFileException {
		String name = String.format("/%s", this.originalName);
		InputStream is = ReaderFromResoucre.class.getResourceAsStream(name);
		if (is == null) {
			System.out.println(String.format("[ERROR] with %s", this.originalName));
			throw new NoSuchFileException(name);
		}
		return is;
	}

	public String getString() throws IOException {
		return new String(getInputStream().readAllBytes(), "UTF-8");
	}

	public JSONObject getJsonObject() throws IOException {
		return new JSONObject(getString());
	}

	public BufferedImage getBufferedImage() {
		BufferedImage bf = null;
		try (InputStream is = getInputStream()) {
			bf = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bf;
	}

	public InputFile getAnimation() {
		InputFile res = null;
		try {
			InputStream is = getInputStream();
			res = new InputFile(is, this.originalName);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("[ERROR] with %s reading", this.originalName));
		}
		return res;
	}
}
