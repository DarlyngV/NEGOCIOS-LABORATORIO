package com.hellohasan.sqlite_project.Features.UpdatePlantaInfo;

import com.hellohasan.sqlite_project.Features.CreatePlanta.Planta;

public interface PlantaUpdateListener {
    void onPlantaInfoUpdated(Planta planta, int position);
}
