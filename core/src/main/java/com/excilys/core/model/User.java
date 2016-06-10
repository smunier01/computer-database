package com.excilys.core.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
    
    @Column(name = "username", unique = true, nullable = false)
    @Size(min=3, max=30)
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min=3, max=30)
    private String password;

    @NotNull
    private String role;

    public User() {
        this("", "");
    }

    public User(String username, String password) {
        this(username, password, null);
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getRoles() {
        return Arrays.asList(this.role.split(","));
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
    	return String.format("%d %s %s %s", id, username, password, role);
    }
}
