package com.zen.agency.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.zen.agency.repository.AgencyRepository;
import com.zen.agency.room.Agency;

import java.util.List;

public class AgencyViewModel extends AndroidViewModel {
    private AgencyRepository repository;
    private LiveData<List<Agency>> allAgency;

    public AgencyViewModel(@NonNull Application application) {
        super(application);
        repository = new AgencyRepository(application);
        allAgency = repository.getAllAgency();
    }

    public void insert(Agency agency) {
        repository.insert(agency);
    }

    public void update(Agency agency) {
        repository.update(agency);
    }

    public void delete(Agency agency) {
        repository.delete(agency);
    }

    public void deleteAllNote() {
        repository.deleteAllAgency();
    }

    public LiveData<List<Agency>> getAllAgency() {
        if (allAgency == null) {
            allAgency = new MutableLiveData<List<Agency>>();
        }
        return allAgency;
    }

    public LiveData<List<Agency>> getAllAgency(String userId) {
        if (allAgency == null) {
            allAgency = new MutableLiveData<List<Agency>>();
        }
        return repository.getAllAgency(userId);
    }

    public LiveData<List<Agency>> searchAllAgency(String userId,Double minSurface, Double maxSurface,String type,Double minPrice,Double maxPrice,Double minNum,Double maxNum,String address) {
        return repository.getAll(userId,minSurface,maxSurface,type,minPrice,maxPrice,minNum,maxNum,address);
    }
}
