package com.example.collegeapp.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);


        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_comp,getString(R.string.cse),getResources().getString(R.string.about_cse)));
        list.add(new BranchModel(R.drawable.ic_ece,getString(R.string.ece),getResources().getString(R.string.about_ece)));
        list.add(new BranchModel(R.drawable.ic_elec,getString(R.string.elec),getResources().getString(R.string.about_elec)));
        list.add(new BranchModel(R.drawable.ic_mech,getString(R.string.mech),getResources().getString(R.string.about_mech)));
        list.add(new BranchModel(R.drawable.ic_civil,getString(R.string.civil),getResources().getString(R.string.about_civil)));
        list.add(new BranchModel(R.drawable.ic_biomed,getString(R.string.biomed),getResources().getString(R.string.about_biomed)));
        list.add(new BranchModel(R.drawable.ic_chemical,getString(R.string.chemical),getResources().getString(R.string.about_chemical)));
        list.add(new BranchModel(R.drawable.ic_archi,getString(R.string.archi),getResources().getString(R.string.about_archi)));
        list.add(new BranchModel(R.drawable.ic_chemistry,getString(R.string.chem),getResources().getString(R.string.about_chemistry)));
        list.add(new BranchModel(R.drawable.ic_physics,getString(R.string.physics),getResources().getString(R.string.about_physics)));
        list.add(new BranchModel(R.drawable.ic_maths,getString(R.string.maths),getResources().getString(R.string.about_maths)));
        list.add(new BranchModel(R.drawable.ic_biotech,getString(R.string.biotech),getResources().getString(R.string.about_biotech)));
        list.add(new BranchModel(R.drawable.ic_environment,getString(R.string.cease),getResources().getString(R.string.about_ceees)));
        list.add(new BranchModel(R.drawable.ic_management,getString(R.string.management),getResources().getString(R.string.about_management)));
        list.add(new BranchModel(R.drawable.ic_management,getString(R.string.humanities),getResources().getString(R.string.about_humanities)));

        adapter = new BranchAdapter(getContext(),list);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));


        ImageView imageView = view.findViewById(R.id.college_image);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/my-college-app-32d40.appspot.com/o/college_pictures%2Fdcrust_image.jpg?alt=media&token=82c5fed5-62e0-4266-afa7-1634fe01efce").into(imageView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("About Us");
    }
}