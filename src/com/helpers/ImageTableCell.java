package com.helpers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageTableCell<T> extends TableCell<T, InputStream>{

    private final ImageView imageView = new ImageView();

    @Override
    protected void updateItem(InputStream item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                item.reset();
                BufferedImage bufferedImage = ImageIO.read(item);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setGraphic(imageView);
        }
        this.setItem(item);
    }

}
