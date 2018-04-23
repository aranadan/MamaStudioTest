package com.fox.andrey.mamastudiotest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    List<Message> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_item_list, container, false);

        list = new ArrayList<>();
        int tabPosition = ((EventsActivity) getActivity()).getTabPosition();

        switch (tabPosition) {
            case 0:
                list = ((EventsActivity) getActivity()).getEventsList();
                Log.d("TAGGGG", list.size() + " - list size");
                break;
            case 1:
                list = ((EventsActivity) getActivity()).getShopsList();
                Log.d("TAGGGG", list.size() + " - list size");
                break;

        }

        //List<Message> list = MessagesList.getInstance().getList();

        MessagesAdapter adapter = new MessagesAdapter(getActivity(), R.layout.item_events, list);

        ListView listView = (ListView) v.findViewById(R.id.fragmentList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Message message = list.get(i);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("message", message);
            startActivity(intent);

        });
        return v;
    }
}
