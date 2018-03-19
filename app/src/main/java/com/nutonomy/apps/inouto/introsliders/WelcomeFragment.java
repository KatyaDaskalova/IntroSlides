package com.nutonomy.apps.inouto.introsliders;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class WelcomeFragment extends Fragment {
    public static final String key = "data";
    private String myData;
    private TextView mTextView;
    private ImageView mImageView;

    private String mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static WelcomeFragment create(String data) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putString(key, data);
        fragment.setArguments(args);
        return fragment;
    }

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getString(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_welcome2, container, false);

        mTextView = rootView.findViewById(R.id.textView3);
        mImageView = rootView.findViewById(R.id.imageView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myData = bundle.getString(key, "");
            //    Toast.makeText(this.getContext(), myData, Toast.LENGTH_LONG).show();
        }


        if (myData != null) {
            try {

                JSONObject jsonObj = new JSONObject(myData);

                // Getting JSON Array node
                JSONArray screen = jsonObj.getJSONArray("screen");

                // looping through screen
                for (int i = 0; i < screen.length(); i++) {
                    JSONObject c = screen.getJSONObject(i);
                    String message = c.getString("message");
                    String resource2 = c.getString("resource");

                    //     Log.e(TAG, "screen message : FFFFFFFFFFFFFFFFFFFFFFFFFFF     " + message );

                    if (!message.isEmpty())
                        mTextView.setText(message);
                    else
                        mTextView.setLines(3);

                    int resourceImage = getContext().getResources().getIdentifier(resource2, "drawable", getContext().getPackageName());
                    mImageView.setImageResource(resourceImage);


                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't parse json.");
        }

        return rootView;
    }

}
