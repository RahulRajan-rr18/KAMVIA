package com.spyromedia.android.kamvia;

public class SearchResultRecyItem {

    private String memberName;
    private String memberLocation;
    private String memberStationCode;
    private  String user_id;

    public String getMemberName() {
        return memberName;
    }

    public String getMemberLocation() {
        return memberLocation;

    }

    public String getUser_id() {
        return user_id;
    }

    public String getMemberStationCode() {
        return memberStationCode;
    }

    public SearchResultRecyItem(String user_id,String memberName, String memberLocation, String memberLocationCode) {
        this.memberName = memberName;
        this.memberLocation = memberLocation;
        this.memberStationCode = memberLocationCode;
        this.user_id = user_id;
    }

}
