package android.example.com.githubprojectsearch;

public class GitRepo {

    private String url;
    private String name;
    private String owner;
    private String avatar;

    public GitRepo(String url, String name, String owner, String avatar) {
        this.url = url;
        this.name = name;
        this.owner = owner;
        this.avatar = avatar;
    }

    public GitRepo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
