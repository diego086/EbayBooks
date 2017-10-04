package bookscom.diegocastro.ebaybooks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bookscom.diegocastro.ebaybooks.R;
import bookscom.diegocastro.ebaybooks.model.Book;

/**
 * Created by diego.castro on 10/3/17.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;

    public BookAdapter(Context context,List<Book> bookList) {
        super(context, 0, bookList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_book, null);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView author = (TextView) view.findViewById(R.id.author);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Book book = getItem(position);

        title.setText(book.getTitle());
        if(book.getAuthor() != null){
            author.setText(book.getAuthor());
        } else {
            author.setText("");
        }
        imageView.setImageBitmap(book.getBitmap());

        return view;

    }
}
