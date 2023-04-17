package br.com.api.produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.model.ProdutoModelo;
import br.com.api.produtos.model.RespostaModelo;
import br.com.api.produtos.repository.ProdutoRepositorio;


@Service
public class ProdutoServico {

  @Autowired ProdutoRepositorio pr;

  @Autowired
  private RespostaModelo rm;

  //Metodo para litar todos os produtos
  public Iterable<ProdutoModelo> listar(){
    return pr.findAll();
  }

  //Metodo para cadastrar ou alterar produtos
  public ResponseEntity<?> cadastrarAlterar(ProdutoModelo pm, String acao){

    if(pm.getNome().equals("")){
      rm.setMensagem("O nome do produto e obrigatorio");
      return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);
    }else if(pm.getMarca().equals("")){
      rm.setMensagem("O nome da marca e obrigatorio");
      return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);
    }else{
      if(acao.equals("cadastrar")){
        return new ResponseEntity<ProdutoModelo>(pr.save(pm), HttpStatus.CREATED);
      }else{
        return new ResponseEntity<ProdutoModelo>(pr.save(pm), HttpStatus.OK);
      }
    }
  }

  //Metodo para remover Produtos
  public ResponseEntity<RespostaModelo> remover(long codigo){
    pr.deleteById(codigo);
    rm.setMensagem("O produto foi removido com sucesso");
    return new ResponseEntity<RespostaModelo>(rm, HttpStatus.OK);
  }
  
}
