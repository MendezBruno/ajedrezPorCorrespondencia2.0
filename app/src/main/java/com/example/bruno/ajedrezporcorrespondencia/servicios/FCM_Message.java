package com.example.bruno.ajedrezporcorrespondencia.servicios;

public class FCM_Message {
    public String to;
    public FCM_Notification notification = new FCM_Notification();

    public class FCM_Notification {
        public String body;
        public String title;
        public String icon;
    }
}
