 package user.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import user.bean.UserUploadDTO;
import user.service.ObjectStorageService;
import user.service.UserService;
import user.service.UserUploadService;

@Controller
@RequestMapping(value="user")
public class UserUploadController {
	@Autowired
	private UserUploadService userUploadService;
	@Autowired
	private ObjectStorageService objectStorageService;
	
	private String bucketName = "bitcamp-9th-bucket-144";
			
	@RequestMapping(value="uploadForm")
	public String uploadForm() {
		return "/upload/uploadForm";
	}
	
	@RequestMapping(value="uploadAJaxForm")
	public String uploadAJaxForm() {
		return "/upload/uploadAJaxForm";
	}
	

/*	
 *  파일 한개 업로드
 *  
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@ModelAttribute UserUploadDTO userUploadDTO,
						 @RequestParam MultipartFile img,
						 HttpSession session ) {
		
	// 실제 폴더 
		// HttpServletRequest 또는 HttpSession으로 생성 
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제폴더 : " + filePath);
		
		String imageOriginalFileName = img.getOriginalFilename(); 	// 실제 파일 이름
		File file = new File(filePath, imageOriginalFileName);
		
		try {
			img.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// <img src='' >: url을 사용할 때는 가상 주소!! 
		String result = "<span>"
					  + "<img src='/spring/storage/" + imageOriginalFileName + "' width='300' height='300'>"
					  + "</span>";
		System.out.println(userUploadDTO.getImageName() + userUploadDTO.getImageContent() + userUploadDTO.getImageFileName() + userUploadDTO.getImageOriginalFileName());
		
		userUploadDTO.setImageOriginalFileName(imageOriginalFileName);
		
		// DB 
		
		return result;
	}
*/
	
	
/*
 * 다중 파일 업로드 
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@ModelAttribute UserUploadDTO userUploadDTO,
						 @RequestParam MultipartFile[] img,
						 HttpSession session ) {
		
	// 실제 폴더 
		// HttpServletRequest 또는 HttpSession으로 생성 
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제폴더 : " + filePath);
		
		String imageOriginalFileName; 
		File file;
		String result;
		//FOR--------
		imageOriginalFileName = img[0].getOriginalFilename(); 
		file = new File(filePath, imageOriginalFileName);
		
		try {
			img[0].transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		result = "<span>"
				+ "<img src='/spring/storage/" + imageOriginalFileName + "' width='300' height='300'>"
				+ "</span>";
		// ------------
		
		//FOR--------
			imageOriginalFileName = img[1].getOriginalFilename(); 
			file = new File(filePath, imageOriginalFileName);
			
			try {
				img[1].transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			result += "<span>"
					+ "<img src='/spring/storage/" + imageOriginalFileName + "' width='300' height='300'>"
					+ "</span>";
			// ------------
	
		
		//System.out.println(userUploadDTO.getImageName() + userUploadDTO.getImageContent() + userUploadDTO.getImageFileName() + userUploadDTO.getImageOriginalFileName());
		
		//userUploadDTO.setImageOriginalFileName(imageOriginalFileName);
		
		// DB 
		
		return result;
	}
 * */
	
	
/*
 * 다중 파일 업로드 (드래그) 
 */
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String upload(@ModelAttribute UserUploadDTO userUploadDTO,
						 @RequestParam("img[]") List<MultipartFile> list,
						 HttpSession session ) {
		
	// 실제 폴더 
		// HttpServletRequest 또는 HttpSession으로 생성 
		String filePath = session.getServletContext().getRealPath("WEB-INF/storage");
		System.out.println("실제폴더 : " + filePath);
		
		String imageOriginalFileName; 
		String imageFileName; 
		File file;
		String result = "";
		
		// 파일들을 모아서 DB로 보내기
		List<UserUploadDTO> imageUploadList = new ArrayList<>();
		
		for(MultipartFile img : list) {
//			imageFileName = UUID.randomUUID().toString();
			
			//Naver Cloud upload platform;
			imageFileName = objectStorageService.uploadFile(bucketName, "storage/", img);
			
			imageOriginalFileName = img.getOriginalFilename(); 
			file = new File(filePath, imageOriginalFileName);
			
			try {
				img.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			result += "<span>"
					    + "<img src='/spring/storage/"
						+ imageOriginalFileName
						+ "'width='200' heigth='200' >"
						+ "</span>";
			
		/*	//imageOriginalFileName에서 한글 처리를 해야한다. 안그러면 파일명이?가됨
			// 파일명의 공백을 처리하지 못함
			try {
			result += "<span>"
					+ "<img src='/spring/storage/" + URLEncoder.encode(imageOriginalFileName, "UTF-8") + "' width='300' height='300'>"
					+ "</span>";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		*/
			UserUploadDTO dto = new UserUploadDTO();
			dto.setImageName(userUploadDTO.getImageName());
			dto.setImageContent(userUploadDTO.getImageContent());
			dto.setImageFileName(imageFileName);
			dto.setImageOriginalFileName(imageOriginalFileName);
			
			imageUploadList.add(dto);
		}
			
		// DB
		userUploadService.upload(imageUploadList);
		
		return result;
	}
	
	@RequestMapping(value="uploadList")
	public ModelAndView uploadList() {
		List<UserUploadDTO> list = userUploadService.uploadList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("/upload/uploadList");
		
		return mav;
//		return userUploadService.uploadList();
	}
	
	@RequestMapping(value="uploadView")
	public String uploadView(@RequestParam String seq, Model model) {
		UserUploadDTO userUploadDTO =  userUploadService.getUploadDTO(seq);
		model.addAttribute("userUploadDTO", userUploadDTO);
		return "/upload/uploadView";
	}
	
	@RequestMapping(value="uploadUpdateForm")
	public String uploadUpdateForm(@RequestParam String seq, Model model) {
		UserUploadDTO userUploadDTO = userUploadService.getUploadDTO(seq);
		model.addAttribute("userUploadDTO", userUploadDTO); 
		
		return "/upload/uploadUpdateForm";
	}	

	@RequestMapping(value="uploadUpdate")
	@ResponseBody
	public String uploadUpdate(@ModelAttribute UserUploadDTO userUploadDTO,
			@RequestParam("img") MultipartFile img) {
		
		userUploadService.uploadUpdate(userUploadDTO, img);
		return "이미지 수정 완료";
	}
	

	@RequestMapping(value="uploadDelete")
	@ResponseBody
	public void uploadDelete(@RequestParam String[] check) {
		for(String seq : check) {
			System.out.println(seq);
		}
		
		userUploadService.uploadDelete(check);
	}
	
	
}
