package com.bytedance.androidcamp.network.demo.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Cat {

    /**
     * breeds : []
     * categories : [{"id":5,"name":"boxes"}]
     * id : h8
     * url : https://cdn2.thecatapi.com/images/h8.jpg
     */

    @SerializedName("id") private String id;
    @SerializedName("url") private String url;
    @SerializedName("breeds") private List<?> breeds;
    @SerializedName("categories") private List<Categories> categories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<?> breeds) {
        this.breeds = breeds;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public static class Categories {
        /**
         * id : 5
         * name : boxes
         */

        @SerializedName("id") private int id;
        @SerializedName("name") private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override public String toString() {
        return "Cat{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", breeds=" + breeds +
                ", categories=" + categories +
                '}';
    }
}
