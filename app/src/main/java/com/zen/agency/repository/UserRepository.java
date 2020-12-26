package com.zen.agency.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.zen.agency.room.Agency;
import com.zen.agency.room.AgencyDao;
import com.zen.agency.room.AgencyDatabase;
import com.zen.agency.room.User;
import com.zen.agency.room.UserDao;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUser;

    public UserRepository(Application application) {
        AgencyDatabase database = AgencyDatabase.getInstance(application);
        userDao = database.userDao();
        allUser = userDao.getAllUser();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);

    }

    public void deleteAllAgency() {
        new DeleteAllUserAsyncTask(userDao).execute();
    }


    public LiveData<List<User>> getAllUser() {
        return allUser;
    }




    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }


    public static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao agencyDao;
        private UpdateUserAsyncTask(UserDao agencyDao) {
            this.agencyDao = agencyDao;
        }

        @Override
        protected Void doInBackground(User... notes) {
            agencyDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... notes) {
            userDao.delete(notes[0]);
            return null;
        }


    }

    public static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        private DeleteAllUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllNotes();
            return null;
        }
    }

}