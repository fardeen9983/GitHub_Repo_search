package android.example.com.githubprojectsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText queryProjects;
    private ListView projectList;
    private List<String> projects = null;
    private ArrayAdapter<String> projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryProjects = findViewById(R.id.search_text);
        projectList = findViewById(R.id.list_project);

        projectAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,projects);
        projectList.setAdapter(projectAdapter);
    }
}
