/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import interfaces.Identified;
import java.sql.Date;


/**
 *
 * @author Ilya-pc
 */
public class Student implements Identified<Long> {
    
    private Long id;
    private String name;
    private String middleName;
    private String secondName;
    private Date dateBirth;
    private long idGroup;
    

    public Student() {
    }

    public Student(long id, String name, String middleName, String secondName, Date dateBirth, long idGroup) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.secondName = secondName;
        this.dateBirth = dateBirth;
        this.idGroup = idGroup;
    }

    @Override
    public Long getId(){
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setId(long id){
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }    

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", middleName=" + middleName + ", secondName=" + secondName + ", dateBirth=" + dateBirth + ", idGroup=" + idGroup + '}';
    }
    
}
