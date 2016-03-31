package com.zangcun.store.model;

import java.io.Serializable;
import java.util.List;


public class TkModel implements Serializable {

    /**
     * goods_id : 231
     * updated_at : null
     * goods_name : 西藏 镀金烫金唐卡 黑卡 长寿佛 唐卡 三色装裱布 佛像 不褪色
     * goods_number : 5994
     * goods_type : 48
     * market_price : 58.0
     * price : 48.0
     * default_image : Data/Upload/Goods/201510/19/562466615acc5.jpg
     * contents : ["Data/Upload/kind/image/20151019/20151019114249_87793.jpg","Data/Upload/kind/image/20151019/20151019114249_87502.jpg","Data/Upload/kind/image/20151019/20151019114249_53493.jpg","Data/Upload/kind/image/20151019/20151019114249_43860.jpg","Data/Upload/kind/image/20151019/20151019114250_74441.jpg","Data/Upload/kind/image/20151019/20151019114250_25146.jpg","Data/Upload/kind/image/20151019/20151019114250_35263.jpg","Data/Upload/kind/image/20151019/20151019114250_34179.jpg","Data/Upload/kind/image/20151019/20151019114251_33789.jpg","Data/Upload/kind/image/20151019/20151019114251_26233.jpg","Data/Upload/kind/image/20151019/20151019114251_13295.jpg","Data/Upload/kind/image/20151019/20151019114251_61621.jpg","Data/Upload/kind/image/20151019/20151019114252_66142.jpg","Data/Upload/kind/image/20151019/20151019114252_46216.jpg","Data/Upload/kind/image/20151019/20151019114252_40256.jpg","Data/Upload/kind/image/20151019/20151019114252_96707.jpg","Data/Upload/kind/image/20151019/20151019114253_63108.jpg","Data/Upload/kind/image/20151019/20151019114253_19015.jpg","Data/Upload/kind/image/20151019/20151019114336_56454.jpg","Data/Upload/kind/image/20151019/20151019114336_47382.jpg","Data/Upload/kind/image/20151019/20151019114336_53189.jpg","Data/Upload/kind/image/20151019/20151019114337_91557.jpg","Data/Upload/kind/image/20151116/20151116141653_93805.jpg","Data/Upload/kind/image/20151116/20151116141653_45352.jpg"]
     * good_image_urls : ["Data/Upload/Goods/201510/19/5624665e38a1f_430x430.jpg","Data/Upload/Goods/201510/19/5624665ed82c0_430x430.jpg","Data/Upload/Goods/201510/19/5624665f77a92_430x430.jpg","Data/Upload/Goods/201510/19/562466602b53f_430x430.jpg","Data/Upload/Goods/201510/19/56246660bd141_430x430.jpg","Data/Upload/Goods/201510/19/562466615acc5_430x430.jpg"]
     * specs_array : [["蓝色本色","黄色小龙","蓝色小龙"],["长2米","长120厘米"]]
     * options_id : [{"id":549,"spec_1":"蓝色本色","spec_2":"长2米","market_price":"320.0","price":"288.0","stock":999,"sku":"DGG000231_549","image_url":null},{"id":548,"spec_1":"黄色小龙","spec_2":"长2米","market_price":"320.0","price":"288.0","stock":999,"sku":"1","image_url":null},{"id":547,"spec_1":"蓝色小龙","spec_2":"长2米","market_price":"320.0","price":"288.0","stock":999,"sku":"5","image_url":null},{"id":546,"spec_1":"蓝色本色","spec_2":"长120厘米","market_price":"58.0","price":"48.0","stock":999,"sku":"1","image_url":null},{"id":545,"spec_1":"黄色小龙","spec_2":"长120厘米","market_price":"58.0","price":"48.0","stock":999,"sku":"DGG000231_545","image_url":null},{"id":544,"spec_1":"蓝色小龙","spec_2":"长120厘米","market_price":"58.0","price":"48.0","stock":999,"sku":"2","image_url":null}]
     */

    private int goods_id;
    private Object updated_at;
    private String goods_name;
    private int goods_number;
    private int goods_type;
    private String market_price;
    private String price;
    private String default_image;
    private List<String> contents;
    private List<String> good_image_urls;
    private List<List<String>> specs_array;
    /**
     * id : 549
     * spec_1 : 蓝色本色
     * spec_2 : 长2米
     * market_price : 320.0
     * price : 288.0
     * stock : 999
     * sku : DGG000231_549
     * image_url : null
     */

    private List<OptionsIdEntity> options_id;

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public void setGoods_type(int goods_type) {
        this.goods_type = goods_type;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public void setGood_image_urls(List<String> good_image_urls) {
        this.good_image_urls = good_image_urls;
    }

    public void setSpecs_array(List<List<String>> specs_array) {
        this.specs_array = specs_array;
    }

    public void setOptions_id(List<OptionsIdEntity> options_id) {
        this.options_id = options_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public Object getUpdated_at() {
        return updated_at;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public int getGoods_type() {
        return goods_type;
    }

    public String getMarket_price() {
        return market_price;
    }

    public String getPrice() {
        return price;
    }

    public String getDefault_image() {
        return default_image;
    }

    public List<String> getContents() {
        return contents;
    }

    public List<String> getGood_image_urls() {
        return good_image_urls;
    }

    public List<List<String>> getSpecs_array() {
        return specs_array;
    }

    public List<OptionsIdEntity> getOptions_id() {
        return options_id;
    }

    public static class OptionsIdEntity implements Serializable {
        private int id;
        private String spec_1;
        private String spec_2;
        private String market_price;
        private String price;
        private int stock;
        private String sku;
        private Object image_url;

        public void setId(int id) {
            this.id = id;
        }

        public void setSpec_1(String spec_1) {
            this.spec_1 = spec_1;
        }

        public void setSpec_2(String spec_2) {
            this.spec_2 = spec_2;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public void setImage_url(Object image_url) {
            this.image_url = image_url;
        }

        public int getId() {
            return id;
        }

        public String getSpec_1() {
            return spec_1;
        }

        public String getSpec_2() {
            return spec_2;
        }

        public String getMarket_price() {
            return market_price;
        }

        public String getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getSku() {
            return sku;
        }

        public Object getImage_url() {
            return image_url;
        }
    }
}
