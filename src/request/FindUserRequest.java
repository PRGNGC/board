package request;

import java.util.UUID;

public class FindUserRequest {
    private UUID id;

    public FindUserRequest(){}

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
