package com.zen.agency.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.zen.agency.room.Agency;
import com.zen.agency.room.AgencyDao;
import com.zen.agency.room.AgencyDatabase;

import java.util.List;

public class AgencyRepository {
    private AgencyDao agencyDao;
    private LiveData<List<Agency>> allAgency;
    private LiveData<List<Agency>> searchAgency;

    public AgencyRepository(Application application) {
        AgencyDatabase database = AgencyDatabase.getInstance(application);
        agencyDao = database.agencyDao();
        allAgency = agencyDao.getAllAgency();
    }

    public void insert(Agency agency) {
        new InsertNoteAsyncTask(agencyDao).execute(agency);
    }

    public void update(Agency agency) {
        new UpdateNoteAsyncTask(agencyDao).execute(agency);
    }

    public void delete(Agency agency) {
        new DeleteNoteAsyncTask(agencyDao).execute(agency);

    }

    public void deleteAllAgency() {
        new DeleteAllNotesAsyncTask(agencyDao).execute();
    }

    public LiveData<List<Agency>> searchAllAgency(Double minSurface, Double maxSurface,String type,Double minPrice,Double maxPrice,Double minNum,Double maxNum,String address) {
        return agencyDao.getAllAgency(minSurface,maxSurface,type,minPrice,maxPrice,minNum,maxNum,address);
    }

    public LiveData<List<Agency>> getAllAgency() {
        return allAgency;
    }
    public LiveData<List<Agency>> getAllAgency(String userId) {
        return agencyDao.getAllAgency(userId);
    }
    public LiveData<List<Agency>> getAll(String userId,Double minSurface, Double maxSurface,String type,Double minPrice,Double maxPrice,Double minNum,Double maxNum,String address) {
        StringBuilder sql=new StringBuilder("SELECT *  FROM agency_table where userId="+userId+" ");
        if(minSurface<maxSurface){
            sql.append("and surface  between "+minSurface+" and "+maxSurface+" ");
        }
        if(minPrice<maxPrice){
            sql.append("and price  between "+minPrice+" and "+maxPrice+" ");
        }
        if(minNum<maxNum){
            sql.append("and roomNum  between "+minNum+" and "+maxNum+" ");
        }
        if(!TextUtils.isEmpty(address)){
            sql.append("and address  like '%"+address+"%' ");
        }
        if(!TextUtils.isEmpty(type)){
            sql.append("and type = '"+type+"' ");
        }
        String finalSql=sql.toString();
        Log.e("mysql",finalSql);
        return agencyDao.getAll(new SimpleSQLiteQuery(finalSql+""));
    }



    public static class InsertNoteAsyncTask extends AsyncTask<Agency, Void, Void> {
        private AgencyDao agencyDao;
        private InsertNoteAsyncTask(AgencyDao noteDao) {
            this.agencyDao = noteDao;
        }

        @Override
        protected Void doInBackground(Agency... agencies) {
            agencyDao.insert(agencies[0]);
            return null;
        }
    }


    public static class UpdateNoteAsyncTask extends AsyncTask<Agency, Void, Void> {
        private AgencyDao agencyDao;
        private UpdateNoteAsyncTask(AgencyDao agencyDao) {
            this.agencyDao = agencyDao;
        }

        @Override
        protected Void doInBackground(Agency... notes) {
            agencyDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Agency, Void, Void> {
        private AgencyDao agencyDao;

        private DeleteNoteAsyncTask(AgencyDao agencyDao) {
            this.agencyDao = agencyDao;
        }

        @Override
        protected Void doInBackground(Agency... notes) {
            agencyDao.delete(notes[0]);
            return null;
        }


    }

    public static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private AgencyDao agencyDao;

        private DeleteAllNotesAsyncTask(AgencyDao agencyDao) {
            this.agencyDao = agencyDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            agencyDao.deleteAllNotes();
            return null;
        }
    }

}