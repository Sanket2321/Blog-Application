package com.codingwithsanket.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
//we have entity also so why we are using dto suppose we want to expose the data directly 
// in that case we used user dto we are not exposing entities or used it we only used entity for 
//for databse means entity only associated with table so  
//we are using dto because 
public class UserDto {
      private Long id;
      private String name;
      private String email;
      private String password;
      private String about;
      
}
