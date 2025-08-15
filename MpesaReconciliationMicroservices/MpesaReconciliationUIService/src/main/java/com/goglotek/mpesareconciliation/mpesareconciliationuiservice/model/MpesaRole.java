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

@Entity
@Table(name = "mpesa_role")
public class MpesaRole {
	@Id
	@Column(name = "role_id")
	@SequenceGenerator(name = "role_sequence", sequenceName = "role_sq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
	private Long roleId;
	@Column(name = "createdOn", nullable = true)
	private Date createdOn;
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@ManyToMany
	@JoinTable(name = "mpesa_role_permissions", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id"))
	private List<MpesaPermission> permissions;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<MpesaPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<MpesaPermission> permissions) {
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
