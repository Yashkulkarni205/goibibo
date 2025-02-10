package com.goibio.user.entity;

import com.goibio.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String city;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String password;
    public UserDTO toDTO()
    {
        return new UserDTO(this.id,this.firstName,this.lastName,this.dateOfBirth,
                this.gender,this.city,this.email,this.phone,this.password);
    }
}
