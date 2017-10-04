package bookscom.diegocastro.ebaybooks.model;

import android.graphics.Bitmap;

/**
 * Created by diego.castro on 10/3/17.
 */
public class Book {
    private String title;
    private String author;
    private String imageURL;

    private Bitmap bitmap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
