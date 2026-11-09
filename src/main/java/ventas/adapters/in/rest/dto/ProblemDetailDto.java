package ventas.adapters.in.rest.dto;

import java.time.Instant;
import java.util.List;

public class ProblemDetailDto {

    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
    private Instant timestamp;
    private List<String> invalidParams;

    public ProblemDetailDto() {
        this.timestamp = Instant.now();
    }

    public ProblemDetailDto(String type, String title, int status, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.timestamp = Instant.now();
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getInstance() { return instance; }
    public void setInstance(String instance) { this.instance = instance; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public List<String> getInvalidParams() { return invalidParams; }
    public void setInvalidParams(List<String> invalidParams) { this.invalidParams = invalidParams; }
}
