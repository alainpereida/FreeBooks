package com.example.freebooks;

import android.content.Intent;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private List<Book> items;

    public BookAdapter(List<Book> items) { this.items = items; }

    public class BookHolder extends  RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView author;
        public TextView synopsis;
        public TextView year;
        public ImageButton shared;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            synopsis = itemView.findViewById(R.id.synopsis);
            synopsis.setMovementMethod(new ScrollingMovementMethod());
            year = itemView.findViewById(R.id.year);
            shared = itemView.findViewById(R.id.shared);
        }
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_renglon, parent, false);
        return new BookHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.author.setText(items.get(position).getAuthor());
        holder.synopsis.setText(items.get(position).getSynopsis());
        holder.year.setText(items.get(position).getYear());
        holder.shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "El Libro es " + items.get(position).getTitle() +
                        " del año " + items.get(position).getYear());
                v.getContext().startActivity(Intent.createChooser(intent, "Share with"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
