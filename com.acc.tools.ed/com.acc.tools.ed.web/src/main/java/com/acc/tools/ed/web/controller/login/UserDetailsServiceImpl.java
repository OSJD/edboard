package com.acc.tools.ed.web.controller.login;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.acc.tools.ed.integration.dto.EDBUser;
import com.acc.tools.ed.integration.service.ILoginService;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ILoginService iLoginService;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {


		try {
			EDBUser userDetails = iLoginService.searchUser(username);
			
			if(userDetails!= null)
			{
				
				System.out.println("ent id::" + userDetails.getEnterpriseId());
				Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
				GrantedAuthority authority=new SimpleGrantedAuthority (userDetails.getRole());
				authorities.add(authority);
				User user= new User(userDetails.getEnterpriseId(), userDetails.getPassword(), authorities);
				return user;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
