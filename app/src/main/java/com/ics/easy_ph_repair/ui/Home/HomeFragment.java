package com.ics.easy_ph_repair.ui.Home;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.ics.easy_ph_repair.R;
import com.ics.easy_ph_repair.Session.SessionManager;
import com.ics.easy_ph_repair.ui.MyServiceFragment.ServiceFragment;
import com.ics.easy_ph_repair.ui.NewJobFrgment.NewJobFragment;
import com.ics.easy_ph_repair.ui.ServiceApproval.ServiceApprovalFragment;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{
    CardView addnewjb,ser_status,collect_unity,sercomp;
    SessionManager sessionManager;
    Fragment RemoveHome;
    TextView nameof,numberof;
    private SliderLayout mDemoSlider;
    public NavController navController;
    ArrayList<String> listUrl = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
   // private HomeViewModel homeViewModel;
    List<Integer> DrawintegerList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
            //For slider
        sessionManager = new SessionManager(getActivity());
        mDemoSlider = root.findViewById(R.id.slider);
        nameof = root.findViewById(R.id.nameof);
        numberof = root.findViewById(R.id.numberof);
        sercomp = root.findViewById(R.id.sercomp);


        DrawintegerList.add(R.drawable.mobile_reslide);
        DrawintegerList.add(R.drawable.anotherslide2);
        nameof.setText(sessionManager.getUsername() );
        numberof.setText(sessionManager.getMobile());
        listUrl.add("https://www.revive-adserver.com/media/GitHub.jpg");
        listName.add("JPG - Github");

        listUrl.add("https://tctechcrunch2011.files.wordpress.com/2017/02/android-studio-logo.png");
        listName.add("PNG - Android Studio");

        listUrl.add("http://static.tumblr.com/7650edd3fb8f7f2287d79a67b5fec211/3mg2skq/3bdn278j2/tumblr_static_idk_what.gif");
        listName.add("GIF - Disney");

        listUrl.add("http://www.gstatic.com/webp/gallery/1.webp");
        listName.add("WEBP - Mountain");


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        for (int i = 0; i < DrawintegerList.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getActivity());
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout

            sliderView
                    .image(DrawintegerList.get(i))
                    .description(listName.get(i)).setOnSliderClickListener(this);
//            sliderView
//                    .image(listUrl.get(i))
//                    .description(listName.get(i))
//                    .setRequestOption(requestOptions)
//                    .setProgressBarVisible(true)
//                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

//
        addnewjb = root.findViewById(R.id.addnewjb);
        ser_status = root.findViewById(R.id.ser_status);
        collect_unity = root.findViewById(R.id.collect_unity);
        collect_unity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent = new Intent("Hey_Clicked");
                if(!sessionManager.isLoggedIn()) {

                    intent.putExtra("Clicked", "NotLoggedin");

                }else {
                    intent.putExtra("Clicked", "collect_unity");
                }
                // put your all data using put extra
                DrawintegerList.clear();
                listName.clear();
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        sercomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent = new Intent("Hey_Clicked");
                if(!sessionManager.isLoggedIn()) {

                    intent.putExtra("Clicked", "NotLoggedin");

                }else {
                    intent.putExtra("Clicked", "Service_Complete");
                }
                // put your all data using put extra
                DrawintegerList.clear();
                listName.clear();
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        ser_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent   intent = new Intent("Hey_Clicked");
                if(!sessionManager.isLoggedIn()) {

                    intent.putExtra("Clicked", "NotLoggedin");

                }else {
                    intent.putExtra("Clicked", "Service_Status");
                }
                // put your all data using put extra
                DrawintegerList.clear();
                listName.clear();
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        addnewjb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //closefragment();
                Intent   intent = new Intent("Hey_Clicked");
                if(!sessionManager.isLoggedIn()) {

                    intent.putExtra("Clicked", "NotLoggedin");

                }else {
                    intent.putExtra("Clicked", "AddNewJob");
                }
                // put your all data using put extra
                DrawintegerList.clear();
                listName.clear();
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

//    private void closefragment() {
//
//    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
        Toast.makeText(getActivity(), baseSliderView.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}