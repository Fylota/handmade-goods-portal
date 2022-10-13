package hu.bme.edu.handmade.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "privileges")
public class Privilege {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "name")
    private Integer name;
    @Basic
    @Column(name = "role_id")
    private Integer roleId;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles = new ArrayList<>();

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Privilege that = (Privilege) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roleId);
    }
}
