package hu.bme.edu.handmade.models.response;

public class ResponseFile {
    private Long id;
    private String name;
    private String url;
    private long size;

    public ResponseFile(Long id, String name, String url, long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
