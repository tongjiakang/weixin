package com.vanke.wxapi.vo;

/**
 * Created by PanJM on 2016/7/6.
 * 修改永久素材
 */
public class MaterialUpdateNews {

    private String media_id;

    private String index;

    private MaterialArticle articles;

    public MaterialArticle getArticles() {
        return articles;
    }

    public void setArticles(MaterialArticle articles) {
        this.articles = articles;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
