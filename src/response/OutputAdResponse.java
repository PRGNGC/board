package response;

import java.util.List;

public class OutputAdResponse {
    private List<String> ads;

    public OutputAdResponse(){}

    public List<String> getAds(){
        return this.ads;
    }

    public void setAds(List<String> ads){
        this.ads = ads;
    }
}
