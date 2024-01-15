package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;

import java.util.ArrayList;

public class SearchedAccommodationListAdapter extends ArrayAdapter<SearchAccommodationDTO> {
    private ArrayList<SearchAccommodationDTO> accommodations;
    private FragmentActivity activity;

    public SearchedAccommodationListAdapter(Context context, ArrayList<SearchAccommodationDTO> accommodations, FragmentActivity activity){
        super(context, R.layout.accommodation_card, accommodations);
        this.accommodations = accommodations;
        this.activity = activity;

    }
    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Nullable
    @Override
    public SearchAccommodationDTO getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationDTO accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }
        Log.e("ZIV SAM", "Ziv sammmm");
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);

        if(accommodation != null){
            //imageView.setImageResource(product.getImage());
            productTitle.setText(accommodation.getName());
            productDescription.setText(accommodation.getDescription());
            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + accommodation.getName() + ", id: " +
                        accommodation.getId());
                Toast.makeText(getContext(), "Clicked: " + accommodation.getName()  +
                        ", id: " + accommodation.getId(), Toast.LENGTH_SHORT).show();
                FragmentTransition.to( AccommodationFragment.newInstance(accommodation), activity, false, R.id.base_accommodations);


            });
        }

        return convertView;
    }
}
