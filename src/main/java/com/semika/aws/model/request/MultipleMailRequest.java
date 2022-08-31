package com.semika.aws.model.request;

import java.util.List;

public class MultipleMailRequest {

    private List emailList;

    public List getEmailList() {
        return emailList;
    }

    public void setEmailList(List emailList) {
        this.emailList = emailList;
    }
}
