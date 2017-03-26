package com.example.saisi.agni;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saisi on 26-Mar-17.
 */

public class CourseList extends ArrayAdapter<CourseDetails> {

        private Activity Context;
        List<CourseDetails> details;

    public CourseList(Activity context, List<CourseDetails> detailsObject) {
        super(context,R.layout.previous_posts_list_fragment, detailsObject);
        this.Context = context;
        this.details = detailsObject;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        LayoutInflater inflater = Context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.previous_posts_list_fragment,null,true);

        TextView courseNameText = (TextView)listViewItem.findViewById(R.id.CourseNameList);
        TextView courseDetailsText = (TextView)listViewItem.findViewById(R.id.CourseDetailsList);

        CourseDetails detailss = details.get(position);
        courseNameText.setText(detailss.getCourseName());
        courseDetailsText.setText(detailss.getCourseDetails());
        return listViewItem;
    }
}
