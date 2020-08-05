package br.com.climb.test.model;

public class BaixarEstoque {

    private String nomeProduto;
    private Float preco;
    private Long quantidade;

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Float getPreco() {
        return preco;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    @Override
    public String toString() {
        return "BaixarEstoque{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }
}
