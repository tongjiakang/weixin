package com.vanke.mhj.model.material;

import com.vanke.mhj.model.base.IdEntity;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "WX_MSG_TEXTITEM")
public class NewsItem extends IdEntity {
    @Column(name = "TITLE", length = 64)
    private String title;
    @Column(name = "AUTHOR", length = 64)
    private String author;
    @Column(name = "IMAGEPATH", length = 256)
    private String imagePath;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "DESCRIPTION", length = 256)
    private String description;
    @ManyToOne(cascade = { CascadeType.MERGE })
    @JoinColumn(name = "newsTemplateId")
    private NewsTemplate newsTemplate;
    @Column(name = "ORDERS", length = 2)
    private String orders;
    private String accountId;
    /** 类型：图文|外部链接 */
    @Column(name = "NEWTYPE", length = 10)
    private String newType;
    /** 外部 */
    @Column(name = "URL", length = 256)
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NewsTemplate getNewsTemplate() {
        return newsTemplate;
    }

    public void setNewsTemplate(NewsTemplate newsTemplate) {
        this.newsTemplate = newsTemplate;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
