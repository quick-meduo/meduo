/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.admin.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.quick.meduo.common.constants.UserCredentialLevel;
import org.quick.meduo.common.constants.UserState;
import org.quick.meduo.common.constants.UserType;
import org.quick.meduo.core.activerecord.MeduoModel;
import org.quick.meduo.mask.annotation.Masking;
import org.quick.meduo.mask.api.SensitiveType;

import java.io.Serializable;
import java.util.Date;

/**
  *@Description:
  *@TODO:
  *@Changelog:
  *@Authors: Brian.G @2019/11/12
  *@Since: 1.0
  */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class UserModel extends MeduoModel<UserModel> implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;
    private String uid;
    @Masking(SensitiveType.CHINESE_NAME)
    private String name;
    private Boolean frozen;
    @TableLogic
    private int    delflag;
    private UserType type;
    private UserState state;
    private String avatar;
    private String salt;
    private String phone;
    private String email;
    private String password;
    private UserCredentialLevel userCredentialLevel;
    @TableField(exist = false)
    private String encryptedPassword;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date   updateDate;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserCredentialLevel(UserCredentialLevel userCredentialLevel) {
        this.userCredentialLevel = userCredentialLevel;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public UserType getType() {
        return type;
    }

    public UserState getState() {
        return state;
    }

    public String getAvatar() {
        return avatar;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public UserCredentialLevel getUserCredentialLevel() {
        return userCredentialLevel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

}

