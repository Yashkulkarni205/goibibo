package com.goibio.user.dto;

import com.goibio.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String city;
    private String email;
    private String phone;
    private String password;
    public User toEntity()
    {
        return new User(this.id,this.firstName,this.lastName,this.dateOfBirth,
                this.gender,this.city,this.email,this.phone,this.password);
    }
}
