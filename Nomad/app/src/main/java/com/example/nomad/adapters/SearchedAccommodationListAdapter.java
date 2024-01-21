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
import androidx.lifecycle.Observer;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.dto.SearchAccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.services.AccommodationService;
import com.example.nomad.services.AccomodationsService;
import com.example.nomad.services.AuthService;

import java.util.ArrayList;

public class SearchedAccommodationListAdapter extends ArrayAdapter<SearchAccommodationDTO> {
    private ArrayList<SearchAccommodationDTO> accommodations;
    private FragmentActivity activity;
    AuthService authService = new AuthService();
    AccomodationsService accomodationsService = new AccomodationsService();


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
        SearchAccommodationDTO accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_accommodation_card,
                    parent, false);
        }
        Log.e("ZIV SAM", "Ziv sammmm");
        LinearLayout productCard = convertView.findViewById(R.id.product_card_item);
        ImageView imageView = convertView.findViewById(R.id.product_image);
        TextView productTitle = convertView.findViewById(R.id.product_title);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        TextView totalPrice = convertView.findViewById(R.id.total_price);
        TextView pricePerNight = convertView.findViewById(R.id.price_per_night);
        TextView average = convertView.findViewById(R.id.average_rating);


        if(accommodation != null){

            //imageView.setImageResource(product.getImage());
            productTitle.setText(accommodation.getName());
            productDescription.setText(accommodation.getDescription());
            totalPrice.setText("Total price: "+accommodation.getTotalPrice().toString());
            pricePerNight.setText("Price per night: "+accommodation.getPricePerNight().toString());
            average.setText("Average rating:"+String.valueOf(accommodation.getAverageRating()));

            if(authService.getRole().getAuthority().equals("GUEST")){
                LinearLayout layout = convertView.findViewById(R.id.favourite_button);
                Button favorite = new Button(activity);
                favorite.setText("Like/dislike");
                layout.addView(favorite);
                favorite.setOnClickListener(v -> {
                    // Handle click on the item at 'position'
                    accomodationsService.likeDislike(authService.getId(), accommodation.getId());
                    accomodationsService.getLikedDisliked().observe(activity, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean objects) {
                            // Update your UI or perform any actions when LiveData changes
                            Boolean isLiked =  objects;
                            Log.i("Response like", "Selected: " + isLiked.toString());

                            if(isLiked){
                                Toast.makeText(getContext(), "Accommodation added to favourites", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Accommodation removed from favourites", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                });
            }

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + accommodation.getName() + ", id: " +
                        accommodation.getId());
                Toast.makeText(getContext(), "Clicked: " + accommodation.getName()  +
                        ", id: " + accommodation.getId(), Toast.LENGTH_SHORT).show();
                FragmentTransition.to( AccommodationFragment.newInstance(accommodation), activity, true, R.id.base_accommodations);
            });
        }

        return convertView;
    }
}
