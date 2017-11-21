package com.mustafaergan.springdata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Entity
@Table(name = "customer")
@Where(clause="deleted=false")
@SQLDelete(sql = 
    "UPDATE customer " +
    "SET deleted = true " +
    "WHERE customer_id = ? ")
public @Data class Customer {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "customer_id", length = 50)
	private String customerId;
	private String name;
	@Column(name = "create_by", length = 50)
	private String createBy;
	@Column(name = "update_by", length = 50)
	private String updateBy;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;
    private boolean deleted;

	@PrePersist
	void onCreate() {
		setDeleted(false);
		setCreateDate(new Date());
		setCreateBy("admin");
	}

	@PreUpdate
	void onPersist() {
		setUpdateDate(new Date());
		setUpdateBy("admin");
	}
}
