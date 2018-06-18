package com.oritmalki.newsapp1.networkapi;

public class Tag {


        private String id;
        private String type;
        private String webTitle;
        private String webUrl;
        private String apiUrl;
        private String bio;


        public Tag(String id, String type, String webTitle, String webUrl, String apiUrl, String bio) {
            this.id = id;
            this.type = type;
            this.webTitle = webTitle;
            this.webUrl = webUrl;
            this.apiUrl = apiUrl;
            this.bio = bio;
        }

        public Tag() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public void setWebTitle(String webTitle) {
            this.webTitle = webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }


    }

