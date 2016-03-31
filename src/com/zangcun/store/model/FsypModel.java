package com.zangcun.store.model;

import java.io.Serializable;
import java.util.List;


public class FsypModel implements Serializable {
    /**
     * goods_id : 228
     * updated_at : null
     * goods_name : 尼泊尔正品纯铁 护身避邪普巴金刚杵普巴金刚橛 普巴橛
     * goods_number : 99
     * goods_type : 49
     * market_price : 65.0
     * price : 55.0
     * default_image : Data/Upload/Goods/201510/15/561f74c8415ae.jpg
     * contents : ["Data/Upload/kind/image/20151015/20151015180141_54130.jpg","Data/Upload/kind/image/20151015/20151015180142_15335.jpg","Data/Upload/kind/image/20151015/20151015180142_86766.jpg","Data/Upload/kind/image/20151015/20151015180142_13513.jpg","Data/Upload/kind/image/20151015/20151015180142_96638.jpg","Data/Upload/kind/image/20151015/20151015180143_25420.jpg","Data/Upload/kind/image/20151015/20151015180143_74359.jpg","Data/Upload/kind/image/20151015/20151015180143_77500.jpg","Data/Upload/kind/image/20151015/20151015180143_22708.jpg","Data/Upload/kind/image/20151015/20151015180143_90965.jpg","Data/Upload/kind/image/20151116/20151116141435_90195.jpg"]
     * good_image_urls : ["Data/Upload/Goods/201510/15/561f74c70021b_430x430.jpg","Data/Upload/Goods/201510/15/561f74c7a23e5_430x430.jpg","Data/Upload/Goods/201510/15/561f74c8415ae_430x430.jpg","Data/Upload/Goods/201510/15/561f74c8d2dfb_430x430.jpg","Data/Upload/Goods/201510/15/561f74c977902_430x430.jpg"]
     * specs_array : [[""],[""]]
     * options_id : [{"id":7824,"spec_1":"","spec_2":"","market_price":"65.0","price":"55.0","stock":97,"sku":"201510151710","image_url":null}]
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
     * id : 7824
     * spec_1 :
     * spec_2 :
     * market_price : 65.0
     * price : 55.0
     * stock : 97
     * sku : 201510151710
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
