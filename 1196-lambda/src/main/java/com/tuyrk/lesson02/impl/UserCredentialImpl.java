package com.tuyrk.lesson02.impl;

import com.tuyrk.lesson02.IUserCredential;

/**
 * @author tuyrk
 */
public class UserCredentialImpl implements IUserCredential {
    /**
     * 通过用户账号，验证用户身份信息的接口
     *
     * @param username 要验证的用户账号
     * @return 返回身份信息[系统管理员、用户管理员、普通用户]
     */
    @Override
    public String verifyUser(String username) {
        if ("admin".equals(username)) {
            return "系统管理员";
        } else if ("manager".equals(username)) {
            return "用户管理员";
        }
        return "普通会员";
    }
}
