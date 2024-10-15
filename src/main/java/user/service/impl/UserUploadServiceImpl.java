package user.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import user.bean.UserUploadDTO;
import user.dao.UserUploadDAO;
import user.service.ObjectStorageService;
import user.service.UserUploadService;

@Service
public class UserUploadServiceImpl implements UserUploadService {
	@Autowired
	private UserUploadDAO userUploadDAO;
	@Autowired
	private HttpSession session;
	@Autowired
	private ObjectStorageService objectStorageService;
	
	private String bucketName = "";

	
	@Override
	public void upload(List<UserUploadDTO> imageUploadList) {
		userUploadDAO.upload(imageUploadList);
	}


	@Override
	public List<UserUploadDTO> uploadList() {
		return userUploadDAO.uploadList();
	}


	@Override
	public UserUploadDTO getUploadDTO(String seq) {
		return userUploadDAO.getUploadDTO(seq);
	}

	@Override
	public void uploadUpdate(UserUploadDTO userUploadDTO, MultipartFile img) {
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제 폴더 : " + filePath);
		
		/*
		Object storage(NCP)는 이미지를 덮어쓰지 않는다.
		DB에서 seq에 해당하는 imageFileName을 꺼내와서 Object Storage(NCP)의 이미지를 삭제하고,
		새로운 이미지를 올린다.
		 */

		UserUploadDTO dto = userUploadDAO.getUploadDTO(userUploadDTO.getSeq() + ""); //기존 db에 보관된 1개의 정보
		String imageFileName;
		
		
		if(img.getSize() != 0)
		{
			
			imageFileName = userUploadDAO.getImageFileName(userUploadDTO.getSeq());
			System.out.println("imageFileName : "+ imageFileName);
			
			List<String> list = new ArrayList<>();
			list.add(imageFileName);
			
			//Object Storage(NCP) 이미지 삭제
			objectStorageService.deleteFile(bucketName, "storage/", list);
			//Object Storage(NCP) 새로운 이미지 올리기
			
			imageFileName = objectStorageService.uploadFile(bucketName, "storage/", img);
		
			String imageOriginalFileName = img.getOriginalFilename();
			File file = new File(filePath, imageOriginalFileName);
		
			
			try {
				img.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			userUploadDTO.setImageFileName(imageFileName);
			userUploadDTO.setImageOriginalFileName(imageOriginalFileName);

		} else {
			userUploadDTO.setImageFileName(dto.getImageFileName());
			userUploadDTO.setImageOriginalFileName(dto.getImageOriginalFileName());
		}
		
		//DB
		userUploadDAO.uploadUpdate(userUploadDTO);
	}	
	
	@Override
	public void uploadDelete(String[] check) {
		//mapper.xml에서 <forEach> 사용하려면 데이터를 list에 담아야 한다.
		List<String> list = new ArrayList<>();
		
		String imageFileName ;
		//Object storage에 있는 이미지도 삭제
		for(String seq : check ) {
			imageFileName = userUploadDAO.getImageFileName((Integer.parseInt(seq)));
			list.add(imageFileName);
		} // for
		
		objectStorageService.deleteFile(bucketName, "storage/", list);
		
		//DB
		userUploadDAO.uploadDelete(list);
	}
		
}
