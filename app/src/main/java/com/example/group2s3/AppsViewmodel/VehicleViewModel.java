package com.example.group2s3.AppsViewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.group2s3.Repository.VehicleRepository;
import com.example.group2s3.Vehicle;

import java.util.List;

public class VehicleViewModel extends AndroidViewModel {

    private VehicleRepository vehicleRepository;
    private LiveData<List<Vehicle>> allVehicle;

    public VehicleViewModel(@NonNull Application application) {
        super(application);

        vehicleRepository = new VehicleRepository(application);
        allVehicle = vehicleRepository.getAllVehicle();
    }

    public LiveData<List<Vehicle>> getAllVehicle() {
        return allVehicle;
    }

    public void insert(Vehicle vehicle){
        vehicleRepository.insert(vehicle);
    }

    public void delete(Vehicle vehicle){
        vehicleRepository.delete(vehicle);
    }

    public void update(Vehicle vehicle){
        vehicleRepository.update(vehicle);
    }
}
