package com.zangcun.store.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class RegisterEntity {

    /**
     * id : 91
     * user_name : 18780224529
     * email :
     * address : []
     * orders : []
     * star_good_ids : []
     * oauths : []
     */

    private UserBean user;
    /**
     * user : {"id":91,"user_name":"18780224529","email":"","address":[],"orders":[],"star_good_ids":[],"oauths":[]}
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX25hbWUiOiIxODc4MDIyNDUyOSIsImV4cCI6MTQ2MDAyODcyOCwiaWQiOjkxLCJwYXNzd29yZCI6ImUxMGFkYzM5NDliYTU5YWJiZTU2ZTA1N2YyMGY4ODNlIn0.H6KW2JZvqX0ddrKf_yrZoc18AB3AKTfFJ0hNEEaPVes
     */

    private String token;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        private int id;
        private String user_name;
        private String email;
        private List<?> address;
        private List<?> orders;
        private List<?> star_good_ids;
        private List<?> oauths;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<?> getAddress() {
            return address;
        }

        public void setAddress(List<?> address) {
            this.address = address;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public List<?> getStar_good_ids() {
            return star_good_ids;
        }

        public void setStar_good_ids(List<?> star_good_ids) {
            this.star_good_ids = star_good_ids;
        }

        public List<?> getOauths() {
            return oauths;
        }

        public void setOauths(List<?> oauths) {
            this.oauths = oauths;
        }
    }
}
