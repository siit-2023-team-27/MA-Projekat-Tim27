package com.example.nomad.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.nomad.R;
import com.example.nomad.activities.SliderAdapter;
import com.example.nomad.activities.SliderData;
import com.example.nomad.dto.AccommodationDTO;
import com.example.nomad.services.ImageService;
import com.example.nomad.services.LocationService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageFragment extends Fragment {

    private Button addImageButton;
    private Button saveButton;
    private Button removeImageButton;
    private ImageService imageService = new ImageService();
    private ViewFlipper imageFlipper;
    private FloatingActionButton buttonForward;
    private FloatingActionButton buttonBack;
    private ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
    private HashMap<String, Bitmap> images = new HashMap<>();
    private ArrayList<String> imageOrder = new ArrayList<>();

    private AccommodationDTO accommodation;
    public ImageFragment() {
        // Required empty public constructor
    }

    public AccommodationDTO getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(AccommodationDTO accommodation) {
        this.accommodation = accommodation;
    }

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addImageButton = view.findViewById(R.id.addImageButton);
        removeImageButton = view.findViewById(R.id.removeImageButton);
        saveButton = view.findViewById(R.id.saveButton);

        removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.remove(imageOrder.get(imageFlipper.getDisplayedChild()));
                imageOrder.remove(imageFlipper.getDisplayedChild());
                imageFlipper.removeView(imageFlipper.getCurrentView());
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImages();
            }
        });
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        imageFlipper = view.findViewById(R.id.imageFlipper);
        buttonForward = view.findViewById(R.id.buttonForward);
        buttonBack = view.findViewById(R.id.buttonPrevious);
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFlipper.showNext();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFlipper.showPrevious();
            }
        });
        // Add image resources to the array list.

//        sliderDataArrayList.add(new SliderData(R.drawable.img1));
//        sliderDataArrayList.add(new SliderData(R.drawable.img2));
//        sliderDataArrayList.add(new SliderData(R.drawable.img3));
//        sliderDataArrayList.add(new SliderData(R.drawable.img4));
//
//        // Create and set the adapter for the slider view.
//        SliderAdapter adapter = new SliderAdapter(requireContext(), sliderDataArrayList);
//        imageFlipper.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//        imageFlipper.setSliderAdapter(adapter);
//        imageFlipper.setScrollTimeInSec(3);
//        imageFlipper.setAutoCycle(true);
//        imageFlipper.startAutoCycle();

    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getActivity().getContentResolver(),
                                    selectedImageUri);
                            String name = String.valueOf(Time.from(Instant.now()).getTime());
                            images.put(name, selectedImageBitmap);
                            imageOrder.add(name);

                            ImageView image = new ImageView ( getActivity().getApplicationContext() );
                            image.setImageBitmap( selectedImageBitmap );
                            imageFlipper.addView( image );
                            imageFlipper.showNext();

                            //                            imageFlipper.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//                            imageFlipper.setSliderAdapter(adapter);


                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");
        OutputStream os;
        try {
            imageFile.createNewFile();
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }
    private void saveImages(){
        for (String key : images.keySet()){
            saveImage(key, images.get(key));
        }
        AccommodationLocationFragment locationFragment = AccommodationLocationFragment.newInstance(false, "t");
        locationFragment.setAccommodation(accommodation);
        FragmentTransition.to(locationFragment, getActivity(), true, R.id.accommodationCreationHostView);
    }
    private void saveImage(String key, Bitmap selectedImageBitmap){
        File file = persistImage(selectedImageBitmap, key);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("images", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        imageService.upload(filePart);
    }
}