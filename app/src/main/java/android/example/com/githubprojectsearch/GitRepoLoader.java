package android.example.com.githubprojectsearch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class GitRepoLoader extends AsyncTaskLoader<ArrayList<GitRepo>> {

    private String url;
    private final String TAG = getClass().getSimpleName();

    public GitRepoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ArrayList<GitRepo> loadInBackground() {
        return parseJson();
    }

    private ArrayList<GitRepo> parseJson() {
        ArrayList<GitRepo> gitRepos = new ArrayList<>();
        if (url != null) {
            String json = getJSON(getURL(url));
            try {
                JSONObject root = new JSONObject(json);
                JSONArray items = root.optJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item = items.optJSONObject(i);
                    gitRepos.add(new GitRepo(item.optString("html_url"), item.optString("tetros")
                            , item.optJSONObject("owner").optString("login")
                            , item.optJSONObject("owner").optString("avatar_url")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return gitRepos;
    }

    private URL getURL(String string) {
        URL url = null;
        if (string != null) {
            try {
                url = new URL(string);
            } catch (MalformedURLException e) {
                Log.e(TAG, "Malformed URL");
            }
        }
        return url;
    }

    private String getJSON(URL url) {
        String json = null;
        if (url != null) {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                json = parseInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return json;
    }

    private String parseInputStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        String line;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
