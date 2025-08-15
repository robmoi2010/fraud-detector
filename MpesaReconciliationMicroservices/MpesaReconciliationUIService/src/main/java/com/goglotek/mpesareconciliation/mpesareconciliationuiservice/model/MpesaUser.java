package com.goglotek.mpesareconciliation.mpesareconciliationuiservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mpesa_user")
public class MpesaUser {
	@Id
	@Column(name = "user_id")
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private Long userId;
	@Column(name = "created_on", nullable = true)
	private Date createdOn;
	@Column(name = "updated_on", nullable = true)
	private Date updatedOn;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password;
	@Column(name = "firstname", nullable = true)
	private String firstName;
	@Column(name = "lastname", nullable = true)
	private String lastName;
	@Column(name = "account_verified", nullable = true)
	private Boolean accountVerified;
	@Column(name = "active", nullable = true)
	private Boolean active;
	@Column(name = "failed_login_attempts", nullable = true)
	private Integer failedLoginAttempts;
	@Column(name = "login_disabled", nullable = true)
	private Boolean loginDisabled;
	@ManyToMany
	@JoinTable(name = "mpesa_users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private List<MpesaRole> roles;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getAccountVerified() {
		return accountVerified;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getLoginDisabled() {
		return loginDisabled;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean isAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(Boolean accountVerified) {
		this.accountVerified = accountVerified;
	}

	public Integer getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(Integer failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public Boolean isLoginDisabled() {
		return loginDisabled;
	}

	public void setLoginDisabled(Boolean loginDisabled) {
		this.loginDisabled = loginDisabled;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<MpesaRole> getRoles() {
		return roles;
	}

	public void setRoles(List<MpesaRole> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
