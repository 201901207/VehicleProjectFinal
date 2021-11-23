package com.example.group2s3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Vehicle.class},version = 1)
public abstract class VehicleDatabase extends RoomDatabase {

    //To make this class singleton and use the same instance everywhere
    private static VehicleDatabase instance;
    public abstract VehicleDao vehicleDao();

    //To make sure that only one thread can access it at a time
    public static synchronized VehicleDatabase getInstance(Context context){
        // Instance will be generated only if its not null
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    VehicleDatabase.class,"vehicle_DB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateVehicleDB(instance).execute();
        }
    };

    private static class PopulateVehicleDB extends AsyncTask<Void,Void,Void>{
        private VehicleDao vehicleDao;

        private PopulateVehicleDB(VehicleDatabase vehicleDatabase) {
            this.vehicleDao = vehicleDatabase.vehicleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //insert initial 1 time data here
            vehicleDao.insert(new Vehicle("Honda","City","Hatchback","GJ","11","AS 8821","12JND568FH92DF"));
            vehicleDao.insert(new Vehicle("Maruti Suzuki","Wagoner","Minivan","GJ","05","HH 4455","122J5ND56482DE"));
            vehicleDao.insert(new Vehicle("Skoda","Octavia","Hatchback","GJ","03","AA 9875","12JND789JH92GF"));
            return null;
        }
    }
}
