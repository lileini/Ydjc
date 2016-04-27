package com.zangcun.store.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopCarModel implements Parcelable {

    /**
     * rec_id : 923
     * goods_name : 珍品尼泊尔纯手工 镀金积光佛母佛像 摩利支天菩萨佛母 仅此一尊
     * goods_image : Data/Upload/Goods/201510/15/561f214521833.jpg
     * quantity : 11
     * ischecked : 1
     * price : 6800.0
     * good : {"goods_id":224,"updated_at":null,"goods_name":"珍品尼泊尔纯手工 镀金积光佛母佛像 摩利支天菩萨佛母 仅此一尊","goods_number":0,"goods_type":47,"market_price":"7000.0","price":"6800.0","default_image":"Data/Upload/Goods/201510/15/561f214521833.jpg","is_shipping":true,"contents":["Data/Upload/kind/image/20151015/20151015092531_46128.jpg","Data/Upload/kind/image/20151015/20151015092531_10126.jpg","Data/Upload/kind/image/20151015/20151015092531_89719.jpg","Data/Upload/kind/image/20151015/20151015092532_99489.jpg","Data/Upload/kind/image/20151015/20151015092532_27089.jpg","Data/Upload/kind/image/20151015/20151015092532_37697.jpg","Data/Upload/kind/image/20151015/20151015092532_63074.jpg","Data/Upload/kind/image/20151015/20151015092532_53971.jpg","Data/Upload/kind/image/20151015/20151015092533_24376.jpg","Data/Upload/kind/image/20151015/20151015092533_39115.jpg","Data/Upload/kind/image/20151015/20151015092533_22095.jpg","Data/Upload/kind/image/20151015/20151015092533_56889.jpg","Data/Upload/kind/image/20151015/20151015092534_88001.jpg","Data/Upload/kind/image/20151015/20151015092534_56559.jpg","Data/Upload/kind/image/20151015/20151015092534_72824.jpg","Data/Upload/kind/image/20151015/20151015092534_94077.jpg","Data/Upload/kind/image/20151015/20151015092534_99820.jpg","Data/Upload/kind/image/20151015/20151015092535_85321.jpg","Data/Upload/kind/image/20151116/20151116141057_81874.jpg"],"good_image_urls":["Data/Upload/Goods/201510/15/561f1f8a472d8_430x430.jpg","Data/Upload/Goods/201510/15/561f214521833_430x430.jpg","Data/Upload/Goods/201510/15/561f22e6c3bbc_430x430.jpg","Data/Upload/Goods/201510/15/561f22e75c470_430x430.jpg","Data/Upload/Goods/201510/15/561f22e80a6bd_430x430.jpg"],"specs_array":[[""],[""]],"options_id":[{"id":7822,"spec_1":"","spec_2":"","market_price":"7000.0","price":"6800.0","stock":0,"sku":"201510150910","image_url":null}]}
     * good_option : 颜色:,尺寸:
     */

    private int rec_id;
    private String goods_name;
    private String goods_image;
    private int quantity;
    private int ischecked;
    private GoodBean.OptionsIdBean optionsIdBean;

    private String price;

    public GoodBean.OptionsIdBean getOptionsIdBean() {
        return optionsIdBean;
    }

    public void setOptionsIdBean(GoodBean.OptionsIdBean optionsIdBean) {
        this.optionsIdBean = optionsIdBean;
    }

    public String[] getColorSize() {
        return colorSize;
    }

    public void setColorSize(String[] colorSize) {
        this.colorSize = colorSize;
    }

    private String[] colorSize;
    /**
     * goods_id : 224
     * updated_at : null
     * goods_name : 珍品尼泊尔纯手工 镀金积光佛母佛像 摩利支天菩萨佛母 仅此一尊
     * goods_number : 0
     * goods_type : 47
     * market_price : 7000.0
     * price : 6800.0
     * default_image : Data/Upload/Goods/201510/15/561f214521833.jpg
     * is_shipping : true
     * contents : ["Data/Upload/kind/image/20151015/20151015092531_46128.jpg","Data/Upload/kind/image/20151015/20151015092531_10126.jpg","Data/Upload/kind/image/20151015/20151015092531_89719.jpg","Data/Upload/kind/image/20151015/20151015092532_99489.jpg","Data/Upload/kind/image/20151015/20151015092532_27089.jpg","Data/Upload/kind/image/20151015/20151015092532_37697.jpg","Data/Upload/kind/image/20151015/20151015092532_63074.jpg","Data/Upload/kind/image/20151015/20151015092532_53971.jpg","Data/Upload/kind/image/20151015/20151015092533_24376.jpg","Data/Upload/kind/image/20151015/20151015092533_39115.jpg","Data/Upload/kind/image/20151015/20151015092533_22095.jpg","Data/Upload/kind/image/20151015/20151015092533_56889.jpg","Data/Upload/kind/image/20151015/20151015092534_88001.jpg","Data/Upload/kind/image/20151015/20151015092534_56559.jpg","Data/Upload/kind/image/20151015/20151015092534_72824.jpg","Data/Upload/kind/image/20151015/20151015092534_94077.jpg","Data/Upload/kind/image/20151015/20151015092534_99820.jpg","Data/Upload/kind/image/20151015/20151015092535_85321.jpg","Data/Upload/kind/image/20151116/20151116141057_81874.jpg"]
     * good_image_urls : ["Data/Upload/Goods/201510/15/561f1f8a472d8_430x430.jpg","Data/Upload/Goods/201510/15/561f214521833_430x430.jpg","Data/Upload/Goods/201510/15/561f22e6c3bbc_430x430.jpg","Data/Upload/Goods/201510/15/561f22e75c470_430x430.jpg","Data/Upload/Goods/201510/15/561f22e80a6bd_430x430.jpg"]
     * specs_array : [[""],[""]]
     * options_id : [{"id":7822,"spec_1":"","spec_2":"","market_price":"7000.0","price":"6800.0","stock":0,"sku":"201510150910","image_url":null}]
     */

    private GoodBean good;
    private String good_option;

    public int getRec_id() {
        return rec_id;
    }

    public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIschecked() {
        return ischecked;
    }

    public void setIschecked(int ischecked) {
        this.ischecked = ischecked;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public GoodBean getGood() {
        return good;
    }

    public void setGood(GoodBean good) {
        this.good = good;
    }

    public String getGood_option() {
        return good_option;
    }

    public void setGood_option(String good_option) {
        this.good_option = good_option;
    }

    public static class GoodBean implements Parcelable {
        private int goods_id;
        private String updated_at;
        private String goods_name;
        private int goods_number;
        private int goods_type;
        private String market_price;
        private String price;
        private String default_image;
        private boolean is_shipping;
        private List<String> contents;
        private List<String> good_image_urls;
        private ArrayList<ArrayList<String>> specs_array;
        /**
         * id : 7822
         * spec_1 :
         * spec_2 :
         * market_price : 7000.0
         * price : 6800.0
         * stock : 0
         * sku : 201510150910
         * image_url : null
         */

        private List<OptionsIdBean> options_id;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDefault_image() {
            return default_image;
        }

        public void setDefault_image(String default_image) {
            this.default_image = default_image;
        }

        public boolean isIs_shipping() {
            return is_shipping;
        }

        public void setIs_shipping(boolean is_shipping) {
            this.is_shipping = is_shipping;
        }

        public List<String> getContents() {
            return contents;
        }

        public void setContents(List<String> contents) {
            this.contents = contents;
        }

        public List<String> getGood_image_urls() {
            return good_image_urls;
        }

        public void setGood_image_urls(List<String> good_image_urls) {
            this.good_image_urls = good_image_urls;
        }

        public ArrayList<ArrayList<String>> getSpecs_array() {
            return specs_array;
        }

        public void setSpecs_array(ArrayList<ArrayList<String>> specs_array) {
            this.specs_array = specs_array;
        }

        public List<OptionsIdBean> getOptions_id() {
            return options_id;
        }

        public void setOptions_id(List<OptionsIdBean> options_id) {
            this.options_id = options_id;
        }

        public static class OptionsIdBean implements Parcelable{
            private int id;
            private String spec_1;
            private String spec_2;
            private String market_price;
            private String price;
            private int stock;
            private String sku;
            private String image_url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSpec_1() {
                return spec_1;
            }

            public void setSpec_1(String spec_1) {
                this.spec_1 = spec_1;
            }

            public String getSpec_2() {
                return spec_2;
            }

            public void setSpec_2(String spec_2) {
                this.spec_2 = spec_2;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public OptionsIdBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.spec_1);
                dest.writeString(this.spec_2);
                dest.writeString(this.market_price);
                dest.writeString(this.price);
                dest.writeInt(this.stock);
                dest.writeString(this.sku);
                dest.writeString(this.image_url);
            }

            protected OptionsIdBean(Parcel in) {
                this.id = in.readInt();
                this.spec_1 = in.readString();
                this.spec_2 = in.readString();
                this.market_price = in.readString();
                this.price = in.readString();
                this.stock = in.readInt();
                this.sku = in.readString();
                this.image_url = in.readString();
            }

            public static final Creator<OptionsIdBean> CREATOR = new Creator<OptionsIdBean>() {
                @Override
                public OptionsIdBean createFromParcel(Parcel source) {
                    return new OptionsIdBean(source);
                }

                @Override
                public OptionsIdBean[] newArray(int size) {
                    return new OptionsIdBean[size];
                }
            };
        }

        public GoodBean() {
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.goods_id);
            dest.writeString(this.updated_at);
            dest.writeString(this.goods_name);
            dest.writeInt(this.goods_number);
            dest.writeInt(this.goods_type);
            dest.writeString(this.market_price);
            dest.writeString(this.price);
            dest.writeString(this.default_image);
            dest.writeByte(is_shipping ? (byte) 1 : (byte) 0);
            dest.writeStringList(this.contents);
            dest.writeStringList(this.good_image_urls);
            dest.writeTypedList(options_id);
        }

        protected GoodBean(Parcel in) {
            this.goods_id = in.readInt();
            this.updated_at = in.readString();
            this.goods_name = in.readString();
            this.goods_number = in.readInt();
            this.goods_type = in.readInt();
            this.market_price = in.readString();
            this.price = in.readString();
            this.default_image = in.readString();
            this.is_shipping = in.readByte() != 0;
            this.contents = in.createStringArrayList();
            this.good_image_urls = in.createStringArrayList();
            this.options_id = in.createTypedArrayList(OptionsIdBean.CREATOR);
        }

        public static final Creator<GoodBean> CREATOR = new Creator<GoodBean>() {
            @Override
            public GoodBean createFromParcel(Parcel source) {
                return new GoodBean(source);
            }

            @Override
            public GoodBean[] newArray(int size) {
                return new GoodBean[size];
            }
        };
    }


    public ShopCarModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rec_id);
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_image);
        dest.writeInt(this.quantity);
        dest.writeInt(this.ischecked);
        dest.writeParcelable(this.optionsIdBean, flags);
        dest.writeString(this.price);
        dest.writeStringArray(this.colorSize);
        dest.writeParcelable(this.good, flags);
        dest.writeString(this.good_option);
    }

    protected ShopCarModel(Parcel in) {
        this.rec_id = in.readInt();
        this.goods_name = in.readString();
        this.goods_image = in.readString();
        this.quantity = in.readInt();
        this.ischecked = in.readInt();
        this.optionsIdBean = in.readParcelable(GoodBean.OptionsIdBean.class.getClassLoader());
        this.price = in.readString();
        this.colorSize = in.createStringArray();
        this.good = in.readParcelable(GoodBean.class.getClassLoader());
        this.good_option = in.readString();
    }

    public static final Creator<ShopCarModel> CREATOR = new Creator<ShopCarModel>() {
        @Override
        public ShopCarModel createFromParcel(Parcel source) {
            return new ShopCarModel(source);
        }

        @Override
        public ShopCarModel[] newArray(int size) {
            return new ShopCarModel[size];
        }
    };
}
