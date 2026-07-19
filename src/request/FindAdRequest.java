package request;

import java.util.UUID;

public class FindAdRequest {
    private UUID id;
    private UUID authorId;

    public FindAdRequest(){}

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) { this.id = id; }

    public UUID getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(UUID authorId) { this.authorId = authorId; }
}
