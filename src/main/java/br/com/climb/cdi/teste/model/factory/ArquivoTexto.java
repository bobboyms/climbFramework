package br.com.climb.cdi.teste.model.factory;

public class ArquivoTexto {
    public void gerar() {
        System.out.println("arquivo de texto gerado");
    }

    public void close() {
        System.out.println("Arquivo fechado");
    }
}
