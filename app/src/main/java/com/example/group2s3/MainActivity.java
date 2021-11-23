package com.example.group2s3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2s3.AppsViewmodel.VehicleViewModel;
import com.example.group2s3.RVAdapter.VehicleRVAdapter;
import com.example.group2s3.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private VehicleViewModel vehicleViewModel;
    Button addVehicleBtn;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Vehicle Detail");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("S3T2");

        addVehicleBtn = findViewById(R.id.btn_add_note);


        RecyclerView recyclerView = findViewById(R.id.vehicle_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final VehicleRVAdapter vehicleRVAdapter = new VehicleRVAdapter();
        recyclerView.setAdapter(vehicleRVAdapter);

        //VehicleDao vehicleDao = VehicleDatabase.getInstance(getApplicationContext()).vehicleDao();
        vehicleViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(VehicleViewModel.class);

        vehicleViewModel.getAllVehicle().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                vehicleRVAdapter.setVehicleList(vehicles,vehicleViewModel);
            }
        });

        addVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String invin = binding.etVIN.getText().toString().trim();
                String inPlatno = binding.etPlateNo.getText().toString().trim();
                String inComp = binding.etCompany.getText().toString().trim();
                String inModel = binding.etModel.getText().toString().trim();
                String inType = binding.etType.getText().toString().trim();
                String inStatecode = "";
                String inDistcode = "";
                String inCodePlate = "";
                int indSpace = -1, ind2Space = -1, ind3Space = -1;
                if (!inPlatno.equals("")) {
                    indSpace = inPlatno.indexOf(" ", 0);
                    if (indSpace != -1) {
                        inStatecode = inPlatno.substring(0, indSpace).trim();

                        ind2Space = inPlatno.indexOf(" ", indSpace + 1);
                        if (ind2Space != -1) {
                            inDistcode = inPlatno.substring(indSpace + 1, ind2Space).trim();
                            ind3Space = inPlatno.indexOf(" ", ind2Space + 1);
                            if (ind3Space != -1) {
                                inCodePlate = inPlatno.substring(ind2Space + 1).trim();
                            }else {
                                Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Enter Valid Info!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!invin.equals("")  && !inDistcode.equals("") && !inStatecode.equals("") && !inComp.equals("") && !inModel.equals("") && !inType.equals("")) {

                    Vehicle newVehicle = new Vehicle(inComp, inModel, inType, inStatecode, inDistcode, inCodePlate, invin);
                    vehicleViewModel.insert(newVehicle);

                    binding.etVIN.setText("");
                    binding.etPlateNo.setText("");
                    binding.etCompany.setText("");
                    binding.etModel.setText("");
                    binding.etType.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Info!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}