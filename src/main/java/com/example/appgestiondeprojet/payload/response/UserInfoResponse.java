package com.example.appgestiondeprojet.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

}
