package com.example.collegeapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.collegeapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;


public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1);

        setSliderViews();

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Deenbandhu Chottu Ram University Of Science And Technology Murthal");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setSliderViews() {

        for(int i= 0; i<5; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());


              switch (i){
                  case 0:
                      sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/Sliderviewpictures%2FIMG-20230808-WA0005.jpg?alt=media&token=6a969662-d82f-434c-8b80-9f78ffa030b5");
                      break;

                  case 1:
                      sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/Sliderviewpictures%2FIMG-20230705-WA0008.jpg?alt=media&token=0cd5a299-36af-40e1-9f37-95a164adcdbb");
                      break;

                  case 2:
                      sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/Sliderviewpictures%2FIMG-20230517-WA0000.jpg?alt=media&token=6ce07f6b-e455-4b5d-9978-75a4cf04dfc5");
                      break;

                  case 3:
                      sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/Sliderviewpictures%2FIMG-20230505-WA0000.jpg?alt=media&token=5b3a828e-aa56-4555-b429-089922f62fc6");
                      break;

                  case 4:
                      sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/Sliderviewpictures%2FIMG-20230423-WA0001.jpg?alt=media&token=797c6bd3-aa88-4e20-8751-df0473585d97");
                      break;

              }
              sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

              sliderLayout.addSliderView(sliderView);
        }
    }
}