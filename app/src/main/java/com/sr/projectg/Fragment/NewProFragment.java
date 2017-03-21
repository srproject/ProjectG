package com.sr.projectg.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sr.projectg.R;
import com.sr.projectg.activity.EditprofileActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NewProFragment extends Fragment {

    TextView editprofiletext;
    ImageView imageprofile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().setTitle("Team B");


        View rootView = inflater.inflate(R.layout.new_profile_layout, container, false);
//typecode

        editprofiletext = (TextView) rootView.findViewById(R.id.editprofiletext);

        editprofiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditprofileActivity.class);
                startActivity(intent);

            }
        });

        imageprofile = (ImageView) rootView.findViewById(R.id.imageprofile);


//ENDcode
        return rootView;
    }

    private void loadImageFromStorage() {
        String appname = getString(R.string.app_name);


        try {

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "/" + appname + "/Image/profile_image.png");
            if (f.exists()) {

                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                imageprofile.setImageBitmap(b);
            } else {


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //END//load Image From Storage


    @Override
    public void onResume() {
        super.onResume();

        loadImageFromStorage();


    }


}
