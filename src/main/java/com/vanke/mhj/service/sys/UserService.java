package com.vanke.mhj.service.sys;

import java.util.List;

import com.common.persistence.pagetable.PageModel;
import com.vanke.mhj.model.sys.Tuser;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.sys.User;

public interface UserService {

	public PageModel dataGrid(User user, PageModel ph) throws Exception;

	public void add(User user) throws Exception;

	public void delete(Long id) throws Exception;

	public void edit(User user) throws Exception;

	public User get(Long id) throws Exception;

	public User login(User user) throws Exception;

	public void editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) throws Exception;

	public User getByLoginName(User user) throws Exception;

	public List<User> getUserListByUserType() throws Exception;

	public String[] getUserListNameByUserType() throws Exception;

	public void resetPassword(Long id) throws Exception;

	public Tuser findUserByOpenId(String openId) throws Exception;

}
