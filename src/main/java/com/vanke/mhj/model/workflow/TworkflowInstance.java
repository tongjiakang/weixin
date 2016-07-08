package com.vanke.mhj.model.workflow;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.vanke.mhj.model.base.IdEntity;

@Entity
@Table(name = "SYS_WF_INSTANCE")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TworkflowInstance extends IdEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1069679047569111409L;

    @Column(name = "IS_END", length = 2)
    private String isEnd = "0"; // 状态：0未结束，1结束

    @Column(name = "PROCESS_STATUS", length = 2)
    private String processStatus = "0";// 0未提交，1待审核，2审核通过，-1审核不通过，-2作废

    /**
     * Entity-流程日志表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workflowInstance", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @OrderBy("id ASC")
    private Set<TworkflowLog> wfLog = new HashSet<TworkflowLog>(0);

    // /**
    // * Entity-户型表
    // */
    // @OneToOne(fetch = FetchType.LAZY, mappedBy = "workflowInstance", cascade
    // =
    //
    // { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
    // targetEntity = ThouseType.class)
    //
    // private ThouseType tHouseType = new ThouseType();

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public Set<TworkflowLog> getWfLog() {
        return wfLog;
    }

    public void setWfLog(Set<TworkflowLog> wfLog) {
        this.wfLog = wfLog;
    }

    // public ThouseType gettHouseType() {
    // return tHouseType;
    // }
    //
    // public void settHouseType(ThouseType tHouseType) {
    // this.tHouseType = tHouseType;
    // }
}
