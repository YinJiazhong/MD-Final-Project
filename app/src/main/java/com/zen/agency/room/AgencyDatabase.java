package com.zen.agency.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zen.agency.util.DateConverter;

@Database(entities = {Agency.class,User.class}, version = 8, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AgencyDatabase extends RoomDatabase {
    private static AgencyDatabase instance;

    public abstract AgencyDao agencyDao();

    public abstract UserDao userDao();

    public static synchronized AgencyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AgencyDatabase.class, "agency_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populatedDbAsyncTask(instance).execute();
        }
    };

    private static class populatedDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AgencyDao agencyDao;
        private UserDao userDao;

        private populatedDbAsyncTask(AgencyDatabase db) {
            agencyDao = db.agencyDao();
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
