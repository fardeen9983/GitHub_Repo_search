package android.example.com.githubprojectsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GitRepoAdapter extends ArrayAdapter<GitRepo> {

    public GitRepoAdapter(@NonNull Context context, @NonNull List<GitRepo> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        if (root==null)
            root = LayoutInflater.from(getContext()).inflate(R.layout.repo_layout,parent,false);
        TextView name = root.findViewById(R.id.repo_name);
        TextView owner = root.findViewById(R.id.repo_owner);
        GitRepo repo = getItem(position);
        name.setText(repo.getName());
        owner.setText(repo.getOwner());

        return root;
    }
}
