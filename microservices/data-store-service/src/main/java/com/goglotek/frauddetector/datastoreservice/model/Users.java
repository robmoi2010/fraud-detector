/*
 *
 *  * Copyright (C) 2025 Robert Moi, Goglotek LTD
 *  *
 *  * This file is part of the Fraud Detector System.
 *  *
 *  * The Fraud Detector System is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * The Fraud Detector System is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with the Fraud Detector System. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.goglotek.frauddetector.datastoreservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class Users implements UserDetails {

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
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
  private List<Role> roles;

  @Column(name = "credentials_not_expired", nullable = false)
  private Boolean credentialsNotExpired;

  @Column(name = "account_not_expired", nullable = false)
  private Boolean accountNotExpired;

  @Column(name = "account_not_locked", nullable = false)
  private Boolean accountNotLocked;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled;
  private Collection<? extends GrantedAuthority> authorities;

  public void setCredentialsNotExpired(Boolean credentialsNotExpired) {
    this.credentialsNotExpired = credentialsNotExpired;
  }

  public void setAccountNotExpired(Boolean accountNotExpired) {
    this.accountNotExpired = accountNotExpired;
  }

  public void setAccountNotLocked(Boolean accountNotLocked) {
    this.accountNotLocked = accountNotLocked;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

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

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNotExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNotLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNotExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public Boolean getAccountVerified() {
    return accountVerified;
  }

  public void setAccountVerified(Boolean accountVerified) {
    this.accountVerified = accountVerified;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getFailedLoginAttempts() {
    return failedLoginAttempts;
  }

  public void setFailedLoginAttempts(Integer failedLoginAttempts) {
    this.failedLoginAttempts = failedLoginAttempts;
  }

  public Boolean getLoginDisabled() {
    return loginDisabled;
  }

  public void setLoginDisabled(Boolean loginDisabled) {
    this.loginDisabled = loginDisabled;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
