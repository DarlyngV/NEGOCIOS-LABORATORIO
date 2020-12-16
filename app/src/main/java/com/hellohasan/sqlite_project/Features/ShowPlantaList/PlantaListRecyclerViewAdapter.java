package com.hellohasan.sqlite_project.Features.ShowPlantaList;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hellohasan.sqlite_project.Database.DatabaseQueryClass;
import com.hellohasan.sqlite_project.Features.CreatePlanta.Planta;
import com.hellohasan.sqlite_project.Features.UpdatePlantaInfo.PlantaUpdateDialogFragment;
import com.hellohasan.sqlite_project.Features.UpdatePlantaInfo.PlantaUpdateListener;
import com.hellohasan.sqlite_project.R;
import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class PlantaListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Planta> plantaList;
    private DatabaseQueryClass databaseQueryClass;

    public PlantaListRecyclerViewAdapter(Context context, List<Planta> plantaList) {
        this.context = context;
        this.plantaList = plantaList;
        databaseQueryClass = new DatabaseQueryClass(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.planta_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Planta planta = plantaList.get(position);

        holder.nameTextView.setText(planta.getName());
        holder.stateTextView.setText(planta.getState());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this planta?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deletePlanta(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlantaUpdateDialogFragment plantaUpdateDialogFragment = PlantaUpdateDialogFragment.newInstance(planta.getName(), itemPosition, new PlantaUpdateListener() {
                    @Override
                    public void onPlantaInfoUpdated(Planta planta, int position) {
                        plantaList.set(position, planta);
                        notifyDataSetChanged();
                    }
                });
                plantaUpdateDialogFragment.show(((StateListActivity) context).getSupportFragmentManager(), Config.UPDATE_PLANTA);
            }
        });
    }

    private void deletePlanta(int position) {
        Planta planta = plantaList.get(position);
        long count = databaseQueryClass.deletePlantaByRegNum(planta.getState());

        if(count>0){
            plantaList.remove(position);
            notifyDataSetChanged();
            ((StateListActivity) context).viewVisibility();
            Toast.makeText(context, "Planta deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Planta not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return plantaList.size();
    }
}
