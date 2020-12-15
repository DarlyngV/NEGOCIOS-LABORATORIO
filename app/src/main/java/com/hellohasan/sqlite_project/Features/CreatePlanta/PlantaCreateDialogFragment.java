package com.hellohasan.sqlite_project.Features.CreatePlanta;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hellohasan.sqlite_project.Util.Config;
import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.R;


public class PlantaCreateDialogFragment extends DialogFragment {

    private static PlantaCreateListener plantaCreateListener;

    private EditText nameEditText;
    private EditText stateEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private char state = 'A';


    public PlantaCreateDialogFragment() {
        // Required empty public constructor
    }

    public static PlantaCreateDialogFragment newInstance(String title, PlantaCreateListener listener){
        plantaCreateListener = listener;
        PlantaCreateDialogFragment plantaCreateDialogFragment = new PlantaCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        plantaCreateDialogFragment.setArguments(args);

        plantaCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return plantaCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_planta_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.plantaNameEditText);
        stateEditText = view.findViewById(R.id.stateEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                Planta planta = new Planta(-1, nameString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertPlanta(planta);

                if(id>0){
                    planta.setId((int) id);
                    plantaCreateListener.onPlantaCreated(planta);
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
