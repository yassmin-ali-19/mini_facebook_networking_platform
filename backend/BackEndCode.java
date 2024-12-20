package backend;

public class BackEndCode {

    private String photoPath;
    private String coverPath;
    private String bio;
    private String password;

    public BackEndCode(String photoPath, String coverPath, String bio, String password) {
        this.photoPath = photoPath;
        this.coverPath = coverPath;
        this.bio = bio;
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
