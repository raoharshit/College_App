package com.example.collegeapp.ui.home;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.example.collegeapp.ui.about.BranchAdapter;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private ImageView map;
    private Uri uri;
    private ViewPager viewPager;
    private DepartmentAdapter departmentAdapter;
    private List<DepartmentModel> list;
    private ImageView contactView,mailView;
    private CircleImageView linkedinView,twitterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        contactView = view.findViewById(R.id.phone);
        mailView = view.findViewById(R.id.gmail);
        linkedinView = view.findViewById(R.id.linkedin);
        twitterView = view.findViewById(R.id.twitter);

        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getString(R.string.contact_no)));
                startActivity(intent);
            }
        });

        mailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.mail_link));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        linkedinView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.linkedin_link));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        twitterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.twitter_link));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        list = new ArrayList<>();


        list.add(new DepartmentModel(R.drawable.ic_comp, getResources().getString(R.string.cse)));
        list.add(new DepartmentModel(R.drawable.ic_ece, getResources().getString(R.string.ece)));
        list.add(new DepartmentModel(R.drawable.ic_elec, getResources().getString(R.string.elec)));
        list.add(new DepartmentModel(R.drawable.ic_mech, getResources().getString(R.string.mech)));
        list.add(new DepartmentModel(R.drawable.ic_civil, getResources().getString(R.string.civil)));
        list.add(new DepartmentModel(R.drawable.ic_biomed, getResources().getString(R.string.biomed)));
        list.add(new DepartmentModel(R.drawable.ic_chemical, getResources().getString(R.string.chemical)));
        list.add(new DepartmentModel(R.drawable.ic_archi, getResources().getString(R.string.archi)));

        list.add(new DepartmentModel(R.drawable.ic_chemistry, getResources().getString(R.string.chem)));
        list.add(new DepartmentModel(R.drawable.ic_physics, getResources().getString(R.string.physics)));

        list.add(new DepartmentModel(R.drawable.ic_maths, getResources().getString(R.string.maths)));
        list.add(new DepartmentModel(R.drawable.ic_biotech, getResources().getString(R.string.biotech)));
        list.add(new DepartmentModel(R.drawable.ic_environment, getResources().getString(R.string.cease)));
        list.add(new DepartmentModel(R.drawable.ic_management, getResources().getString(R.string.management)));
        list.add(new DepartmentModel(R.drawable.ic_management, getResources().getString(R.string.humanities)));

        // adapter
        departmentAdapter = new DepartmentAdapter(getContext(), list);
        // viewpager initialisation
        viewPager = view.findViewById(R.id.viewpager);

        //setting adapter in viewpager
        viewPager.setAdapter(departmentAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2);

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

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Dashboard");
    }
}