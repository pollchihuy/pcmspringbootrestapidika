package com.juaracoding;

import java.util.concurrent.Callable;

public class ContohTimeout implements Callable<Boolean> {

    String emailTo;
    String subject;
    String content;
    String[] attachment;

    public ContohTimeout(String emailTo, String subject, String content, String[] attachment) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
    }

    @Override
    public Boolean call() throws Exception {
        panggilEmail(emailTo, subject, content, attachment);
        return true;
    }

    public void panggilEmail(String emailTo,String subject,String content, String[] attachment){
        //ketik logic disini
    }

}