package Labyrinth.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Labyrinth.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class TelegramBot extends TelegramLongPollingBot {

	private App app;

	public TelegramBot(App app) {
		this.app = app;
	}

	@Override
	public String getBotUsername() {
		return "example_demobot";
	}

	@Override
	public String getBotToken() {
		ClassLoader classLoader = getClass().getClassLoader();
		File fileToken = new File(classLoader.getResource("tgToken.txt").getFile());
		try {
			InputStream is = new FileInputStream(fileToken);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String token;
				token = br.readLine();
				if (token != null) {
					return token;
				}
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chatId = update.getMessage().getChatId();
			String input = update.getMessage().getText();
			// crate text massege
			SendMessage message = new SendMessage();
			message.enableHtml(true);
			message.setChatId(String.valueOf(chatId));
			message.setText(app.moveAndGetMapHTML(input));
			// end of text massege

			// create simple image
			BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = image.createGraphics();
			g2d.setPaint(Color.RED);

			ClassLoader classLoader = getClass().getClassLoader();
			File fileTile = new File(classLoader.getResource("Sprite.png").getFile());
			try {
				Image tile = ImageIO.read(fileTile);
				g2d.drawImage(tile, 0, 0, 16, 16, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2d.dispose();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "png", outputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			byte[] imageData = outputStream.toByteArray();
			SendPhoto sendPhoto = new SendPhoto();
			String id = update.getMessage().getChatId().toString();
			sendPhoto.setChatId(id);
			sendPhoto.setPhoto(new InputFile(new ByteArrayInputStream(imageData), "image.png"));
			// end of image
			try {
				execute(message);
				execute(sendPhoto);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}
}
