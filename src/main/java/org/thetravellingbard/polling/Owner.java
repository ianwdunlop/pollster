package org.thetravellingbard.polling;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "OWNER_DETAILS")
public class Owner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Blog> blogList;

    public Owner() { }

    public Owner(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Blog> getBlogList() { return blogList; }
    public void setBlogList(List<Blog> blogList) { this.blogList = blogList; }

    @Override
    public String toString() {
        String blogs ="";
        for (Blog blog: getBlogList()) {
            blogs += blog.toString();
        }
        return "{" + "\"id\"=" + id + ", \"name\"=\"" + name + "\"" +  ", \"blogs\": [" + blogs + "]" + "}";
    }
}