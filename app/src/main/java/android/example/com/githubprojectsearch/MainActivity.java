package android.example.com.githubprojectsearch;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<GitRepo>>{

    private EditText queryProjects;
    private ListView projectList;
    private List<GitRepo> repos = null;
    private ArrayAdapter<String> projectAdapter;
    private final String BASE_QUERY = "https://api.github.com/search/repositories";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryProjects = findViewById(R.id.search_text);
        projectList = findViewById(R.id.list_project);

        queryProjects.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == KeyEvent.KEYCODE_ENTER) {
                    populateList();
                    return true;
                }
                return false;
            }
        });
        //projectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, repos);
        projectList.setAdapter(projectAdapter);
    }

    private void populateList(){
        String searchKey = queryProjects.getText().toString().trim();
        Uri baseUri = Uri.parse(BASE_QUERY);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("q",searchKey)
                .appendQueryParameter("sort","stars")
                .appendQueryParameter("order","asc");
        builder.build();
        Bundle bundle = new Bundle();
        bundle.putString("url",builder.toString());
        getLoaderManager().initLoader(1,bundle,this);
    }

    @Override
    public Loader<ArrayList<GitRepo>> onCreateLoader(int id, Bundle args) {

        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<GitRepo>> loader, ArrayList<GitRepo> data) {
        repos = data;
        //projectAdapter = new ArrayAdapter<GitRepo>(this,null,repos);
        projectList.setAdapter(projectAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<GitRepo>> loader) {

    }
}
