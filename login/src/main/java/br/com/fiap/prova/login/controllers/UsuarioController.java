package br.com.fiap.prova.login.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.prova.login.dto.UsuarioDto;
import br.com.fiap.prova.login.model.Usuario;
import br.com.fiap.prova.login.repositories.UsuarioRepository;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/home")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("home");
		return model;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro(UsuarioDto model) {
		return new ModelAndView("usuario/cadastro");
	}
	
	@GetMapping("/")
	public ModelAndView login(UsuarioDto model) {
		return new ModelAndView("usuario/login");
	}
	
	@PostMapping("/cadastrarUsuario")
	public ModelAndView cadastrarUsuario(@Valid UsuarioDto model, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			ModelAndView modelView = new ModelAndView("usuario/cadastro");
			return modelView;
		}
		Usuario usuario = modelMapper.map(model, Usuario.class);
		usuarioRepository.save(usuario);
		return new ModelAndView("redirect:/");

	}
	
	@PostMapping("/validaUsuario")
	public ModelAndView validalogin(@Valid UsuarioDto model) {		
		boolean status = false;
		List<Usuario> listaUsuario = usuarioRepository.findAll();		
		for (Usuario usuario : listaUsuario) {
			if(model.getUser().equals(usuario.getUser()) && model.getPassword().equals(usuario.getPassword())){
				status= true;
				break;
			}			
		}
		if (status) {
			return new ModelAndView("redirect:/home");
		}
		
		return new ModelAndView("redirect:/");

	}
	


	
}
