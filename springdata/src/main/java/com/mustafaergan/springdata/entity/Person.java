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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;


@Entity
@Table(name = "person")
@Where(clause="deleted=false")
@SQLDelete(sql = 
    "UPDATE person " +
    "SET deleted = true " +
    "WHERE person_id = ? ")
@NamedEntityGraphs({
    @NamedEntityGraph(name="Person.allJoinsButCustomer", attributeNodes = {
            @NamedAttributeNode("departmantSet")
    }),
    @NamedEntityGraph(name="Person.allJoins", attributeNodes = {
            @NamedAttributeNode("departmantSet"),
            @NamedAttributeNode("customerSet")
    }),
    @NamedEntityGraph(name="noJoins", attributeNodes = {
    })
})
public @Data class Person {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy = "uuid")
    @Column(name = "person_id", length = 50)
    private String personId;
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
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name="lookup_person_departmant",
      joinColumns={ @JoinColumn(name="person_id", referencedColumnName="person_id") },
      inverseJoinColumns={ @JoinColumn(name="departmant_id", referencedColumnName="departmant_id")})
    private Set<Departmant> departmantSet;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name="lookup_person_customer",
      joinColumns={ @JoinColumn(name="person_id", referencedColumnName="person_id") },
      inverseJoinColumns={ @JoinColumn(name="customer_id", referencedColumnName="customer_id")})
    private Set<Customer> customerSet;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address; 

}
