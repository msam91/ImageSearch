package com.codepath.gridimagesearch.modules.Fragments;


import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.modules.models.Filters;

/**
 * Created by msamant on 10/17/15.
 */
public class FilterFragment extends DialogFragment {
    private Button mSubmitButton;
    private Button mResetButton;
    private Spinner mSpinner_1;
    private Spinner mSpinner_2;
    private Spinner mSpinner_3;
    private EditText mFilterSite;
    private Filters mfiltersSubmit;



    public interface SubmitFiltersListener {
        void onFinishEditDialog(Filters filters);
    }


    public FilterFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterFragment newInstance(String title) {
        FilterFragment frag = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        setUpViews(view);
    }

    private void setUpViews(View view){
        mSubmitButton = (Button) view.findViewById(R.id.btnSubmit);
        mResetButton = (Button) view.findViewById(R.id.btnReset);
        mSpinner_1 = (Spinner)view.findViewById(R.id.spinner_1);
        mSpinner_2 = (Spinner)view.findViewById(R.id.spinner_2);
        mSpinner_3 = (Spinner)view.findViewById(R.id.spinner_3);
        mFilterSite = (EditText)view.findViewById(R.id.etSiteFilter);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpinner_1.getSelectedItem()!=null && mSpinner_2.getSelectedItem()!=null && mSpinner_3.getSelectedItem()!=null) {
                    mfiltersSubmit = new Filters();
                    mfiltersSubmit.setImageSize(mSpinner_1.getSelectedItem().toString());
                    mfiltersSubmit.setImageColor(mSpinner_2.getSelectedItem().toString());
                    mfiltersSubmit.setImageType(mSpinner_3.getSelectedItem().toString());
                    mfiltersSubmit.setFilterSite(mFilterSite.getText().toString());
                }
                SubmitFiltersListener listener = (SubmitFiltersListener) getActivity();
                listener.onFinishEditDialog(mfiltersSubmit);
                dismiss();
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitFiltersListener listener = (SubmitFiltersListener) getActivity();
                listener.onFinishEditDialog(null);
                dismiss();
            }
        });
    }

}
