package com.zangcun.store.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class GetAddressResultModel implements Serializable {


    /**
     * id : 91
     * user_name : 18780224529
     * email :
     * address : [{"id":191,"consignee":"图腾","email":"","region_id":2525,"address":"北京北京东城区急急急","mobile":"555","is_default":false},{"id":190,"consignee":"图腾","email":"","region_id":2525,"address":"北京北京东城区急急急","mobile":"555","is_default":false}]
     * orders : []
     * star_good_ids : []
     * oauths : []
     */

    private int id;
    private String user_name;
    private String email;
    /**
     * id : 191
     * consignee : 图腾
     * email :
     * region_id : 2525
     * address : 北京北京东城区急急急
     * mobile : 555
     * is_default : false
     */

    private List<AddressBean> address;
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

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
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

    public static class AddressBean implements Serializable{
        private int id;
        private String consignee;
        private String email;
        private int region_id;
        private String address;
        private String mobile;
        private boolean is_default;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }

        @Override
        public String toString() {
            return "AddressBean{" +
                    "id=" + id +
                    ", consignee='" + consignee + '\'' +
                    ", email='" + email + '\'' +
                    ", region_id=" + region_id +
                    ", address='" + address + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", is_default=" + is_default +
                    '}';
        }
    }
}
