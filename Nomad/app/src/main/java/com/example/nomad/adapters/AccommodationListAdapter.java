package com.example.nomad.adapters;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.nomad.R;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.fragments.FragmentTransition;
import com.example.nomad.fragments.accommodations.AccommodationFragment;
import com.example.nomad.fragments.accommodations.AccommodationListFragment;

import java.util.ArrayList;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationDTO> {
    private ArrayList<AccommodationDTO> accommodations;
    private FragmentActivity activity;

    public AccommodationListAdapter(Context context, ArrayList<AccommodationDTO> accommodations, FragmentActivity activity){
        super(context, R.layout.accommodation_card, accommodations);
        this.accommodations = accommodations;
        this.activity = activity;

    }
    /*
     * Ova metoda vraca ukupan broj elemenata u listi koje treba prikazati
     * */
    @Override
    public int getCount() {
        return accommodations.size();
    }

    /*
     * Ova metoda vraca pojedinacan element na osnovu pozicije
     * */
    @Nullable
    @Override
    public AccommodationDTO getItem(int position) {
        return accommodations.get(position);
    }

    /*
     * Ova metoda vraca jedinstveni identifikator, za adaptere koji prikazuju
     * listu ili niz, pozicija je dovoljno dobra. Naravno mozemo iskoristiti i
     * jedinstveni identifikator objekta, ako on postoji.
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * Ova metoda popunjava pojedinacan element ListView-a podacima.
     * Ako adapter cuva listu od n elemenata, adapter ce u petlji ici
     * onoliko puta koliko getCount() vrati. Prilikom svake iteracije
     * uzece java objekat sa odredjene poziciuje (model) koji cuva podatke,
     * i layout koji treba da prikaze te podatke (view) npr R.layout.product_card.
     * Kada adapter ima model i view, prosto ce uzeti podatke iz modela,
     * popuniti view podacima i poslati listview da prikaze, i nastavice
     * sledecu iteraciju.
     * */
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