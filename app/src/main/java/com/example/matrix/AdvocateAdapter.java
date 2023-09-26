package com.example.matrix;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.squareup.picasso.Picasso;

public class AdvocateAdapter extends ArrayAdapter<Advocate> {
    private Context context;
    private List<Advocate> advocates;

    public AdvocateAdapter(Context context, List<Advocate> advocates) {
        super(context, 0, advocates);
        this.context = context;
        this.advocates = advocates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Advocate advocate = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_advocate_item, parent, false);
        }

        // Lookup view elements within item layout
        TextView nameTextView = convertView.findViewById(R.id.advocate_name);
        TextView loactionTextView = convertView.findViewById(R.id.advlocation);
        TextView amountTextView = convertView.findViewById(R.id.amount);
        TextView experienceTextView = convertView.findViewById(R.id.AdvExperience);
        TextView languageTextView = convertView.findViewById(R.id.Advlanguage);
        TextView practiceAreasTextView = convertView.findViewById(R.id.practice_contents);
        ImageView imageView = convertView.findViewById(R.id.adv_image_imageview);
        // Populate the data into the template view using the data object
        nameTextView.setText(advocate.getName());
        loactionTextView.setText(advocate.getLocation());
        amountTextView.setText(advocate.getAmount());
        languageTextView.setText(advocate.getLanguage());
        experienceTextView.setText(advocate.getExperience()+" of Experience");
        practiceAreasTextView.setText(advocate.getPracticeAreas());
        if (advocate.getImageUrl() != null && !advocate.getImageUrl().isEmpty()) {
            Picasso.get().load(advocate.getImageUrl()).into(imageView);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}