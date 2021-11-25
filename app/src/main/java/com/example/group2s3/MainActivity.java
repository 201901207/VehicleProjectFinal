package com.example.group2s3;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    int ID_VEHICLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ID_VEHICLE = -1;
        setTitle("Vehicle Detail");
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("S3T2");

        SharedPreferences sharedPreferences = getSharedPreferences("is_first_time", MODE_PRIVATE);
        if (1 == (sharedPreferences.getInt("is_first_time", 1))) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("is_first_time", 0);
            editor.apply();

            alertShow();
        }


        RecyclerView recyclerView = findViewById(R.id.vehicle_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final VehicleRVAdapter vehicleRVAdapter = new VehicleRVAdapter();
        recyclerView.setAdapter(vehicleRVAdapter);

        //VehicleDao vehicleDao = VehicleDatabase.getInstance(getApplicationContext()).vehicleDao();
        vehicleViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(VehicleViewModel.class);

        vehicleViewModel.getAllVehicle().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                vehicleRVAdapter.setVehicleList(vehicles);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Vehicle vehicle = vehicleRVAdapter.getVehicle(viewHolder.getAdapterPosition());

                if (ID_VEHICLE == -1) {

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Are You Sure?")
                            .setMessage("Press DELETE to continue")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    vehicleViewModel.delete(vehicle);
                                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            vehicleRVAdapter.notifyDataSetChanged();

                            Toast.makeText(MainActivity.this, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }).setCancelable(false).show();
                } else {
                    vehicleRVAdapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Finish Updation First", Toast.LENGTH_SHORT).show();
                }

            }
        }).attachToRecyclerView(recyclerView);

        vehicleRVAdapter.setonVehicleItemClickListener(new VehicleRVAdapter.onVehicleItemClickListener() {
            @Override
            public void onVehicleClick(Vehicle vehicle) {
                binding.etVIN.setText(vehicle.getVIN());
                binding.etCompany.setText(vehicle.getCompany());
                String str = vehicle.getPlat_statCode() + " " + vehicle.getPlat_distCode() + " " + vehicle.getPlat_codeNum();
                binding.etPlateNo.setText(str);
                binding.etModel.setText(vehicle.getVehicle_model());
                binding.etType.setText(vehicle.getVehicle_type());
                ID_VEHICLE = vehicle.getId_vehicle();

                binding.btnAddNote.setVisibility(View.INVISIBLE);
                binding.btnCancel.setVisibility(View.VISIBLE);
                binding.btnChange.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "You can Change it Now", Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etVIN.setText("");
                binding.etCompany.setText("");
                binding.etPlateNo.setText("");
                binding.etModel.setText("");
                binding.etType.setText("");

                ID_VEHICLE = -1;

                binding.btnAddNote.setVisibility(View.VISIBLE);
                binding.btnCancel.setVisibility(View.INVISIBLE);
                binding.btnChange.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(), "Updating Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
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
                            } else {
                                Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Enter Valid Info!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!invin.equals("") && !inDistcode.equals("") && !inStatecode.equals("") && !inComp.equals("") && !inModel.equals("") && !inType.equals("")) {

                    Vehicle newVehicle = new Vehicle(ID_VEHICLE, inComp, inModel, inType, inStatecode, inDistcode, inCodePlate, invin);
                    vehicleViewModel.update(newVehicle);

                    Toast.makeText(MainActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();

                    ID_VEHICLE = -1;

                    binding.etVIN.setText("");
                    binding.etPlateNo.setText("");
                    binding.etCompany.setText("");
                    binding.etModel.setText("");
                    binding.etType.setText("");

                    binding.btnAddNote.setVisibility(View.VISIBLE);
                    binding.btnCancel.setVisibility(View.INVISIBLE);
                    binding.btnChange.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Info!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        addVehicleBtn = findViewById(R.id.btn_add_note);

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
                            } else {
                                Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Enter Valid Plate No!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Enter Valid Info!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!invin.equals("") && !inDistcode.equals("") && !inStatecode.equals("") && !inComp.equals("") && !inModel.equals("") && !inType.equals("")) {

                    Vehicle newVehicle = new Vehicle(inComp, inModel, inType, inStatecode, inDistcode, inCodePlate, invin);
                    vehicleViewModel.insert(newVehicle);

                    Toast.makeText(MainActivity.this, "ADDED Successfully", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void alertShow() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete and Update Info")
                .setMessage("To Delete swipe the Vehicle card left or right.\nTo Update click on Vehicle card.")
                .setNeutralButton("OKAY", null)
                .setIcon(R.drawable.ic_info_red)
                .setCancelable(false)
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.id_info) {
            alertShow();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }
}