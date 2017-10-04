package bookscom.diegocastro.ebaybooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import bookscom.diegocastro.ebaybooks.adapter.BookAdapter;
import bookscom.diegocastro.ebaybooks.model.Book;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private TextView msg;

    public static final String SERVER_URL = "http://de-coding-test.s3.amazonaws.com/books.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        msg = (TextView) findViewById(R.id.msgLegend);
        context = this;
        new JsonFetch().execute(SERVER_URL);

    }

    private class JsonFetch extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                return getBookList(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }

        private List<Book> getBookList(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String temp = "";
            List<Book> bookList = new LinkedList<>();
            try {
                while ((temp = reader.readLine()) != null) {
                    stringBuffer.append(temp);
                }

                JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Book book = new Book();

                    if(!jsonObject.isNull("title")){
                        book.setTitle(jsonObject.getString("title"));
                    } else {
                        book.setTitle("");
                    }

                    if(!jsonObject.isNull("author")){
                        book.setAuthor(jsonObject.getString("author"));
                    } else {
                        book.setAuthor("");
                    }

                    if(!jsonObject.isNull("imageURL")){
                        book.setImageURL(jsonObject.getString("imageURL"));
                        //TODO HANDLE IF THERE IS AN ERROR GETTING THE IMAGE. URL INVALID FOR EXAMPLE
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(book.getImageURL()).getContent());
                        book.setBitmap(bitmap);
                    } else {
                        book.setImageURL("");
                    }


                    bookList.add(book);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return bookList;
        }

        @Override
        protected void onPostExecute(List<Book> bookList) {
            super.onPostExecute(bookList);
            if (bookList == null) {
                msg.setText("There is an error on fetching the books. Please try again later");

            } else {
                listView.setAdapter(new BookAdapter(context, bookList));
            }
        }
    }
}
