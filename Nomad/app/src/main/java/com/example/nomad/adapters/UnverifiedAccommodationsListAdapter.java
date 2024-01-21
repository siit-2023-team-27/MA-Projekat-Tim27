package com.example.nomad.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.services.AccomodationsService;

import java.util.ArrayList;

public class UnverifiedAccommodationsListAdapter extends ArrayAdapter<AccommodationDTO> {

    private ArrayList<AccommodationDTO> accommodations;

    private FragmentActivity activity;

    private AccomodationsService accomodationsService = new AccomodationsService();


    public UnverifiedAccommodationsListAdapter(@NonNull Context context, ArrayList<AccommodationDTO> accommodations, FragmentActivity activity) {
        super(context, R.layout.unverified_accommodation_card, accommodations);
        this.accommodations = accommodations;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Nullable
    @Override
    public AccommodationDTO getItem(int position) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.unverified_accommodation_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.unverified_card);
        ImageView imageView = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.accommodation_title);
        TextView address = convertView.findViewById(R.id.accommodation_address);

        if(accommodation != null){
            //imageView.setImageResource(product.getImage());
            title.setText(accommodation.getName());
            address.setText(accommodation.getAddress());

            Button acceptButton = convertView.findViewById(R.id.button_accept);
            Button declineButton = convertView.findViewById(R.id.button_decline);

            // Set click listeners for the buttons
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CLICK", "Klik na Accept dugme. " + accommodation.getId());
                    accomodationsService.verifyAccommodation(accommodation.getId());
                    Toast.makeText(getContext(), "Accommodation is accepted", Toast.LENGTH_SHORT).show();
                    accommodations.remove(accommodation);
                    notifyDataSetChanged();
                }
            });

            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("CLICK", "Klik na Decline dugme. " + accommodation.getId());
                    accomodationsService.declineAccommodation(accommodation.getId());
                    Toast.makeText(getContext(), "Accommodation is declined", Toast.LENGTH_SHORT).show();
                    accommodations.remove(accommodation);
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }
}
