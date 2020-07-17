package com.example.freebooks;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Book> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.reciclador);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new BookAdapter(items);
        recyclerView.setAdapter(adapter);

        getBooks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_book_bottom:
                mostrarDialogo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogo() {
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialog_add, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(inflator);

        final EditText title = (EditText) inflator.findViewById(R.id.title_new);
        final EditText author = (EditText) inflator.findViewById(R.id.author_new);
        final EditText synopsis = (EditText) inflator.findViewById(R.id.synopsis_new);
        final EditText year = (EditText) inflator.findViewById(R.id.year_new);

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendBook(new Book(title.getText().toString(), author.getText().toString(), synopsis.getText().toString(), year.getText().toString()));
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }

    public void sendBook(final Book newBook) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.3:3000/api/v1/books?title=" + newBook.getTitle() + "&synopsis=" + newBook.getSynopsis()
                + "&year=" + newBook.getYear() + "&author=" + newBook.getAuthor();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        items.add(newBook);
                        adapter.notifyDataSetChanged();
                        showMessage(newBook.getTitle() + " se agrego correctamente.");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    showMessage("DATA: Response Failed");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();
                return  params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String,String>();
                headers.put("Content-Type", "application/json");
                return  headers;
            }
        };
        queue.add(stringRequest);
    }

    public void getBooks() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.3:3000/api/v1/books";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Book>>(){}.getType();
                List<Book> itemsAux = gson.fromJson(response, listType);
                for (Book item:
                        itemsAux) {
                    System.err.println(item.getId() + " " + item.getAuthor() + " " +item.getSynopsis());
                    items.add(item);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private void showMessage(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
