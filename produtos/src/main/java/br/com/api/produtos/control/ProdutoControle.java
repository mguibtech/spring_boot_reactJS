package br.com.api.produtos.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.produtos.model.ProdutoModelo;
import br.com.api.produtos.model.RespostaModelo;
import br.com.api.produtos.service.ProdutoServico;

@RestController
@CrossOrigin(originPatterns = "*")
public class ProdutoControle {


  @Autowired
  private ProdutoServico ps;

  @GetMapping("/listar")
  public Iterable<ProdutoModelo> listar(){
    return ps.listar();
  }

  @PutMapping("/alterar")
  public ResponseEntity<?> alterar(@RequestBody ProdutoModelo pm){
    return ps.cadastrarAlterar(pm, "alterar");
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<?> cadastrar(@RequestBody ProdutoModelo pm){
    return ps.cadastrarAlterar(pm, "cadastrar");
  }

  @GetMapping("/")
  public String rota(){
    return "Api de produtos funcionando";
  }

  @DeleteMapping("/remover/{codigo}")
  public ResponseEntity<RespostaModelo> remover(@PathVariable long codigo){
    return ps.remover(codigo);
  }

}
