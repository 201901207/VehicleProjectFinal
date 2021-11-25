package com.example.group2s3.RVAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group2s3.AppsViewmodel.VehicleViewModel;
import com.example.group2s3.MainActivity;
import com.example.group2s3.R;
import com.example.group2s3.Vehicle;
import com.example.group2s3.VehicleDao;
import com.example.group2s3.VehicleDatabase;

import java.util.ArrayList;
import java.util.List;

public class VehicleRVAdapter extends RecyclerView.Adapter<VehicleRVAdapter.VehicleViewHolder> {

    private List<Vehicle> vehicleList = new ArrayList<>();
    private onVehicleItemClickListener onVehicleItemClickListener;
//    public VehicleRVAdapter(List<Vehicle> vehicleList) {
//        this.vehicleList = vehicleList;
//    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
        notifyDataSetChanged();
    }

    public Vehicle getVehicle(int position) {
        return vehicleList.get(position);
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false);
        return new VehicleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicleCurrent = vehicleList.get(position);
        holder.textViewVIN.setText(vehicleCurrent.getVIN());
        holder.textViewCompany.setText(vehicleCurrent.getCompany());
        String plateNumber = vehicleCurrent.getPlat_statCode() + " " + vehicleCurrent.getPlat_distCode() + " " + vehicleCurrent.getPlat_codeNum();
        holder.textViewPlateNo.setText(plateNumber);
        holder.textViewType.setText(vehicleCurrent.getVehicle_type());
        holder.textViewModel.setText(vehicleCurrent.getVehicle_model());

    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewVIN;
        private TextView textViewPlateNo;
        private TextView textViewCompany;
        private TextView textViewType;
        private TextView textViewModel;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewVIN = itemView.findViewById(R.id.tv_vin);
            textViewCompany = itemView.findViewById(R.id.tv_companyname);
            textViewModel = itemView.findViewById(R.id.tv_model);
            textViewPlateNo = itemView.findViewById(R.id.tv_platnum);
            textViewType = itemView.findViewById(R.id.tv_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onVehicleItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onVehicleItemClickListener.onVehicleClick(vehicleList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface onVehicleItemClickListener {
        void onVehicleClick(Vehicle vehicle);
    }

    public void setonVehicleItemClickListener(onVehicleItemClickListener onVehicleItemClickListener) {
        this.onVehicleItemClickListener =onVehicleItemClickListener;
    }
}
