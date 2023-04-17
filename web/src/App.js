import { useEffect, useState } from "react";
import "./App.css";
import { Formulario } from "./Formulario";
import { Tabela } from "./Tabela";

function App() {
  //Objeto produto
  const produto = {
    codigo: 0,
    nome: "",
    marca: "",
  };

  const [produtos, setProdutos] = useState([]);
  const [btnCadastrar, setBtnCadastrar] = useState(true);
  const [objProduto, setObjProduto] = useState(produto);

  function aoDigitar(e) {
    setObjProduto({ ...objProduto, [e.target.name]: e.target.value });
  }

  function cadastrar() {
    fetch("http://localhost:8080/cadastrar", {
      method: "post",
      body: JSON.stringify(objProduto),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((retorno) => retorno.json())
      .then((retorno_convertido) => {
        // console.log(retorno_convertido)
        if (retorno_convertido.mensagem !== undefined) {
          alert(retorno_convertido.mensagem);
        } else {
          setProdutos([...produtos, retorno_convertido]);
          alert("Produto cadatrado com sucesso!");
          limparFormulario();
        }
      });
  }

  function alterar() {
    fetch("http://localhost:8080/alterar", {
      method: "put",
      body: JSON.stringify(objProduto),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((retorno) => retorno.json())
      .then((retorno_convertido) => {
        // console.log(retorno_convertido)
        if (retorno_convertido.mensagem !== undefined) {
          alert(retorno_convertido.mensagem);
        } else {
          alert("Produto alterado com sucesso!");

          let vertorTemp = [...produtos];
          let indice = vertorTemp.findIndex((p) => {
            return p.codigo === objProduto.codigo;
          });

          vertorTemp[indice] = objProduto

          setProdutos(vertorTemp);

          limparFormulario();
        }
      });
  }

  function remover() {
    fetch("http://localhost:8080/remover/" + objProduto.codigo, {
      method: "delete",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((retorno) => retorno.json())
      .then((retorno_convertido) => {
        // console.log(retorno_convertido)
        alert(retorno_convertido.mensagem);

        let vertorTemp = [...produtos];
        let indice = vertorTemp.findIndex((p) => {
          return p.codigo === objProduto.codigo;
        });

        vertorTemp.splice(indice, 1);

        setProdutos(vertorTemp);

        limparFormulario();
      });
  }

  function limparFormulario() {
    setObjProduto(produto);
    setBtnCadastrar(true);
  }

  function selecionarProduto(indice) {
    setObjProduto(produtos[indice]);
    setBtnCadastrar(false);
  }

  useEffect(() => {
    fetch("http://localhost:8080/listar")
      .then((retorno) => retorno.json())
      .then((retorno_convertido) => setProdutos(retorno_convertido));
  }, []);

  return (
    <div>
      {/* <p>{JSON.stringify(objProduto)}</p> */}
      {/* <p>{JSON.stringify(produtos)}</p> */}
      <Formulario
        botao={btnCadastrar}
        eventoTeclado={aoDigitar}
        cadastrar={cadastrar}
        obj={objProduto}
        cancelar={limparFormulario}
        remover={remover}
        alterar={alterar}
      />
      <Tabela selecionar={selecionarProduto} vetor={produtos} />
    </div>
  );
}

export default App;
