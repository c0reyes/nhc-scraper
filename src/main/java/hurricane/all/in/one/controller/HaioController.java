package hurricane.all.in.one.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hurricane.all.in.one.service.HaioServiceImpl;

@Controller
public class HaioController {
	@Autowired
	HaioServiceImpl haioService;

	@GetMapping({"/","/index.html"})
	public String getIndex(Model model) {
		haioService.setZone("at");
		model.addAttribute("img", haioService.getImg());
		model.addAttribute("imgSat", haioService.getImgSat());
		model.addAttribute("hurricane", haioService.getNoaa());
		model.addAttribute("model", haioService.getModel());
		model.addAttribute("meso", haioService.getMeso());
		model.addAttribute("page", haioService.getPage());
		return "index";
	}
	
	@GetMapping({"/zone/{zone}"})
	public String getIndex(@PathVariable String zone, Model model) {
		haioService.setZone(zone);
		model.addAttribute("img", haioService.getImg());
		model.addAttribute("imgSat", haioService.getImgSat());
		model.addAttribute("hurricane", haioService.getNoaa());
		model.addAttribute("model", haioService.getModel());
		model.addAttribute("meso", haioService.getMeso());
		model.addAttribute("page", haioService.getPage());
		return "index";
	}
	
	@RequestMapping(value="image", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@RequestParam("url")  String imageUrl) throws IOException {
		byte[] imageBytes = haioService.getImage(imageUrl);
	
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}
}
