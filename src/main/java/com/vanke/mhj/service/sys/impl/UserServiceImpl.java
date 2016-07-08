package com.vanke.mhj.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.google.common.collect.Lists;
import com.vanke.mhj.dao.sys.UserDao;
import com.vanke.mhj.framework.constant.GlobalConstant;
import com.vanke.mhj.model.sys.Torganization;
import com.vanke.mhj.model.sys.Tuser;
import com.vanke.mhj.service.base.ServiceException;
import com.vanke.mhj.service.sys.OrganizationService;
import com.vanke.mhj.service.sys.UserService;
import com.vanke.mhj.utils.ControllerMsg;
import com.vanke.mhj.utils.MD5Util;
import com.vanke.mhj.utils.contants.CommonContants;
import com.vanke.mhj.utils.contants.UseStatus;
import com.vanke.mhj.vo.base.SessionInfo;
import com.vanke.mhj.vo.sys.Organization;
import com.vanke.mhj.vo.sys.User;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    @Resource
    private OrganizationService organizationService;

    @Override
    public void add(User u) throws Exception {

        if (getByLoginName(u) != null) {
            throw new Exception(ControllerMsg.USER_LOGIN_NAME_EXIST_ERROR);
        }
        if (!StringUtils.isEmpty(u.getPhone()) && dao.findUserByPhone(u.getPhone()) != null) {
            throw new Exception(ControllerMsg.USER_PHONE_EXIST_ERROR);
        }
        Tuser t = new Tuser();
        BeanUtils.copyProperties(u, t);
        t.setOrganization(dao.find(u.getOrgId(), Torganization.class));
        //t.setRole(dao.find(u.getRoleId(), Trole.class));
        t.setPassword(MD5Util.md5(u.getPassword()));
        t.setStatus(UseStatus.ACTIVITY.getValue());
        t.setIsdefault(GlobalConstant.NOT_DEFAULT);
        dao.save(t);
    }

    @Override
    public void delete(Long id) throws Exception {
        Tuser t = dao.find(id, Tuser.class);
        if (t.getIsdefault() == GlobalConstant.DEFAULT) {
            throw new Exception(ControllerMsg.USER_CANNOT_DELETE_DEFAULT_USER_ERROR);
        }
        del(t);
    }

    private void del(Tuser t) throws Exception {
        dao.delete(t);
    }

    @Override
    public void edit(User user) throws Exception {
        Tuser t = dao.find(user.getId(), Tuser.class);
        // 如果更改了用户的电话
        if (user.getPhone() != null && !"".equals(user.getPhone()) && !user.getPhone().equals(t.getPhone())
                && dao.findUserByPhone(user.getPhone()) != null) {
            throw new Exception(ControllerMsg.USER_PHONE_EXIST_ERROR);
        }
        t.setAge(user.getAge());
        t.setUserName(user.getUserName());
        Torganization org = dao.find(user.getOrgId(), Torganization.class);
        if (org != null) {
            t.setOrganization(org);
        }
        t.setSex(user.getSex());
        t.setPhone(user.getPhone());
        t.setStatus(user.getStatus());
        dao.update(t);
    }

    @Override
    public User get(Long id) throws Exception {
        Tuser t = dao.find(id, Tuser.class);
        User u = new User();
        BeanUtils.copyProperties(t, u);
        u.setStatusLabel(UseStatus.getLabel(u.getStatus()));
        if (t.getOrganization() != null) {
            u.setOrgId(t.getOrganization().getId());
            u.setOrgName(t.getOrganization().getOrgName());
        }

        return u;
    }

    @Override
    public User login(User user) throws Exception {
        if (StringUtils.isEmpty(user.getLoginname().trim())) {
            throw new ServiceException("登录失败，请输入登录账号！");
        }
        Tuser t = dao.getUserByNameOrPhone(user.getLoginname().trim());
        if (t == null || !t.getPassword().equals(MD5Util.md5(user.getPassword()))) {
            throw new ServiceException(ControllerMsg.USER_LOGINNAME_OR_PASSWORD_INCORRECT_ERROR);
        }
        if (t.getStatus() == UseStatus.ACTIVITY.getValue()) {
            User u = new User();
            BeanUtils.copyProperties(t, u);
            u.setOrgNo(t.getOrganization().getOrgNo());
            u.setOrgId(t.getOrganization().getId());
            u.setOrgName(t.getOrganization().getOrgName());
            return u;
        } else if (t.getStatus() == UseStatus.UNACTIVITY.getValue()) {
            throw new ServiceException(ControllerMsg.USER_USER_DISABLED_ERROR);
        }
        return null;
    }


    @Override
    public PageModel dataGrid(User user, PageModel ph) throws Exception {
        List<User> ul = Lists.newArrayList();
        List<Condition> conditions = Lists.newArrayList();
        fillConditions(user, conditions);
        QueryResult<Tuser> result = dao.getPageResult(Tuser.class, conditions, ph);
        for (Tuser t : result.getReultList()) {
            User u = new User();
            BeanUtils.copyProperties(t, u);
            u.setStatusLabel(UseStatus.getLabel(u.getStatus()));
            if (t.getOrganization() != null) {
                u.setOrgId(t.getOrganization().getId());
                u.setOrgName(t.getOrganization().getOrgName());
            }
            ul.add(u);
        }
        ph.setTotal(result.getTotalCount());
        ph.setRows(ul);
        return ph;
    }

    private void fillConditions(User user, List<Condition> conditions) throws Exception {
        if (user != null) {
            if (!StringUtils.isEmpty(user.getOrgId())) {
                Organization org = organizationService.get(user.getOrgId());
                user.setOrgNo(org.getOrgNo());
            }
            if (!StringUtils.isEmpty(user.getUserName())) {
                conditions.add(new Condition("userName", "%" + user.getUserName() + "%", Condition.LIKE));
            }
            if (!StringUtils.isEmpty(user.getCreatedatetimeStart())) {
                conditions.add(new Condition("createDate", user.getCreatedatetimeStart(), Condition.GREAT_THAN_EQUAL));
            }
            if (!StringUtils.isEmpty(user.getCreatedatetimeEnd())) {
                conditions.add(new Condition("createDate", user.getCreatedatetimeEnd(), Condition.LESS_THAN_EQUAL));
            }
            if (!StringUtils.isEmpty(user.getOrgNo())) {
                conditions
                        .add(new Condition("organization.orgNo", user.getOrgNo().toLowerCase() + "%", Condition.LIKE));
            }
        }
    }

    @Override
    public void editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd) throws Exception {
        Tuser u = dao.find(sessionInfo.getId(), Tuser.class);
        if (u.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {
            u.setPassword(MD5Util.md5(pwd));
        } else {
            throw new Exception(ControllerMsg.USER_ORIGINAL_PASSWORD_INCORRECT_ERROR);
        }
    }

    @Override
    public User getByLoginName(User user) throws Exception {
        Tuser t = dao.findUserByLoginname(user.getLoginname());
        User u = new User();
        if (t != null) {
            BeanUtils.copyProperties(t, u);
        } else {
            return null;
        }
        return u;
    }

    @Override
    public List<User> getUserListByUserType() throws Exception {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(new Condition("usertype", 1, Condition.EQUAL_TO));
        conditions.add(new Condition("status", UseStatus.ACTIVITY.getValue(), Condition.EQUAL_TO));
        List<Tuser> list = dao.getPageResult(Tuser.class, conditions, null).getReultList();
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < list.size(); i++) {
            User u = new User();
            BeanUtils.copyProperties(list.get(i), u);
            u.setStatusLabel(UseStatus.getLabel(u.getStatus()));
            users.add(u);
        }
        return users;
    }

    @Override
    public String[] getUserListNameByUserType() throws Exception {
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(new Condition("usertype", 1, Condition.EQUAL_TO));
        conditions.add(new Condition("status", UseStatus.ACTIVITY.getValue(), Condition.EQUAL_TO));
        List<Tuser> list = dao.getPageResult(Tuser.class, conditions, null).getReultList();
        String[] users = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            users[i] = list.get(i).getUserName();
        }
        return users;
    }

    @Override
    public void resetPassword(Long id) throws Exception {
        Tuser user = dao.find(id, Tuser.class);
        user.setPassword(MD5Util.md5(CommonContants.DEFAULT_PWD));
        dao.update(user);
    }


    @Override
    public Tuser findUserByOpenId(String openId) throws Exception {
        List<Condition> conList = Lists.newArrayList();
        conList.add(new Condition("openId", openId, Condition.EQUAL_TO));
        return dao.getSingleResult(Tuser.class, conList);
    }

}
