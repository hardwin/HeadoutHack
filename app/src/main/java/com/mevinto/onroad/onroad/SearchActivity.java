package com.mevinto.onroad.onroad;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ((ListView) findViewById(R.id.listView)).setAdapter(new GroupsAdapter(SearchActivity.this));
    }

    class GroupsAdapter extends BaseAdapter {
        Context con;

        public GroupsAdapter(Context con) {
            this.con = con;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(con);
            convertView = inflater.inflate(R.layout.groups_cell_row, null);

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 10;
        }
    }

}
