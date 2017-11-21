package com.mustafaergan.springdata.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Entity
@Table(name = "address")
@Where(clause="deleted=false")
@SQLDelete(sql = 
    "UPDATE address " +
    "SET deleted = true " +
    "WHERE address_id = ? ")
public @Data class Address {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(name = "address_id", length = 50)
    private String addressId;
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
