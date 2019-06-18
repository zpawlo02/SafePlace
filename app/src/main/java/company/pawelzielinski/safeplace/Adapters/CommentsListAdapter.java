package company.pawelzielinski.safeplace.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import company.pawelzielinski.safeplace.Classes.Comment;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.R;

public class CommentsListAdapter extends ArrayAdapter<Comment> {

    Context context;
    int mResource;
    Bundle bundle;

    public CommentsListAdapter(Context context, int resource, ArrayList<Comment> objects, Bundle bundle) {
        super(context, resource, objects);
        this.context = context;
        this.bundle = bundle;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.adapter_comment_view_layout, null,true);

        String text = getItem(position).getComment();
        String username = getItem(position).getUsername();
        String userId = getItem(position).getUserId();

        TextView textViewusername = (TextView) v.findViewById(R.id.textViewUserName);
        TextView textViewText = (TextView) v.findViewById(R.id.textViewText);

        textViewusername.setText(username);
        textViewText.setText(text);

        return v;
    }
}
