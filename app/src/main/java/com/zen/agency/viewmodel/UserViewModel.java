package com.zen.agency.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zen.agency.repository.AgencyRepository;
import com.zen.agency.repository.UserRepository;
import com.zen.agency.room.Agency;
import com.zen.agency.room.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUser = repository.getAllUser();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void deleteAllNote() {
        repository.deleteAllAgency();
    }

    public LiveData<List<User>> getAllUser() {
        if (allUser == null) {
            allUser = new MutableLiveData<List<User>>();
        }
        return allUser;
    }
}
