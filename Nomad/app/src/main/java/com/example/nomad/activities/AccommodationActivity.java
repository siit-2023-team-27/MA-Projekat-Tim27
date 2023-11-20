package com.example.nomad.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nomad.R;
import java.util.ArrayList;
import com.smarteist.autoimageslider.SliderView;
public class AccommodationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        // we are creating array list for storing our image urls.
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

        // initializing the slider view.
        SliderView sliderView = findViewById(R.id.slider);

        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(R.drawable.img1));
        sliderDataArrayList.add(new SliderData(R.drawable.img2));
        sliderDataArrayList.add(new SliderData(R.drawable.img3));
        sliderDataArrayList.add(new SliderData(R.drawable.img4));

        //set rating
        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.ratingBar); // initiate a rating bar
        simpleRatingBar.setRating((float) 4.5);

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        LinearLayout layout = (LinearLayout) findViewById(R.id.image_container);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(this);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setChecked(true);

            // Disable the checkbox to prevent user interaction
            checkBox.setEnabled(false);

            // Make the checkbox non-clickable
            checkBox.setClickable(false);
            textView.setPadding(60, 0, 60, 0);
            textView.setText("Internet");
            textView.setLayoutParams(layoutParams);
            checkBox.setLayoutParams(layoutParams);
            layout.addView(checkBox);
            layout.addView(textView);

        }

        LinearLayout layoutComments = (LinearLayout) findViewById(R.id.comments_container);
        LinearLayout.LayoutParams layoutParamsComments = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams pictureLayoutParams = new LinearLayout.LayoutParams(100, 100);


        for (int i = 0; i <2; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.user);
            imageView.setLayoutParams(pictureLayoutParams);

            TextView textView = new TextView(this);
            textView.setPadding(20, 0, 0, 0);
            textView.setText("Jane Doe");
            textView.setLayoutParams(layoutParamsComments);

            linearLayout.setPadding(0, 40, 0, 0);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);

            TextView description = new TextView(this);
            description.setPadding(0, 20, 0, 0);
            description.setText("Well... we were not very happy with this place..");
            description.setLayoutParams(layoutParamsComments);

            layoutComments.addView(linearLayout);
            layoutComments.addView(description);
        }
    }
}