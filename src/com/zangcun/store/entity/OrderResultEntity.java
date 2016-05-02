package com.zangcun.store.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OrderResultEntity {

    /**
     * order_id : 451
     * order_sn : 2016041721595506980
     * order_status : 11
     * order_amount : 1780.0
     * goods_amount : 1780.0
     * add_time : 1460901595
     * user_id : 91
     * consignee : hugging kkkkk
     * address : 55555
     * mobile :
     * invoice_no :
     * pay_time : 0
     * shipping_time : 0
     * message : null
     * region_id : 500
     * order_goods : [{"id":521,"goods_sn":"2","good":{"goods_id":289,"updated_at":null,"goods_name":"精品 尼泊尔进口 纯手工雕花 纯银八吉祥 护法杯 佛事用品","goods_number":5,"goods_type":49,"market_price":"2000.0","price":"1780.0","default_image":"Data/Upload/Goods/201511/05/563ab20e5607a.jpg","is_shipping":true,"contents":["Data/Upload/kind/image/20151105/20151105093428_31289.jpg","Data/Upload/kind/image/20151105/20151105093428_79872.jpg","Data/Upload/kind/image/20151105/20151105093428_58191.jpg","Data/Upload/kind/image/20151105/20151105093429_65360.jpg","Data/Upload/kind/image/20151105/20151105093429_91023.jpg","Data/Upload/kind/image/20151105/20151105093429_87006.jpg","Data/Upload/kind/image/20151105/20151105093429_99722.jpg","Data/Upload/kind/image/20151105/20151105093429_16937.jpg","Data/Upload/kind/image/20151105/20151105093430_81862.jpg","Data/Upload/kind/image/20151105/20151105093430_63931.jpg"],"good_image_urls":["Data/Upload/Goods/201511/05/563ab20e5607a_430x430.jpg","Data/Upload/Goods/201511/05/563ab20ecc475_430x430.jpg","Data/Upload/Goods/201511/05/563ab20f59ea8_430x430.jpg","Data/Upload/Goods/201511/05/563ab20fdac88_430x430.jpg","Data/Upload/Goods/201511/05/563ab2106ddfc_430x430.jpg"],"specs_array":[["银色"],[""]],"options_id":[{"id":1224,"spec_1":"银色","spec_2":"","market_price":"2000.0","price":"1780.0","stock":5,"sku":"2","image_url":null}]},"goods_numbers":1,"goods_attr":"颜色:银色,尺寸:"}]
     */

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }



    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean implements Parcelable {
        private int order_id;
        private String order_sn;
        private int order_status;
        private String order_amount;
        private String goods_amount;
        private int add_time;
        private int user_id;
        private String consignee;
        private String address;
        private String mobile;
        private String invoice_no;
        private int pay_time;
        private int shipping_time;
        private String message;
        private int region_id;
        /**
         * id : 521
         * goods_sn : 2
         * good : {"goods_id":289,"updated_at":null,"goods_name":"精品 尼泊尔进口 纯手工雕花 纯银八吉祥 护法杯 佛事用品","goods_number":5,"goods_type":49,"market_price":"2000.0","price":"1780.0","default_image":"Data/Upload/Goods/201511/05/563ab20e5607a.jpg","is_shipping":true,"contents":["Data/Upload/kind/image/20151105/20151105093428_31289.jpg","Data/Upload/kind/image/20151105/20151105093428_79872.jpg","Data/Upload/kind/image/20151105/20151105093428_58191.jpg","Data/Upload/kind/image/20151105/20151105093429_65360.jpg","Data/Upload/kind/image/20151105/20151105093429_91023.jpg","Data/Upload/kind/image/20151105/20151105093429_87006.jpg","Data/Upload/kind/image/20151105/20151105093429_99722.jpg","Data/Upload/kind/image/20151105/20151105093429_16937.jpg","Data/Upload/kind/image/20151105/20151105093430_81862.jpg","Data/Upload/kind/image/20151105/20151105093430_63931.jpg"],"good_image_urls":["Data/Upload/Goods/201511/05/563ab20e5607a_430x430.jpg","Data/Upload/Goods/201511/05/563ab20ecc475_430x430.jpg","Data/Upload/Goods/201511/05/563ab20f59ea8_430x430.jpg","Data/Upload/Goods/201511/05/563ab20fdac88_430x430.jpg","Data/Upload/Goods/201511/05/563ab2106ddfc_430x430.jpg"],"specs_array":[["银色"],[""]],"options_id":[{"id":1224,"spec_1":"银色","spec_2":"","market_price":"2000.0","price":"1780.0","stock":5,"sku":"2","image_url":null}]}
         * goods_numbers : 1
         * goods_attr : 颜色:银色,尺寸:
         */

        private List<OrderGoodsBean> order_goods;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
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

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public int getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(int shipping_time) {
            this.shipping_time = shipping_time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }


        public static class OrderGoodsBean implements Parcelable {
            private int id;
            private String goods_sn;
            /**
             * goods_id : 289
             * updated_at : null
             * goods_name : 精品 尼泊尔进口 纯手工雕花 纯银八吉祥 护法杯 佛事用品
             * goods_number : 5
             * goods_type : 49
             * market_price : 2000.0
             * price : 1780.0
             * default_image : Data/Upload/Goods/201511/05/563ab20e5607a.jpg
             * is_shipping : true
             * contents : ["Data/Upload/kind/image/20151105/20151105093428_31289.jpg","Data/Upload/kind/image/20151105/20151105093428_79872.jpg","Data/Upload/kind/image/20151105/20151105093428_58191.jpg","Data/Upload/kind/image/20151105/20151105093429_65360.jpg","Data/Upload/kind/image/20151105/20151105093429_91023.jpg","Data/Upload/kind/image/20151105/20151105093429_87006.jpg","Data/Upload/kind/image/20151105/20151105093429_99722.jpg","Data/Upload/kind/image/20151105/20151105093429_16937.jpg","Data/Upload/kind/image/20151105/20151105093430_81862.jpg","Data/Upload/kind/image/20151105/20151105093430_63931.jpg"]
             * good_image_urls : ["Data/Upload/Goods/201511/05/563ab20e5607a_430x430.jpg","Data/Upload/Goods/201511/05/563ab20ecc475_430x430.jpg","Data/Upload/Goods/201511/05/563ab20f59ea8_430x430.jpg","Data/Upload/Goods/201511/05/563ab20fdac88_430x430.jpg","Data/Upload/Goods/201511/05/563ab2106ddfc_430x430.jpg"]
             * specs_array : [["银色"],[""]]
             * options_id : [{"id":1224,"spec_1":"银色","spec_2":"","market_price":"2000.0","price":"1780.0","stock":5,"sku":"2","image_url":null}]
             */

            private GoodBean good;
            private int goods_numbers;
            private String goods_attr;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public GoodBean getGood() {
                return good;
            }

            public void setGood(GoodBean good) {
                this.good = good;
            }

            public int getGoods_numbers() {
                return goods_numbers;
            }

            public void setGoods_numbers(int goods_numbers) {
                this.goods_numbers = goods_numbers;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public GoodBean.OptionsIdBean getOptionsIdBean() {
                return optionsIdBean;
            }

            public void setOptionsIdBean(GoodBean.OptionsIdBean optionsIdBean) {
                this.optionsIdBean = optionsIdBean;
            }

            private GoodBean.OptionsIdBean optionsIdBean;

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
                private List<List<String>> specs_array;

                /**
                 * id : 1224
                 * spec_1 : 银色
                 * spec_2 :
                 * market_price : 2000.0
                 * price : 1780.0
                 * stock : 5
                 * sku : 2
                 * image_url : null
                 */
                private GoodBean.OptionsIdBean optionsIdBean;


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

                public List<List<String>> getSpecs_array() {
                    return specs_array;
                }

                public void setSpecs_array(List<List<String>> specs_array) {
                    this.specs_array = specs_array;
                }

                public List<OptionsIdBean> getOptions_id() {
                    return options_id;
                }

                public void setOptions_id(List<OptionsIdBean> options_id) {
                    this.options_id = options_id;
                }

                public static class OptionsIdBean {
                    private int id;
                    private String spec_1;
                    private String spec_2;
                    private String market_price;
                    private String price;
                    private int stock;
                    private String sku;
                    private Object image_url;

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

                    public Object getImage_url() {
                        return image_url;
                    }

                    public void setImage_url(Object image_url) {
                        this.image_url = image_url;
                    }
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
                }

                public GoodBean() {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.goods_sn);
                dest.writeParcelable(this.good, flags);
                dest.writeInt(this.goods_numbers);
                dest.writeString(this.goods_attr);

            }

            public OrderGoodsBean() {
            }

            protected OrderGoodsBean(Parcel in) {
                this.id = in.readInt();
                this.goods_sn = in.readString();
                this.good = in.readParcelable(GoodBean.class.getClassLoader());
                this.goods_numbers = in.readInt();
                this.goods_attr = in.readString();
            }

            public static final Creator<OrderGoodsBean> CREATOR = new Creator<OrderGoodsBean>() {
                @Override
                public OrderGoodsBean createFromParcel(Parcel source) {
                    return new OrderGoodsBean(source);
                }

                @Override
                public OrderGoodsBean[] newArray(int size) {
                    return new OrderGoodsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.order_id);
            dest.writeString(this.order_sn);
            dest.writeInt(this.order_status);
            dest.writeString(this.order_amount);
            dest.writeString(this.goods_amount);
            dest.writeInt(this.add_time);
            dest.writeInt(this.user_id);
            dest.writeString(this.consignee);
            dest.writeString(this.address);
            dest.writeString(this.mobile);
            dest.writeString(this.invoice_no);
            dest.writeInt(this.pay_time);
            dest.writeInt(this.shipping_time);
            dest.writeString(this.message);
            dest.writeInt(this.region_id);
            dest.writeList(this.order_goods);
        }

        public OrderBean() {
        }

        protected OrderBean(Parcel in) {
            this.order_id = in.readInt();
            this.order_sn = in.readString();
            this.order_status = in.readInt();
            this.order_amount = in.readString();
            this.goods_amount = in.readString();
            this.add_time = in.readInt();
            this.user_id = in.readInt();
            this.consignee = in.readString();
            this.address = in.readString();
            this.mobile = in.readString();
            this.invoice_no = in.readString();
            this.pay_time = in.readInt();
            this.shipping_time = in.readInt();
            this.message = in.readString();
            this.region_id = in.readInt();
            this.order_goods = new ArrayList<OrderGoodsBean>();
            in.readList(this.order_goods, OrderGoodsBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>() {
            @Override
            public OrderBean createFromParcel(Parcel source) {
                return new OrderBean(source);
            }

            @Override
            public OrderBean[] newArray(int size) {
                return new OrderBean[size];
            }
        };
    }
}
