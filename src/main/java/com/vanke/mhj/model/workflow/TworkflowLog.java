package com.vanke.mhj.model.workflow;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.vanke.mhj.model.base.IdEntity;

@Entity
@Table(name = "SYS_WF_LOG")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TworkflowLog extends IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5427150157140283854L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "INSTANCE_ID")
	private TworkflowInstance workflowInstance;

	@Column(name = "STEP_NAME", length = 30)
	private String stepName;

	@Column(name = "PROCESS_OPT", length = 20)
	private String processOperation;

	@Column(name = "REMARK", length = 200)
	private String remark;

	public TworkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(TworkflowInstance workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getProcessOperation() {
		return processOperation;
	}

	public void setProcessOperation(String processOperation) {
		this.processOperation = processOperation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
