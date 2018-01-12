package com.wxh.myapplication;

import java.io.Serializable;

/**
 * @auther wxh
 * @date 2018/1/9 14:56
 */

public class Person implements Serializable {

    private Integer personId;
    private String name;
    private String phone;

    public Person(){

    }

    public Person(Integer personId, String name, String phone) {
        this.personId = personId;
        this.name = name;
        this.phone = phone;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (personId != null ? !personId.equals(person.personId) : person.personId != null)
            return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return phone != null ? phone.equals(person.phone) : person.phone == null;

    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
