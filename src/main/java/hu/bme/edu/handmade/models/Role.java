package hu.bme.edu.handmade.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name")
    private Integer name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Collection<Privilege> privileges = new ArrayList<>();

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id) && Objects.equals(name, role.name) && Objects.equals(users, role.users) && Objects.equals(privileges, role.privileges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users, privileges);
    }
}
