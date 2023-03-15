package org.avj.pojo;

import java.util.Objects;

public class Employee {
    private int id;
    private String email;
    private String name;
    private String phone;
    private String post;
    private long salary;

    public Employee(){}

    public Employee(int id, String email, String name, String phone, String post, long salary) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.post = post;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPost() {
        return post;
    }

    public long getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(email, employee.email) && name.equals(employee.name) && Objects.equals(phone, employee.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, phone);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                '}';
    }
}
