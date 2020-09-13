package com.solt.jdc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageManager {
	public static File createDirectory() {
		File path = new File(System.getProperty("user.home").concat("/MyApp/data/bin/bookstore"));
		if(!path.exists()) {
			path.mkdirs();
		}
		return path;
	}
	 
	public static void saveImage(ImageView view, File file) {
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(), null), "png", file);
		} catch (IOException e) {
			throw new BookStoreException("Save Image Error!");
		}
	}
	
	public static Image getImage(String imgName) {
		File file = new File(createDirectory(), imgName);
		try (FileInputStream fis = new FileInputStream(file)){
			Image img = new Image(fis);
			return img;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ImageView getImageView(String name) {
		ImageView iView = new ImageView();
		iView.setFitHeight(50);
		iView.setFitWidth(50);
		
		iView.setImage(getImage(name));
		return iView;
	}
}
