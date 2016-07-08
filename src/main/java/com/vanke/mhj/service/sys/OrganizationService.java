package com.vanke.mhj.service.sys;

import java.util.List;

import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.base.Tree;
import com.vanke.mhj.vo.sys.Organization;

public interface OrganizationService {

	public List<Organization> treeGrid(String orgNo) throws Exception;

	public void add(Organization organization) throws Exception;

	public void delete(Long id) throws Exception;

	public void edit(Organization organization) throws Exception;

	public Organization get(Long id) ;

	public List<Tree> tree(String orgNo) throws Exception;

	public List<Tree> treeOwn(String orgType, String orgNo) throws Exception;

	public List<Tree> loadRelatedOrgs(SessionInfo sessionInfo) throws Exception;

}
