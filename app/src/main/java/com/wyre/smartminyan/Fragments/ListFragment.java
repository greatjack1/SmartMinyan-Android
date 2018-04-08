package com.wyre.smartminyan.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyre.smartminyan.R;

/**
 * Created by yaakov on 4/3/18.
 */

public class ListFragment extends Fragment {

    private RecyclerView mList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.d("smartminyan","list fragment has been created");
        View v = inflater.inflate(R.layout.list_fragment,container,false);
        mList = v.findViewById(R.id.recycler_view_list);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
