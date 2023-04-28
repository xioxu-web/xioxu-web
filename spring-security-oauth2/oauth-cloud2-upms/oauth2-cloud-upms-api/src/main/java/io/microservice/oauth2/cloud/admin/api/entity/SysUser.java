/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.microservice.oauth2.cloud.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.microservice.oauth2.cloud.common.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xiaoxu123
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键id")
	private Long userId;

	/**
	 * 用户名
	 */
	@Schema(title = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	@Schema(description = "随机盐")
	private String salt;

	/**
	 * 锁定标记
	 */
	@Schema(description = "锁定标记")
	private String lockFlag;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@Schema(description = "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	@Schema(description = "用户所属部门id")
	private Long deptId;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

	public Long getUserId() {
		return userId;
	}

	public
	void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public
	void setUsername(String username) {
		this.username = username;
	}

	public
	String getPassword() {
		return password;
	}

	public
	void setPassword(String password) {
		this.password = password;
	}

	public
	String getSalt() {
		return salt;
	}

	public
	void setSalt(String salt) {
		this.salt = salt;
	}

	public
	String getLockFlag() {
		return lockFlag;
	}

	public
	void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public
	String getPhone() {
		return phone;
	}

	public
	void setPhone(String phone) {
		this.phone = phone;
	}

	public
	String getAvatar() {
		return avatar;
	}

	public
	void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public
	Long getDeptId() {
		return deptId;
	}

	public
	void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public
	String getDelFlag() {
		return delFlag;
	}

	public
	void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
