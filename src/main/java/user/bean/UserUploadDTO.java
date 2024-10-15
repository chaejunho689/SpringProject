package user.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUploadDTO {
	private int seq;
	private String imageName;
	private String imageContent;
	private String imageFileName;
	private String imageOriginalFileName;
}
