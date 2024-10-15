package user.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
	private String name;
	private String id;
	private String pwd;
	
	@Override
	public String toString() {

		return name + "\t" + id + "\t" + pwd;
	}
}
