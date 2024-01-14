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
import com.example.nomad.services.AccomodationsService;

import java.util.ArrayList;

public class HostAccommodationsAdapter extends ArrayAdapter<AccommodationDTO> {

    private ArrayList<AccommodationDTO> accommodations;

    private FragmentActivity activity;

    private AccomodationsService accomodationsService = new AccomodationsService();

    public HostAccommodationsAdapter(@NonNull Context context, ArrayList<AccommodationDTO> accommodations, FragmentActivity activity) {
        super(context, R.layout.host_accommodation_card, accommodations);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.host_accommodation_card,
                    parent, false);
        }
        LinearLayout card = convertView.findViewById(R.id.accommodation_card);
        ImageView imageView = convertView.findViewById(R.id.accommodation_image);
        TextView title = convertView.findViewById(R.id.accommodation_title);
        TextView address = convertView.findViewById(R.id.accommodation_address);
        TextView status = convertView.findViewById(R.id.accommodation_status);

        Button editButton = convertView.findViewById(R.id.button_edit);

        if (accommodation != null) {
            title.setText(accommodation.getName());
            address.setText(accommodation.getAddress());
            status.setText(accommodation.getStatus().toString());
        }



        return convertView;
    }

}
