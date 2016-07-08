package com.vanke.mhj.model.material;

import com.vanke.mhj.model.base.IdEntity;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.utils.contants.UseStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "WX_MSG_TEXTTEMPLATE")
public class NewsTemplate extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Torganization organization;
    @Column(name = "TEMPLATENAME", length = 256)
    private String templateName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "orders asc")
    private Set<NewsItem> newsItemList = new HashSet<NewsItem>(0);
    private String accountId;
    @Column(name = "TYPE", length = 16)
    private String type;
    
    @Column(name="USESTATUS",length=2)
    private Integer useStatus = UseStatus.ACTIVITY.getValue();

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Torganization getOrganization() {
        return organization;
    }

    public void setOrganization(Torganization organization) {
        this.organization = organization;
    }

	public Set<NewsItem> getNewsItemList() {
		return newsItemList;
	}

	public void setNewsItemList(Set<NewsItem> newsItemList) {
		this.newsItemList = newsItemList;
	}

	/**
	 * @return the userStatus
	 */
	public Integer getUseStatus() {
		return useStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
}
