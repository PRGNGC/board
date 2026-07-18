package response;

import java.util.List;

public class SearchAdResponse {
    private List<String> ads;

    public SearchAdResponse(){}

    public List<String> getAds(){
        return this.ads;
    }

    public void setAds(List<String> ads){
        this.ads = ads;
    }
}
