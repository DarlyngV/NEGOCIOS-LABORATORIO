package com.hellohasan.sqlite_project.Features.UpdatePlantaInfo;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.CreatePlanta.Planta;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;


public class PlantaUpdateDialogFragment extends DialogFragment {

    private static long plantaRegNo;
    private static int plantaItemPosition;
    private static PlantaUpdateListener plantaUpdateListener;

    private Planta mPlanta;

    private EditText nameEditText;
    private EditText stateEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private String state="A";

    private DatabaseQueryClass databaseQueryClass;

    public PlantaUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static PlantaUpdateDialogFragment newInstance(String state, int position, PlantaUpdateListener listener){
        state = state;
        plantaItemPosition = position;
        plantaUpdateListener = listener;
        PlantaUpdateDialogFragment plantaUpdateDialogFragment = new PlantaUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update planta information");
        plantaUpdateDialogFragment.setArguments(args);

        plantaUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return plantaUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planta_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.plantaNameEditText);
       stateEditText = view.findViewById(R.id.stateEditText);
        updateButton = view.findViewById(R.id.updatePlantaInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mPlanta = databaseQueryClass.getPlantaState(state);

        if(mPlanta!=null) {
            nameEditText.setText(mPlanta.getName());
            stateEditText.setText(mPlanta.getState());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    state = (stateEditText.getText().toString());

                    mPlanta.setName(nameString);
                    mPlanta.setState(state);

                    long id = databaseQueryClass.updatePlantaInfo(mPlanta);

                    if(id>0){
                        plantaUpdateListener.onPlantaInfoUpdated(mPlanta, plantaItemPosition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
