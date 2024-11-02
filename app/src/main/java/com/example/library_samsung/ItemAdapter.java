package com.example.library_samsung;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import com.bumptech.glide.Glide;

public class ItemAdapter extends ArrayAdapter<Book> {
    public ItemAdapter(@NonNull Context context, List<Book> books) {
        super(context, R.layout.activity_item_adapter, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            //add convertView=
            convertView= LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_item_adapter, null);

        final Book book = getItem(position);

        ((TextView) convertView.findViewById(R.id.tvTitle))
                .setText(book.getTitle());

        ((TextView) convertView.findViewById(R.id.tvAuthor))
                .setText(book.getAuthor());

        ImageView imageView = convertView.findViewById(R.id.imageView);
        // imageView.setImageURI(Uri.parse(person.image));

        try {
            Glide.with(imageView.getContext())
                    .load(Uri.parse(book.getCover()))
                    .placeholder(R.drawable.ic_launcher_image)
                    .error(R.drawable.ic_launcher_image)
                    .circleCrop()
                    .into(imageView);
        } catch (Exception e) {
        }
        return convertView;
    }
}